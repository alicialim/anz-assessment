INSERT INTO `transactions` (`uuid`, `account_number`, `debit_flag`, `currency`, `amount`, `status`, `create_datetime`, `notes`)
VALUES
('108F5087-17B7-4B4B-88D4-3D60EA082516', '123-2223-212', b'1', 'AUD', 2342.62, b'1', NOW(), 'Bought laptop.'),
('2D102034-E20F-4CBC-A2D8-8B6AEF8064D2', '123-2223-212', b'1', 'AUD', 241.32, b'1', NOW(), 'Bought shoes.'),
('7EE47EE6-2C7F-44CB-AC95-631BB3A93A16', '987-8678-545', b'0', 'SGD', 675.52, b'1', NOW(), 'Earnings from project.'),
('88a86475-3dd9-4657-8522-13d81a42cd59', '123-2223-212', b'1', 'AUD', 1634.70, b'1', NOW(), NULL),
('8ede7505-13f2-47b2-a38b-6bc83ea41652', '987-8678-545', b'0', 'SGD', 637.62, b'1', NOW(), 'Reimbursement for faulty phone.'),
('ff3d521d-d229-408a-83e9-f9671355fe08', '412-8423-234', b'1', 'AUD', 3456.76, b'1', NOW(), NULL),
('dbd954b6-887f-4909-b64e-71eea7412956', '987-8678-545', b'1', 'SGD', 232.00, b'1', NOW(), NULL),
('2edfabd0-3840-43ec-a557-7d34ba655463', '987-8678-545', b'0', 'SGD', 957.12, b'1', NOW(), NULL),
('A54234A4-FE6E-49B2-98FD-43FA84EBAD17', '412-8423-234', b'0', 'AUD', 352.77, b'1', NOW(), NULL),
('FBC632F5-6F29-4341-9879-24B7032CD98F', '412-8423-234', b'1', 'AUD', 846.34, b'1', NOW(), 'Repaired car.'),
('2C979B9C-6A08-4D0F-B24A-26F360B30D24', '234-4324-654', b'0', 'AUD', 876.54, b'1', NOW(), NULL),
('46AA96D5-20FF-4E27-93FD-98CD20BC4AA1', '234-4324-654', b'0', 'AUD', 165.76, b'1', NOW(), NULL);