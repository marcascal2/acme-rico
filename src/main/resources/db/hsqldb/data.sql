-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','veterinarian');

INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (1, 'ES9121000418450200051332', 2567.34, '2019-01-03', 'Viajes');
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (2, 'ES7921000813610123456789', 345.54, '2017-04-24', 'Ahorro');
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (3, 'ES5304871584883649447311', 543.43, '2016-10-30', '');
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (4, 'ES6920383217998112214616', 5436.87, '2018-06-17', 'Coche nuevo');
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (5, 'ES5001827947584138683181', 123, '2015-11-08', 'Regalos');

INSERT INTO instant_transfers(id,account_number_origin,amount,destination) VALUES (1, 'ES9121000418450200051332', 167.34, 'ES7921000813610123456789');
INSERT INTO instant_transfers(id,account_number_origin,amount,destination) VALUES (2, 'ES7921000813610123456789', 45.54, 'ES9121000418450200051332');
INSERT INTO instant_transfers(id,account_number_origin,amount,destination) VALUES (3, 'ES5304871584883649447311', 3.43, 'ES5304871584883649447311');
INSERT INTO instant_transfers(id,account_number_origin,amount,destination) VALUES (4, 'ES6920383217998112214616', 36.87, 'ES6920383217998112214616');

INSERT INTO credit_cards(id,number,deadline,cvv) VALUES (1,'5541147884694773','01/2021','123');
INSERT INTO credit_cards(id,number,deadline,cvv) VALUES (2,'5496675056390627','02/2021','223');
INSERT INTO credit_cards(id,number,deadline,cvv) VALUES (3,'5159262819350277','03/2021','124');
INSERT INTO credit_cards(id,number,deadline,cvv) VALUES (4,'5535869122542955','04/2021','523');

INSERT INTO cc_applications(id,status) VALUES (1,'ACCEPTED');
INSERT INTO cc_applications(id,status) VALUES (2,'PENDING');
INSERT INTO cc_applications(id,status) VALUES (3,'REJECTED');