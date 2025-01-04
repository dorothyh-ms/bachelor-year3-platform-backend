
CREATE DATABASE IF NOT EXISTS statistics;
GRANT ALL
    ON statistics.* TO 'root'@'%';
GRANT SHOW
    DATABASES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;



-- Players
INSERT INTO statistics.players (id, username, first_name, last_name, birth_date, gender, location_id) VALUES ('fff2deb7-7bf2-43d5-8897-4ddd8edc6ab1', 'player1', 'player1', 'player1', '2007-09-29', 'FEMALE', 'c9243160-f091-4ad0-998e-e3403d720c9e');
INSERT INTO statistics.players (id, username, first_name, last_name, birth_date, gender, location_id) VALUES ('e12bb6fd-8e23-40fd-95cc-f318abf3c38c', 'player2', 'player2', 'player2', '2008-08-17', 'FEMALE', 'c5345e8d-8f40-4ccc-b782-701f1bb6bb09');