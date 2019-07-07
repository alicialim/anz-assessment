CREATE TABLE `transactions` (
  `uuid` VARCHAR(36) NOT NULL,
  `account_number` VARCHAR(30) NOT NULL,
  `debit_flag` BIT(1) NOT NULL,
  `currency` VARCHAR(10) DEFAULT NULL,
  `amount` DECIMAL(10,2) NOT NULL,
  `status` BIT(1) DEFAULT b'0',
  `create_datetime` DATETIME NOT NULL,
  `notes` VARCHAR(60) DEFAULT NULL,
  PRIMARY KEY (`uuid`));
