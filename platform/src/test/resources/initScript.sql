CREATE DATABASE platform;
GRANT ALL
    ON landside.* TO 'root'@'%';
GRANT SHOW
    DATABASES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE statistics;
GRANT ALL
    ON waterside.* TO 'root'@'%';
GRANT SHOW
    DATABASES ON *.* TO 'root'@'%';
FLUSH PRIVILEGES;
