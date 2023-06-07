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
    `user_id`     bigint NOT NULL AUTO_INCREMENT,
    `username`   varchar DEFAULT NULL,
    `password`   varchar DEFAULT NULL,
    `userrole`   varchar DEFAULT NULL,
    PRIMARY KEY (`user_id`)
);
insert into `users` (`username`,`password`,`userrole`) values ('bomertruck','j16j16j16','manager');
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











