-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('client1','client1',TRUE);
INSERT INTO authorities VALUES ('client1','client');

INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (1, 'ES9121000418450200051332', 2567.34, '2019-01-03', 'Viajes');
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (2, 'ES7921000813610123456789', 345.54, '2017-04-24', 'Ahorro');
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (3, 'ES5304871584883649447311', 543.43, '2016-10-30', '');
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (4, 'ES6920383217998112214616', 5436.87, '2018-06-17', 'Coche nuevo');
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias) VALUES (5, 'ES5001827947584138683181', 123, '2015-11-08', 'Regalos');
INSERT INTO clients VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', '1998-04-04', 'Madison', 'client1');
INSERT INTO clients VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', '1998-04-04', 'Sun Prairie', 'client1');
INSERT INTO clients VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', '1998-04-04', 'McFarland', 'client1');
INSERT INTO clients VALUES (4, 'Harold', 'Davis', '563 Friendly St.', '1998-04-04', 'Windsor', 'client1');
INSERT INTO clients VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', '1998-04-04', 'Madison', 'client1');
INSERT INTO clients VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', '1998-04-04', 'Monona', 'client1');
INSERT INTO clients VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', '1998-04-04', 'Monona', 'client1');
INSERT INTO clients VALUES (8, 'Maria', 'Escobito', '345 Maple St.', '1998-04-04', 'Madison', 'client1');
INSERT INTO clients VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', '1998-04-04', 'Madison', 'client1');
INSERT INTO clients VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', '1998-04-04', 'Waunakee', 'client1');

INSERT INTO instant_transfers(id,account_number_origin,amount,destination) VALUES (1, 'ES9121000418450200051332', 167.34, 'ES7921000813610123456789');
INSERT INTO instant_transfers(id,account_number_origin,amount,destination) VALUES (2, 'ES7921000813610123456789', 45.54, 'ES9121000418450200051332');
INSERT INTO instant_transfers(id,account_number_origin,amount,destination) VALUES (3, 'ES5304871584883649447311', 3.43, 'ES5304871584883649447311');
INSERT INTO instant_transfers(id,account_number_origin,amount,destination) VALUES (4, 'ES6920383217998112214616', 36.87, 'ES6920383217998112214616');
