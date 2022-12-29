DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    userId     bigint NOT NULL AUTO_INCREMENT,
    userName   varchar DEFAULT NULL,
    password   varchar DEFAULT NULL,
    pictureURL varchar DEFAULT NULL,
    PRIMARY KEY (userId)
);
DROP TABLE IF EXISTS msgs;
CREATE TABLE msgs
(
    id      bigint NOT NULL AUTO_INCREMENT,
    userId varchar DEFAULT NULL,
    content varchar DEFAULT NULL,
    time time DEFAULT NULL,
    PRIMARY KEY (id)
);