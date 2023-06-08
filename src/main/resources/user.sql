/*DROP TABLE IF EXISTS users;
CREATE TABLE users
(
    userId     bigint NOT NULL AUTO_INCREMENT,
    userName   varchar DEFAULT NULL,
    password   varchar DEFAULT NULL,
    pictureURL varchar DEFAULT NULL,
    PRIMARY KEY (userId)
);*/
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users`
(
    `userid`     bigint NOT NULL AUTO_INCREMENT,
    `username`   varchar DEFAULT NULL,
    `password`   varchar DEFAULT NULL,
    `userrole`   varchar DEFAULT NULL,
    `balance`   bigint NOT NULL,
    `ischarging` bigint DEFAULT '0',
    PRIMARY KEY (`userid`)
);
insert into `users` (`username`,`password`,`userrole`,`balance`,`ischarging`) values ('bombtruck','j16j16j16','admin',0,0);
DROP TABLE IF EXISTS msgs;
CREATE TABLE msgs
(
    id      bigint NOT NULL AUTO_INCREMENT,
    receiveUserId varchar DEFAULT NULL,
    content varchar DEFAULT NULL,
    senderUserId varchar DEFAULT NULL,
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











