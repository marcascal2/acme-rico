
-- USERS
-- Director
INSERT INTO users(username,password,enabled) VALUES ('director1','director1',TRUE);
INSERT INTO authorities VALUES ('director1','director');
-- Employees
INSERT INTO users(username,password,enabled) VALUES ('worker1','worker1',TRUE);
INSERT INTO authorities VALUES ('worker1','worker');
INSERT INTO users(username,password,enabled) VALUES ('worker2','worker2',TRUE);
INSERT INTO authorities VALUES ('worker2','worker');
-- Clients
INSERT INTO users(username,password,enabled) VALUES ('client1','client1',TRUE);
INSERT INTO authorities VALUES ('client1','client');
INSERT INTO users(username,password,enabled) VALUES ('client2','client2',TRUE);
INSERT INTO authorities VALUES ('client2','client');
INSERT INTO users(username,password,enabled) VALUES ('client3','client3',TRUE);
INSERT INTO authorities VALUES ('client3','client');
INSERT INTO users(username,password,enabled) VALUES ('client4','client4',TRUE);
INSERT INTO authorities VALUES ('client4','client');
INSERT INTO users(username,password,enabled) VALUES ('client5','client5',TRUE);
INSERT INTO authorities VALUES ('client5','client');
INSERT INTO users(username,password,enabled) VALUES ('client6','client6',TRUE);
INSERT INTO authorities VALUES ('client6','client');
INSERT INTO users(username,password,enabled) VALUES ('client7','client7',TRUE);
INSERT INTO authorities VALUES ('client7','client');
INSERT INTO users(username,password,enabled) VALUES ('client8','client8',TRUE);
INSERT INTO authorities VALUES ('client8','client');
INSERT INTO users(username,password,enabled) VALUES ('client9','client9',TRUE);
INSERT INTO authorities VALUES ('client9','client');
INSERT INTO users(username,password,enabled) VALUES ('client10','client10',TRUE);
INSERT INTO authorities VALUES ('client10','client');

