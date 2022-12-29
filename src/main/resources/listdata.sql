DROP TABLE IF EXISTS `list`;
CREATE TABLE `list`
(
    `id`      bigint(20) NOT NULL AUTO_INCREMENT,
    `ownerID` varchar(255) DEFAULT NULL,
    `ownerName` varchar(255) DEFAULT NULL,
    `friendID` varchar(255) DEFAULT NULL,
    `friendName` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`id`)
);


