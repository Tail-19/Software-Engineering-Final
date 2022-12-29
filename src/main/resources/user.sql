DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    id         bigint NOT NULL AUTO_INCREMENT,
    userName   varchar DEFAULT NULL,
    password   varchar DEFAULT NULL,
    pictureURL varchar DEFAULT NULL,
    PRIMARY KEY (id)
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
DROP TABLE IF EXISTS `list`;
CREATE TABLE `list`
(
    `id`      bigint NOT NULL AUTO_INCREMENT,
    `ownerID` varchar DEFAULT NULL,
    `ownerName` varchar DEFAULT NULL,
    `friendID` varchar DEFAULT NULL,
    `friendName` varchar DEFAULT NULL,
    PRIMARY KEY (`id`)
);











