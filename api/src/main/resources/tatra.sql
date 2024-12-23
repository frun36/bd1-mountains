TRUNCATE mountains.point, mountains.trail, mountains.app_user, mountains.route, mountains.route_trail;

INSERT INTO mountains.point (id, name, altitude, type)
VALUES (1, 'Schronisko Murowaniec', 1500, 'shelter'),
       (2, 'Czerwony Staw w Dolinie Pańszczycy', 1654, 'lake'),
       (3, 'Przełęcz Krzyżne', 2144, 'pass'),
       (4, 'Skrajny Granat', 2228, 'peak'),
       (5, 'Zadni Granat', 2240, 'peak'),
       (6, 'Żleb Kulczyńskiego', 2150, 'other'),
       (7, 'Kozi Wierch', 2291, 'peak'),
       (8, 'Kozia Przełęcz', 2126, 'pass'),
       (9, 'Zawrat', 2159, 'pass'),
       (10, 'Świnica', 2302, 'peak'),
       (11, 'Świnicka Przełęcz', 2051, 'pass'),
       (12, 'Kozia Dolinka', 1788, 'valley'),
       (13, 'Czarny Staw Gąsienicowy', 1624, 'lake'),
       (14, 'Karb', 1853, 'pass'),
       (15, 'Kościelec', 2155, 'peak'),
       (16, 'Zielony Staw Gąsienicowy', 1672, 'lake'),
       (17, 'Dwoiśniak', 1588, 'lake'),
       (18, 'Między Kopami', 1499, 'signpost'),
       (19, 'Kuźnice', 1010, 'village');
ALTER SEQUENCE mountains.point_id_seq RESTART WITH 20;

INSERT INTO mountains.trail (id, start_point_id, end_point_id, got_points, color)
VALUES (1, 1, 2, 6, 'yellow'),
       (2, 2, 1, 4, 'yellow'),
       (3, 2, 3, 8, 'yellow'),
       (4, 3, 2, 2, 'yellow'),
       (5, 3, 4, 7, 'red'),
       (6, 4, 3, 6, 'red'),
       (7, 4, 5, 1, 'red'),
       (8, 5, 4, 1, 'red'),
       (9, 5, 6, 1, 'red'),
       (10, 6, 5, 1, 'red'),
       (11, 6, 7, 1, 'red'),
       (12, 7, 6, 1, 'red'),
       (13, 8, 7, 3, 'red'),
       (14, 9, 8, 3, 'red'),
       (15, 9, 10, 4, 'red'),
       (16, 11, 10, 4, 'red'),
       (17, 10, 11, 1, 'red'),
       (18, 11, 16, 1, 'black'),
       (19, 16, 11, 5, 'black'),
       (20, 16, 17, 1, 'black'),
       (21, 17, 16, 3, 'black'),
       (22, 1, 17, 2, 'yellow'),
       (23, 17, 1, 1, 'yellow'),
       (24, 16, 14, 3, 'blue'),
       (25, 14, 16, 1, 'blue'),
       (26, 13, 14, 3, 'black'),
       (27, 14, 13, 1, 'black'),
       (28, 14, 15, 4, 'black'),
       (29, 15, 14, 1, 'black'),
       (30, 12, 9, 6, 'blue'),
       (31, 13, 12, 2, 'blue'),
       (32, 9, 12, 2, 'blue'),
       (33, 12, 13, 1, 'blue'),
       (34, 12, 8, 3, 'yellow'),
       (35, 8, 12, 1, 'yellow'),
       (36, 12, 6, 4, 'black'),
       (37, 6, 12, 1, 'black'),
       (38, 12, 5, 6, 'green'),
       (39, 5, 12, 2, 'green'),
       (40, 1, 13, 3, 'blue'),
       (41, 13, 1, 2, 'blue'),
       (42, 19, 18, 7, 'yellow'),
       (43, 19, 18, 7, 'blue'),
       (44, 18, 19, 3, 'yellow'),
       (45, 18, 19, 3, 'blue'),
       (46, 18, 1, 1, 'blue'),
       (47, 1, 18, 1, 'blue'),
       (48, 13, 4, 8, 'yellow'),
       (49, 4, 13, 2, 'yellow');
ALTER SEQUENCE mountains.trail_id_seq RESTART WITH 50;

INSERT INTO mountains.app_user(id, username, password, total_got_points)
VALUES (1, 'frun36', 'qwerty', 0),
       (2, 'test', 'test', 0);
ALTER SEQUENCE mountains.app_user_id_seq RESTART WITH 3;

INSERT INTO mountains.route(id, name, user_id, time_modified)
VALUES (1, 'Kościelec z Kuźnic', 1, now()),
       (2, 'Granaty z Kuźnic', 1, now()),
       (3, 'Orla Perć z Murowańca', 2, now());
ALTER SEQUENCE mountains.route_id_seq RESTART WITH 4;

INSERT INTO mountains.route_trail(id, route_id, trail_id, prev_id, next_id)
VALUES (1, 1, 42, null, 2),
       (2, 1, 46, 1, 3),
       (3, 1, 40, 2, 4),
       (4, 1, 26, 3, 5),
       (5, 1, 28, 4, 6),
       (6, 1, 29, 5, 7),
       (7, 1, 25, 6, 8),
       (8, 1, 20, 7, 9),
       (9, 1, 23, 8, 10),
       (10, 1, 47, 9, 11),
       (11, 1, 45, 10, null);
INSERT INTO mountains.route_trail(id, route_id, trail_id, prev_id, next_id)
VALUES (12, 2, 43, null, 13),
       (13, 2, 46, 12, 14),
       (14, 2, 40, 13, 15),
       (15, 2, 48, 14, 16),
       (16, 2, 7, 15, 17),
       (17, 2, 9, 16, 18),
       (18, 2, 37, 17, 19),
       (19, 2, 33, 18, 20),
       (20, 2, 41, 19, 21),
       (21, 2, 47, 20, 22),
       (22, 2, 45, 21, null);
ALTER SEQUENCE mountains.route_trail_id_seq RESTART WITH 23;

SELECT p.name
FROM mountains.route_trail rt
         JOIN mountains.trail t ON t.id = rt.trail_id
         JOIN mountains.point p ON t.start_point_id = p.id;



