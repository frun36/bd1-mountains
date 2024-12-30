DROP SCHEMA IF EXISTS mountains CASCADE;
CREATE SCHEMA mountains;

-- tables
CREATE TABLE mountains.point
(
    id       serial      NOT NULL,
    name     varchar(64) NULL,
    altitude int         NOT NULL,
    type     varchar(16) NOT NULL,
    CONSTRAINT point_pk PRIMARY KEY (id),
    CONSTRAINT point_type CHECK (type IN
                                 ('peak', 'lake', 'shelter', 'pass', 'glade', 'valley', 'signpost', 'village', 'other'))
);

CREATE TABLE mountains.route
(
    id               serial      NOT NULL,
    name             varchar(64) NOT NULL,
    user_id          int         NOT NULL,
    time_modified    timestamp   NOT NULL DEFAULT now(),
    total_got_points int         NOT NULL DEFAULT 0,
    CONSTRAINT route_pk PRIMARY KEY (id)
);

CREATE TABLE mountains.route_trail
(
    id       serial NOT NULL,
    route_id int    NOT NULL,
    trail_id int    NOT NULL,
    prev_id  int    NULL,
    next_id  int    NULL,
    CONSTRAINT route_trail_pk PRIMARY KEY (id),
    CONSTRAINT route_trail_prev_unique UNIQUE (prev_id),
    CONSTRAINT route_trail_next_unique UNIQUE (next_id)
);

CREATE TABLE mountains.trail
(
    id             serial     NOT NULL,
    start_point_id int        NOT NULL,
    end_point_id   int        NOT NULL,
    got_points     int        NOT NULL,
    color          varchar(8) NOT NULL,
    CONSTRAINT trail_pk PRIMARY KEY (id),
    CONSTRAINT trail_color CHECK (color IN ('red', 'yellow', 'green', 'blue', 'black')),
    CONSTRAINT trail_got_points CHECK (got_points >= 0)
);

CREATE TABLE mountains.app_user
(
    id               serial      NOT NULL,
    username         varchar(64) NOT NULL,
    password         varchar(64) NOT NULL,
    route_count      integer     NOT NULL DEFAULT 0,
    avg_route_len    numeric     NOT NULL DEFAULT 0,
    total_got_points integer     NOT NULL DEFAULT 0,
    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT user_username UNIQUE (username)
);

