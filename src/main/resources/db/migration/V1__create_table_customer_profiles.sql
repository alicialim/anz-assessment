CREATE TABLE `customer_profiles` (
  `user_id` VARCHAR(30) NOT NULL,
  `user_name` VARCHAR(30) NOT NULL,
  `first_name` VARCHAR(50) NOT NULL,
  `last_name` VARCHAR(50) NOT NULL,
  `date_of_birth` DATETIME NOT NULL,
  `mobile_number` VARCHAR(30) DEFAULT NULL,
  PRIMARY KEY (`user_id`)
);