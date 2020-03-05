-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','veterinarian');

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

--TRANSFER APPLICATIONS
INSERT INTO transfer_applications VALUES (1,1);
INSERT INTO transfer_applications VALUES (2,2);
INSERT INTO transfer_applications VALUES (3,2);
INSERT INTO transfer_applications VALUES (4,1);

--INSTANT TRANSFER
INSERT INTO instant_transfers(id,amount,destination) VALUES (1, 167.34, 'ES7921000813610123456789');
INSERT INTO instant_transfers(id,amount,destination) VALUES (2, 45.54, 'ES9121000418450200051332');
INSERT INTO instant_transfers(id,amount,destination) VALUES (3, 3.43, 'ES5304871584883649447311');
INSERT INTO instant_transfers(id,amount,destination) VALUES (4, 36.87, 'ES6920383217998112214616');