-- foreign keys
ALTER TABLE mountains.trail
    ADD CONSTRAINT fk_trail_start
        FOREIGN KEY (start_point_id)
            REFERENCES mountains.point (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

ALTER TABLE mountains.route_trail
    ADD CONSTRAINT route_trail_current_trail
        FOREIGN KEY (trail_id)
            REFERENCES mountains.trail (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

ALTER TABLE mountains.route_trail
    ADD CONSTRAINT route_trail_prev
        FOREIGN KEY (prev_id)
            REFERENCES mountains.route_trail (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

ALTER TABLE mountains.route_trail
    ADD CONSTRAINT route_trail_next
        FOREIGN KEY (next_id)
            REFERENCES mountains.route_trail (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

ALTER TABLE mountains.route_trail
    ADD CONSTRAINT route_trail_route
        FOREIGN KEY (route_id)
            REFERENCES mountains.route (id)
            ON DELETE CASCADE
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

ALTER TABLE mountains.route
    ADD CONSTRAINT route_user
        FOREIGN KEY (user_id)
            REFERENCES mountains.app_user (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

ALTER TABLE mountains.trail
    ADD CONSTRAINT trail_point
        FOREIGN KEY (end_point_id)
            REFERENCES mountains.point (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

-- views
CREATE OR REPLACE VIEW mountains.route_trail_ordered AS
(
WITH RECURSIVE route_trail_list AS (SELECT route_id, 1 as ordinal, id, trail_id
                                    FROM mountains.route_trail
                                    WHERE prev_id IS NULL

                                    UNION ALL

                                    SELECT rt.route_id, rtl.ordinal + 1, rt.id, rt.trail_id
                                    FROM route_trail_list rtl
                                             JOIN mountains.route_trail rt ON rtl.id = rt.prev_id)
SELECT *
FROM route_trail_list
    );

-- functions and triggers
CREATE OR REPLACE FUNCTION mountains.route_count_nulls(route_id INT, col_name TEXT)
    RETURNS INTEGER AS
$$
DECLARE
    count INT;
BEGIN
    EXECUTE format('SELECT count(*) FROM mountains.route_trail WHERE route_id = $1 AND %I IS NULL', col_name)
        INTO count
        USING route_id;

    RETURN count;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION mountains.route_find_incoherence(route_id INT)
    RETURNS INTEGER AS
$$
DECLARE
    prev_end_id   INT;
    points_cursor CURSOR FOR
        SELECT t.start_point_id, t.end_point_id
        FROM mountains.route_trail_ordered rt
                 JOIN mountains.trail t ON rt.trail_id = t.id
        WHERE rt.route_id = route_find_incoherence.route_id
        ORDER BY rt.ordinal;
    points_record RECORD;
BEGIN
    prev_end_id := NULL;
    OPEN points_cursor;

    LOOP
        FETCH points_cursor INTO points_record;
        EXIT WHEN NOT FOUND;

        IF prev_end_id IS NOT NULL AND points_record.start_point_id != prev_end_id THEN
            RAISE INFO 'Incoherence: prev_end_id = %, curr_start_id = %', prev_end_id, points_record.start_point_id;
            RETURN prev_end_id;
        END IF;

        prev_end_id := points_record.end_point_id;
    END LOOP;
    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION mountains.route_validate(route_id INT)
    RETURNS VOID AS
$$
DECLARE
    all_count   INT;
    start_count INT;
    end_count   INT;
    incoherence INT;
BEGIN
    SELECT count(*) FROM mountains.route_trail rt WHERE rt.route_id = route_validate.route_id INTO all_count;

    IF all_count = 0 THEN RETURN; END IF;

    SELECT mountains.route_count_nulls(route_id, 'prev_id') INTO start_count;
    IF NOT start_count = 1 THEN
        RAISE EXCEPTION 'Route should have exactly one starting trail - found %', start_count;
    END IF;

    SELECT mountains.route_count_nulls(route_id, 'next_id') INTO end_count;
    IF NOT end_count = 1 THEN
        RAISE EXCEPTION 'Route should have exactly one ending trail - found %', end_count;
    END IF;

    SELECT mountains.route_find_incoherence(route_id) INTO incoherence;
    IF incoherence IS NOT NULL THEN
        RAISE EXCEPTION 'Incoherence found after point ID %', incoherence;
    END IF;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION mountains.route_append(route_id INT, trail_id INT)
    RETURNS INTEGER AS
$$
DECLARE
    trail_count INT;
    last_id     INT;
    inserted_id INT;
BEGIN
    SELECT count(*) FROM mountains.route_trail rt WHERE rt.route_id = route_append.route_id INTO trail_count;

    IF trail_count = 0 THEN
        RAISE INFO 'First trail in route';
        INSERT INTO mountains.route_trail (route_id, trail_id, prev_id, next_id)
        VALUES (route_append.route_id, route_append.trail_id, NULL, NULL)
        RETURNING id INTO inserted_id;
    ELSE
        SELECT id
        FROM mountains.route_trail rt
        WHERE rt.route_id = route_append.route_id
          AND rt.next_id IS NULL
        INTO last_id;

        INSERT INTO mountains.route_trail (route_id, trail_id, prev_id, next_id)
        VALUES (route_append.route_id, route_append.trail_id, last_id, NULL)
        RETURNING id INTO inserted_id;

        UPDATE mountains.route_trail
        SET next_id = inserted_id
        WHERE id = last_id;
    END IF;

    PERFORM mountains.route_validate(route_id);

    RETURN inserted_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION mountains.route_pop_back(route_id INT)
    RETURNS INTEGER AS
$$
DECLARE
    trail_count   INT;
    deleted_count INT;
    last_id       INT;
    new_last_id   INT;
BEGIN
    SELECT count(*) FROM mountains.route_trail rt WHERE rt.route_id = route_pop_back.route_id INTO trail_count;

    IF trail_count = 0 THEN
        RETURN 0;
    ELSIF trail_count = 1 THEN
        DELETE FROM mountains.route_trail rt WHERE rt.route_id = route_pop_back.route_id;
    ELSE
        SELECT rt.id, rt.prev_id
        FROM mountains.route_trail rt
        WHERE rt.route_id = route_pop_back.route_id
          AND rt.next_id IS NULL
        INTO last_id, new_last_id;

        UPDATE mountains.route_trail
        SET next_id = NULL
        WHERE id = new_last_id;

        DELETE FROM mountains.route_trail WHERE id = last_id;
    END IF;

    PERFORM mountains.route_validate(route_id);
    GET DIAGNOSTICS deleted_count = ROW_COUNT;

    RETURN deleted_count;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION mountains.route_prepend(route_id INT, trail_id INT)
    RETURNS INTEGER AS
$$
DECLARE
    trail_count INT;
    first_id    INT;
    inserted_id INT;
BEGIN
    SELECT count(*) FROM mountains.route_trail rt WHERE rt.route_id = route_prepend.route_id INTO trail_count;

    IF trail_count = 0 THEN
        RAISE INFO 'First trail in route';
        INSERT INTO mountains.route_trail (route_id, trail_id, prev_id, next_id)
        VALUES (route_prepend.route_id, route_prepend.trail_id, NULL, NULL)
        RETURNING id INTO inserted_id;
    ELSE
        SELECT id
        FROM mountains.route_trail rt
        WHERE rt.route_id = route_prepend.route_id
          AND rt.prev_id IS NULL
        INTO first_id;

        INSERT INTO mountains.route_trail (route_id, trail_id, prev_id, next_id)
        VALUES (route_prepend.route_id, route_prepend.trail_id, NULL, first_id)
        RETURNING id INTO inserted_id;

        UPDATE mountains.route_trail
        SET prev_id = inserted_id
        WHERE id = first_id;
    END IF;

    PERFORM mountains.route_validate(route_id);

    RETURN inserted_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION mountains.route_pop_front(route_id INT)
    RETURNS INTEGER AS
$$
DECLARE
    trail_count   INT;
    deleted_count INT;
    first_id      INT;
    new_first_id  INT;
BEGIN
    SELECT count(*) FROM mountains.route_trail rt WHERE rt.route_id = route_pop_front.route_id INTO trail_count;

    IF trail_count = 0 THEN
        RETURN 0;
    ELSIF trail_count = 1 THEN
        DELETE FROM mountains.route_trail rt WHERE rt.route_id = route_pop_front.route_id;
    ELSE
        SELECT rt.id, rt.next_id
        FROM mountains.route_trail rt
        WHERE rt.route_id = route_pop_front.route_id
          AND rt.prev_id IS NULL
        INTO first_id, new_first_id;

        UPDATE mountains.route_trail
        SET prev_id = NULL
        WHERE id = new_first_id;

        DELETE FROM mountains.route_trail WHERE id = first_id;
    END IF;

    PERFORM mountains.route_validate(route_id);
    GET DIAGNOSTICS deleted_count = ROW_COUNT;

    RETURN deleted_count;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION mountains.route_recalculate_got()
    RETURNS TRIGGER AS
$$
DECLARE
    new_got      INT;
    old_route_id INT := NULL;
    new_route_id INT := NULL;
BEGIN
    IF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
        new_route_id := NEW.route_id;
    END IF;

    IF TG_OP = 'DELETE' OR TG_OP = 'UPDATE' THEN
        old_route_id := OLD.route_id;
    END IF;

    IF old_route_id IS NOT NULL THEN
        SELECT sum(t.got_points)
        FROM mountains.route_trail rt
                 JOIN mountains.trail t ON rt.trail_id = t.id
        WHERE rt.route_id = old_route_id
        INTO new_got;

        UPDATE mountains.route SET total_got_points = new_got, time_modified = now() WHERE id = old_route_id;
    END IF;

    IF new_route_id IS NOT NULL THEN
        SELECT sum(t.got_points)
        FROM mountains.route_trail rt
                 JOIN mountains.trail t ON rt.trail_id = t.id
        WHERE rt.route_id = new_route_id
        INTO new_got;

        UPDATE mountains.route SET total_got_points = new_got, time_modified = now() WHERE id = new_route_id;
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER route_got_trigger
    AFTER INSERT OR UPDATE OR DELETE
    ON mountains.route_trail
    FOR EACH ROW
EXECUTE FUNCTION mountains.route_recalculate_got();

CREATE OR REPLACE FUNCTION mountains.user_recalculate_stats(user_id INT)
    RETURNS VOID AS
$$
DECLARE
    new_count   INT;
    new_got     INT;
    new_avg_len NUMERIC;
BEGIN
    WITH user_routes AS (SELECT *
                         FROM mountains.route r
                         WHERE r.user_id = user_recalculate_stats.user_id)
    SELECT count(*), sum(total_got_points), avg(total_got_points)
    FROM user_routes
    INTO new_count, new_got, new_avg_len;

    UPDATE mountains.app_user
    SET route_count      = new_count,
        total_got_points = new_got,
        avg_route_len    = new_avg_len
    WHERE id = user_id;
END;
$$ LANGUAGE plpgsql;

CREATE OR REPLACE FUNCTION mountains.user_recalculate_stats_trig()
    RETURNS TRIGGER AS
$$
BEGIN
    IF TG_OP = 'INSERT' OR TG_OP = 'UPDATE' THEN
        PERFORM mountains.user_recalculate_stats(NEW.user_id);
    END IF;

    IF TG_OP = 'DELETE' OR TG_OP = 'UPDATE' THEN
        PERFORM mountains.user_recalculate_stats(OLD.user_id);
    END IF;

    RETURN NULL;
END;
$$ LANGUAGE plpgsql;

CREATE TRIGGER user_stats_trigger
    AFTER INSERT OR UPDATE OR DELETE
    ON mountains.route
    FOR EACH ROW
EXECUTE FUNCTION mountains.user_recalculate_stats_trig();