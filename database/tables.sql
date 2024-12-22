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

CREATE TABLE mountains.route_point
(
    id                serial NOT NULL,
    route_id          int    NOT NULL,
    current_point_id  int    NOT NULL,
    previous_point_id int    NULL,
    next_point_id     int    NULL,
    CONSTRAINT route_point_pk PRIMARY KEY (id)
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

ALTER TABLE mountains.route_point
    ADD CONSTRAINT route_point_current_point
        FOREIGN KEY (current_point_id)
            REFERENCES mountains.point (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

ALTER TABLE mountains.route_point
    ADD CONSTRAINT route_point_next_point
        FOREIGN KEY (next_point_id)
            REFERENCES mountains.route_point (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

ALTER TABLE mountains.route_point
    ADD CONSTRAINT route_point_previous_point
        FOREIGN KEY (previous_point_id)
            REFERENCES mountains.route_point (id)
            NOT DEFERRABLE
                INITIALLY IMMEDIATE
;

ALTER TABLE mountains.route_point
    ADD CONSTRAINT route_point_route
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
