--充电桩相关表
--piles 充电桩表
--pile

DROP TABLE IF EXISTS `piles`;
CREATE TABLE piles
(
    `id` int NOT NULL,  --充电桩编号
    `charging_id` int DEFAULT NULL,  --正在充电的用户ID
    `wait_id` int DEFAULT NULL,  --等待充电的用户ID
    `type` enum('fast','slow') NOT NULL,  --充电桩种类，分为"slow"和"fast"
    `charging_number` int DEFAULT NULL,  --总充电次数
    `charging_time` int DEFAULT NULL,  --总充电时长
    `charging_amount` decimal(10,2) DEFAULT NULL,  --总充电电量
    `charging_cost` decimal(10,2) DEFAULT NULL,  --总充电费用
    `service_cost` decimal(10,2) DEFAULT NULL,  --总服务费
    `total_cost` decimal(10,2) DEFAULT NULL,  --总费用（充电+服务）
    `state` int DEFAULT NULL,  --充电桩状态，分为"0-停止工作","1-正在充电","2-故障"
    `wait_amount` int DEFAULT NULL,  --等待用户的充电量
    `left_time` int DEFAULT NULL, --充电剩余时间
    PRIMARY KEY (`id`)
);

--初始化充电桩信息
INSERT INTO `piles` (`id`, `charging_id`, `wait_id`, `type`, `charging_number`, `charging_time`, `charging_amount`, `charging_cost`, `service_cost`, `total_cost`, `state`) VALUES (1, 0, 0, 'fast', 1, 0, 0.00, 0.00, 0.00, 0.00, 0);
INSERT INTO `piles` (`id`, `charging_id`, `wait_id`, `type`, `charging_number`, `charging_time`, `charging_amount`, `charging_cost`, `service_cost`, `total_cost`, `state`) VALUES (2, 0, 0, 'fast', 2, 0, 0.00, 0.00, 0.00, 0.00, 0);
INSERT INTO `piles` (`id`, `charging_id`, `wait_id`, `type`, `charging_number`, `charging_time`, `charging_amount`, `charging_cost`, `service_cost`, `total_cost`, `state`) VALUES (3, 0, 0, 'slow', 3, 0, 0.00, 0.00, 0.00, 0.00, 0);
INSERT INTO `piles` (`id`, `charging_id`, `wait_id`, `type`, `charging_number`, `charging_time`, `charging_amount`, `charging_cost`, `service_cost`, `total_cost`, `state`) VALUES (4, 0, 0, 'slow', 4, 0, 0.00, 0.00, 0.00, 0.00, 0);
INSERT INTO `piles` (`id`, `charging_id`, `wait_id`, `type`, `charging_number`, `charging_time`, `charging_amount`, `charging_cost`, `service_cost`, `total_cost`, `state`) VALUES (5, 0, 0, 'slow', 5, 0, 0.00, 0.00, 0.00, 0.00, 0);

