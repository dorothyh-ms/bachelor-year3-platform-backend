CREATE DATABASE platform;
GRANT ALL ON platform.* TO 'user'@'%';
GRANT SHOW DATABASES ON *.* TO 'user'@'%';
FLUSH PRIVILEGES;

CREATE DATABASE statistics;
GRANT ALL ON statistics.* TO 'user'@'%';
GRANT SHOW DATABASES ON *.* TO 'user'@'%';
FLUSH PRIVILEGES;