CREATE TABLE `accounts` (
  `account_number` VARCHAR(30) NOT NULL,
  `account_name` VARCHAR(50) NOT NULL,
  `user_id` VARCHAR(30) NOT NULL,
  `type` VARCHAR(20) NOT NULL,
  `balance` DECIMAL(10,2) NOT NULL,
  `currency` VARCHAR(10) DEFAULT NULL,
  `updated_datetime` DATETIME NOT NULL,
  PRIMARY KEY (`account_number`)
);