-- CLIENTS
INSERT INTO clients VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', '23', '1998-04-04', 'Madison', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client1');
INSERT INTO clients VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', '23', '1998-04-04', 'Sun Prairie', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client2');
INSERT INTO clients VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', '23', '1998-04-04', 'McFarland', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client3');
INSERT INTO clients VALUES (4, 'Harold', 'Davis', '563 Friendly St.', '23', '1998-04-04', 'Windsor', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client4');
INSERT INTO clients VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', '23', '1998-04-04', 'Madison', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client5');
INSERT INTO clients VALUES (6, 'Javier', 'Ruiz', '9 Gordal', '23', '1998-04-04', 'Madison', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client6');
INSERT INTO clients VALUES (7, 'Pedro', 'Pérez', '637 Cardinal Ave.', '23', '1998-04-04', 'Sun Prairie', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client7');
INSERT INTO clients VALUES (8, 'Omar', 'Monteslamaravilla', '2692 Commerce St.', '23', '1998-04-04', 'McFarland', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client8');
INSERT INTO clients VALUES (9, 'Leiloleilo', 'Davile', '561 Friendly St.', '23', '1998-04-04', 'Windsor', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client9');
INSERT INTO clients VALUES (10, 'Peter', 'Parker', '2487 S. Fair Way', '23', '1998-04-04', 'Madison', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client10');


-- EMPLOYEES
INSERT INTO employees VALUES (1, 'Javier', 'Dorado Sanchez', '3000.0', 'director1');
INSERT INTO employees VALUES (2, 'Eduardo', 'Garcia Prado', '1500.0','worker1');
INSERT INTO employees VALUES (3, 'Rafael', 'Corchuelo F', '20000.0', 'worker2');

-- BANK ACCOUNTS
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (1, 'ES23 2323 2323 2323 2323', 2567.34, '2019-01-03', 'Viajes',1);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (2, 'ES24 2323 2323 2323 2323', 345.54, '2017-04-24', 'Ahorro',2);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (3, 'ES25 2323 2323 2323 2323', 543.43, '2016-10-30', '',3);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (4, 'ES26 2323 2323 2323 2323', 5436.87, '2018-06-17', 'Coche nuevo',4);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (5, 'ES27 2323 2323 2323 2323', 123, '2015-11-08', 'Regalos',5);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (6, 'ES28 2323 2323 2323 2323', 10000.00, '2019-01-03', 'Viajes',1);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (7, 'ES29 2323 2323 2323 2323', 2000., '2017-04-24', 'Ahorro',1);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (8, 'ES30 2323 2323 2323 2323', 150., '2016-10-30', '',10);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (9, 'ES31 2323 2323 2323 2323', 250000., '2018-06-17', 'Coche nuevo',9);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (10, 'ES32 2323 2323 2323 2323', 123., '2015-11-08', 'Regalos',5);

-- TRANSFERS
--Ahora mismo solo hay transferencias de la cuenta 6 del cliente 1 a distintas cuentas destino
INSERT INTO transfers(id,account_number,amount,destination,bank_account_id,client_id) VALUES (1,'ES28 2323 2323 2323 2323','2000','ES29 2323 2323 2323 2323',6,1);
INSERT INTO transfers(id,account_number,amount,destination,bank_account_id,client_id) VALUES (2,'ES28 2323 2323 2323 2323','200.','ES29 2323 2323 2323 2323',6,1);
INSERT INTO transfers(id,account_number,amount,destination,bank_account_id,client_id) VALUES (3,'ES28 2323 2323 2323 2323','100.','ES31 2323 2323 2323 2323',6,1);
INSERT INTO transfers(id,account_number,amount,destination,bank_account_id,client_id) VALUES (4,'ES28 2323 2323 2323 2323','1200','ES32 2323 2323 2323 2323',6,1);

-- TRANSFER APPLICATIONS
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (1,'ES29 2323 2323 2323 2323','2000.','PENDING',6,1);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (2,'ES29 2323 2323 2323 2323','200.','PENDING',6,1);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (3,'ES29 2323 2323 2323 2323','100.','PENDING',6,1);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (4,'ES29 2323 2323 2323 2323','1200.','PENDING',6,1);


--INSTANT TRANSFER
INSERT INTO instant_transfers(id,amount,destination,bank_account_id,client_id) VALUES (1, 67.34,'ES32 2323 2323 2323 2323',9,9);
INSERT INTO instant_transfers(id,amount,destination,bank_account_id,client_id) VALUES (2, 7.34,'ES24 2323 2323 2323 2323',9,9);
INSERT INTO instant_transfers(id,amount,destination,bank_account_id,client_id) VALUES (3, 47.99,'ES24 2323 2323 2323 2323',9,9);
INSERT INTO instant_transfers(id,amount,destination,bank_account_id,client_id) VALUES (4, 12.0,'ES25 2323 2323 2323 2323',9,9);

-- CREDIT CARD APPLICATIONS
INSERT INTO cc_applications(id,status,bank_account_id,client_id) VALUES (1,'ACCEPTED',1,1);
INSERT INTO cc_applications(id,status,bank_account_id,client_id) VALUES (2,'ACCEPTED',2,2);
INSERT INTO cc_applications(id,status,bank_account_id,client_id) VALUES (3,'ACCEPTED',3,3);
INSERT INTO cc_applications(id,status,bank_account_id,client_id) VALUES (4,'REJECTED',4,2);
INSERT INTO cc_applications(id,status,bank_account_id,client_id) VALUES (5,'REJECTED',5,3);
INSERT INTO cc_applications(id,status,bank_account_id,client_id) VALUES (6,'REJECTED',6,4);
INSERT INTO cc_applications(id,status,bank_account_id,client_id) VALUES (7,'ACCEPTED',10,5);
INSERT INTO cc_applications(id,status,bank_account_id,client_id) VALUES (8,'PENDING',7,1);
INSERT INTO cc_applications(id,status,bank_account_id,client_id) VALUES (9,'PENDING',9,9);

-- CREDIT CARDS
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id) VALUES (1,'123','01/2021','5541147884694773',1,1);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id) VALUES (2,'223','02/2021','5496675056390627',2,2);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id) VALUES (3,'376','03/2021','5159262819350277',3,3);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id) VALUES (4,'124','01/2021','5541147884694778',4,4);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id) VALUES (5,'253','02/2021','5496675056390629',5,5);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id) VALUES (6,'386','03/2021','5159262819350271',2,2);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id) VALUES (7,'223','01/2021','5541147884694772',3,3);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id) VALUES (8,'323','02/2021','5496675056394623',10,5);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id) VALUES (9,'476','03/2021','5159262813350277',8,10);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id) VALUES (10,'623','01/2021','5541147884694773',9,9);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id) VALUES (11,'983','02/2021','5496675656390627',10,5);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id) VALUES (12,'456','03/2021','5159272819350277',5,5);
