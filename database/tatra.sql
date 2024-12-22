TRUNCATE mountains.point, mountains.trail, mountains.app_user, mountains.route, mountains.route_point;

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
       (12, 'Zmarzły Staw Gąsienicowy', 1788, 'lake'),
       (13, 'Czarny Staw Gąsienicowy', 1624, 'lake'),
       (14, 'Karb', 1853, 'pass'),
       (15, 'Kościelec', 2155, 'peak'),
       (16, 'Zielony Staw Gąsienicowy', 1672, 'lake');

ALTER SEQUENCE mountains.point_id_seq RESTART WITH 17;