CREATE DATABASE platform;
GRANT ALL
    ON platform.* TO 'root'@'%';
GRANT SHOW
    DATABASES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE statistics;
GRANT ALL
    ON statistics.* TO 'root'@'%';
GRANT SHOW
    DATABASES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;
