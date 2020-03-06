-- Director
INSERT INTO users(username,password,enabled) VALUES ('director1','director1',TRUE);
INSERT INTO authorities VALUES ('director1','director');
-- Employees
INSERT INTO users(username,password,enabled) VALUES ('employee1','employee1',TRUE);
INSERT INTO authorities VALUES ('employee1','worker');
INSERT INTO users(username,password,enabled) VALUES ('employee2','employee2',TRUE);
INSERT INTO authorities VALUES ('employee2','worker');
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

-- BANK ACCOUNTS
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (1, 'ES9121000418450200051332', 2567.34, '2019-01-03', 'Viajes');
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (2, 'ES7921000813610123456789', 345.54, '2017-04-24', 'Ahorro');
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (3, 'ES5304871584883649447311', 543.43, '2016-10-30', '');
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (4, 'ES6920383217998112214616', 5436.87, '2018-06-17', 'Coche nuevo');
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (5, 'ES5001827947584138683181', 123, '2015-11-08', 'Regalos');

-- TRANSFERS
INSERT INTO transfers VALUES (1,'ES123456789012345678','200.30','ES09876543225852246');
INSERT INTO transfers VALUES (2,'ES123456789009876543','150.00','ES09876544642686232');
INSERT INTO transfers VALUES (3,'ES123456789056789012','2000.80','ES09847634576735432');
INSERT INTO transfers VALUES (4,'ES123436742694567890','304.90','ES09876543346356342');

-- TRANSFER APPLICATIONS
INSERT INTO transfer_applications VALUES (1,'ES09876543225852246','200.54',1);
INSERT INTO transfer_applications VALUES (2,'ES09876544642686232','345.65',2);
INSERT INTO transfer_applications VALUES (3,'ES09847634576735432','1002.00',2);
INSERT INTO transfer_applications VALUES (4,'ES09876543346356342','503.90',1);

--INSTANT TRANSFER
INSERT INTO instant_transfers(id,amount,destination) VALUES (1, 167.34, 'ES7921000813610123456789');
INSERT INTO instant_transfers(id,amount,destination) VALUES (2, 45.54, 'ES9121000418450200051332');
INSERT INTO instant_transfers(id,amount,destination) VALUES (3, 3.43, 'ES5304871584883649447311');
INSERT INTO instant_transfers(id,amount,destination) VALUES (4, 36.87, 'ES6920383217998112214616');

-- CREDIT CARDS
INSERT INTO credit_cards(id,number,deadline,cvv) VALUES (1,'5541147884694773','01/2021','123');
INSERT INTO credit_cards(id,number,deadline,cvv) VALUES (2,'5496675056390627','02/2021','223');
INSERT INTO credit_cards(id,number,deadline,cvv) VALUES (3,'5159262819350277','03/2021','124');
INSERT INTO credit_cards(id,number,deadline,cvv) VALUES (4,'5535869122542955','04/2021','523');

-- CREDIT CARD APPLICATIONS
INSERT INTO cc_applications(id,status) VALUES (1,'ACCEPTED');
INSERT INTO cc_applications(id,status) VALUES (2,'PENDING');
INSERT INTO cc_applications(id,status) VALUES (3,'REJECTED');

-- EMPLOYEES
INSERT INTO employees VALUES (1, 'Javier', 'Dorado Sanchez', '3000.0', 'director1');
INSERT INTO employees VALUES (2, 'Eduardo', 'Garcia Prado', '1500.0', 'employee1');
INSERT INTO employees VALUES (3, 'Rafael', 'Corchuelo F', '20000.0', 'employee2');

-- CLIENTS
INSERT INTO clients VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', '23', '1998-04-04', 'Madison', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client1');
INSERT INTO clients VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', '23', '1998-04-04', 'Sun Prairie', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client2');
INSERT INTO clients VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', '23', '1998-04-04', 'McFarland', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client3');
INSERT INTO clients VALUES (4, 'Harold', 'Davis', '563 Friendly St.', '23', '1998-04-04', 'Windsor', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client4');
INSERT INTO clients VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', '23', '1998-04-04', 'Madison', 'WORKER', '2018-06-06', 'MARRIED', '1000.0', 'client5');

