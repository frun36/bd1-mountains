TRUNCATE mountains.point, mountains.trail, mountains.app_user, mountains.route, mountains.route_trail;

INSERT INTO mountains.point (id, name, altitude, type)
VALUES (1, 'Schronisko Murowaniec', 1500, 'shelter'),
       (2, 'Czerwony Staw w Dolinie Pańszczycy', 1654, 'lake'),
       (3, 'Krzyżne', 2144, 'pass'),
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
       (19, 'Kuźnice', 1010, 'village'),
       (20, 'Myślenickie Turnie', 1360, 'peak'),
       (21, 'Kasprowy Wierch', 1987, 'peak'),
       (22, 'Liliowe', 1952, 'pass');
ALTER SEQUENCE mountains.point_id_seq RESTART WITH 23;

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
       (49, 4, 13, 2, 'yellow'),
       (50, 19, 20, 7, 'green'),
       (51, 20, 19, 4, 'green'),
       (52, 20, 21, 9, 'green'),
       (53, 21, 20, 3, 'green'),
       (54, 21, 22, 2, 'red'),
       (55, 22, 21, 2, 'red'),
       (56, 21, 17, 2, 'yellow'),
       (57, 17, 21, 6, 'yellow'),
       (58, 22, 17, 2, 'green'),
       (59, 17, 22, 6, 'green'),
       (60, 22, 11, 3, 'red'),
       (61, 11, 22, 2, 'red');
ALTER SEQUENCE mountains.trail_id_seq RESTART WITH 62;

INSERT INTO mountains.app_user(id, username, password)
VALUES (1, 'frun36', 'qwerty'),
       (2, 'test', 'test'),
       (3, 'sebix', 'piwo'),
       (4, 'obibok', '1234');
ALTER SEQUENCE mountains.app_user_id_seq RESTART WITH 5;

INSERT INTO mountains.route(id, name, user_id)
VALUES (1, 'Kościelec z Kuźnic', 1),
       (2, 'Granaty z Kuźnic', 1),
       (3, 'Orla Perć z Murowańca', 2),
       (4, 'Murowaniec (Harnaś)', 3),
       (5, 'Murowaniec (Tatra)', 3),
       (6, 'Murowaniec (Żubr)', 3),
       (7, 'Czarny Staw Gąsienicowy', 3),
       (8, 'Świnica z Kuźnic, od Zawratu', 2),
       (9, 'Świnica z Kuźnic, przez Kasprowy', 1),
       (10, 'Pusta', 2);
ALTER SEQUENCE mountains.route_id_seq RESTART WITH 11;

ALTER SEQUENCE mountains.route_trail_id_seq RESTART;

SELECT mountains.route_append(1, 42);
SELECT mountains.route_append(1, 46);
SELECT mountains.route_append(1, 40);
SELECT mountains.route_append(1, 26);
SELECT mountains.route_append(1, 28);
SELECT mountains.route_append(1, 29);
SELECT mountains.route_append(1, 25);
SELECT mountains.route_append(1, 20);
SELECT mountains.route_append(1, 23);
SELECT mountains.route_append(1, 47);
SELECT mountains.route_append(1, 45);

SELECT mountains.route_append(2, 43);
SELECT mountains.route_append(2, 46);
SELECT mountains.route_append(2, 40);
SELECT mountains.route_append(2, 48);
SELECT mountains.route_append(2, 7);
SELECT mountains.route_append(2, 9);
SELECT mountains.route_append(2, 37);
SELECT mountains.route_append(2, 33);
SELECT mountains.route_append(2, 41);
SELECT mountains.route_append(2, 47);
SELECT mountains.route_append(2, 45);

SELECT mountains.route_append(4, 42);
SELECT mountains.route_append(4, 46);
SELECT mountains.route_append(4, 47);
SELECT mountains.route_append(4, 44);

SELECT mountains.route_append(5, 43);
SELECT mountains.route_append(5, 46);
SELECT mountains.route_append(5, 47);
SELECT mountains.route_append(5, 45);

SELECT mountains.route_append(6, 42);
SELECT mountains.route_append(6, 46);
SELECT mountains.route_append(6, 47);
SELECT mountains.route_append(6, 45);

SELECT mountains.route_append(7, 43);
SELECT mountains.route_append(7, 46);
SELECT mountains.route_append(7, 40);
SELECT mountains.route_append(7, 41);
SELECT mountains.route_append(7, 47);
SELECT mountains.route_append(7, 44);

SELECT mountains.route_append(3, 40);
SELECT mountains.route_append(3, 31);
SELECT mountains.route_append(3, 30);
SELECT mountains.route_append(3, 14);
SELECT mountains.route_append(3, 13);
SELECT mountains.route_append(3, 12);
SELECT mountains.route_append(3, 10);
SELECT mountains.route_append(3, 8);
SELECT mountains.route_append(3, 6);
SELECT mountains.route_append(3, 4);
SELECT mountains.route_append(3, 2);

SELECT mountains.route_append(8, 42);
SELECT mountains.route_append(8, 46);
SELECT mountains.route_append(8, 40);
SELECT mountains.route_append(8, 31);
SELECT mountains.route_append(8, 30);
SELECT mountains.route_append(8, 15);
SELECT mountains.route_append(8, 17);
SELECT mountains.route_append(8, 18);
SELECT mountains.route_append(8, 20);
SELECT mountains.route_append(8, 23);
SELECT mountains.route_append(8, 47);
SELECT mountains.route_append(8, 45);

SELECT mountains.route_append(9, 50);
SELECT mountains.route_append(9, 52);
SELECT mountains.route_append(9, 54);
SELECT mountains.route_append(9, 60);
SELECT mountains.route_append(9, 16);
SELECT mountains.route_append(9, 17);
SELECT mountains.route_append(9, 18);
SELECT mountains.route_append(9, 24);
SELECT mountains.route_append(9, 27);
SELECT mountains.route_append(9, 41);
SELECT mountains.route_append(9, 47);
