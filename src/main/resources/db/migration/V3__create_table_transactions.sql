CREATE TABLE `transactions` (
  `uuid` VARCHAR(36) NOT NULL,
  `source_account` VARCHAR(30) NOT NULL,
  `destination_account` VARCHAR(30) NOT NULL,
  `debit_flag` BIT(1) NOT NULL,
  `currency` VARCHAR(10) DEFAULT NULL,
  `amount` DECIMAL NOT NULL,
  `status` BIT(1) DEFAULT b'0',
  `create_datetime` DATETIME NOT NULL,
  `notes` VARCHAR(60) DEFAULT NULL,
  PRIMARY KEY (`uuid`));
