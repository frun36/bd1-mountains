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
    id            serial      NOT NULL,
    name          varchar(64) NOT NULL,
    user_id       int         NOT NULL,
    time_modified timestamp   NOT NULL DEFAULT now(),
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
    total_got_points int         NOT NULL DEFAULT 0,
    CONSTRAINT user_pk PRIMARY KEY (id),
    CONSTRAINT user_username UNIQUE (username),
    CONSTRAINT user_got_points CHECK (total_got_points >= 0)
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

SELECT rt.ordinal, p1.name, p1.altitude, p2.name, p2.altitude, t.color, t.got_points FROM mountains.route_trail_ordered rt
    JOIN mountains.trail t ON rt.trail_id = t.id
    JOIN mountains.point p1 ON t.start_point_id = p1.id
    JOIN mountains.point p2 ON t.end_point_id = p2.id
    WHERE rt.route_id = 2
    ORDER BY rt.ordinal;