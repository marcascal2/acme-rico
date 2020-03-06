INSERT IGNORE INTO bank_accounts VALUES (1, 'ES9121000418450200051332', 2567.34, '2019-01-03', 'Viajes');
INSERT IGNORE INTO bank_accounts VALUES (2, 'ES7921000813610123456789', 345.54, '2017-04-24', 'Ahorro');
INSERT IGNORE INTO bank_accounts VALUES (3, 'ES5304871584883649447311', 543.43, '2016-10-30', '');
INSERT IGNORE INTO bank_accounts VALUES (4, 'ES6920383217998112214616', 5436.87, '2018-06-17', 'Coche nuevo');
INSERT IGNORE INTO bank_accounts VALUES (5, 'ES5001827947584138683181', 123, '2015-11-08', 'Regalos');
INSERT IGNORE INTO clients VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', '1998-04-04', 'Madison');
INSERT IGNORE INTO clients VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', '1998-04-04', 'Sun Prairie');
INSERT IGNORE INTO clients VALUES (3, 'Eduardo', 'Rodriquez', '2693 Commerce St.', '1998-04-04', 'McFarland');
INSERT IGNORE INTO clients VALUES (4, 'Harold', 'Davis', '563 Friendly St.', '1998-04-04', 'Windsor');
INSERT IGNORE INTO clients VALUES (5, 'Peter', 'McTavish', '2387 S. Fair Way', '1998-04-04', 'Madison');
INSERT IGNORE INTO clients VALUES (6, 'Jean', 'Coleman', '105 N. Lake St.', '1998-04-04', 'Monona');
INSERT IGNORE INTO clients VALUES (7, 'Jeff', 'Black', '1450 Oak Blvd.', '1998-04-04', 'Monona');
INSERT IGNORE INTO clients VALUES (8, 'Maria', 'Escobito', '345 Maple St.', '1998-04-04', 'Madison');
INSERT IGNORE INTO clients VALUES (9, 'David', 'Schroeder', '2749 Blackhawk Trail', '1998-04-04', 'Madison');
INSERT IGNORE INTO clients VALUES (10, 'Carlos', 'Estaban', '2335 Independence La.', '1998-04-04', 'Waunakee');

INSERT IGNORE INTO instant_transfers VALUES (1, 167.34, 'ES7921000813610123456789');
INSERT IGNORE INTO instant_transfers VALUES (2, 45.54, 'ES9121000418450200051332');
INSERT IGNORE INTO instant_transfers VALUES (3, 3.43, 'ES5304871584883649447311');
INSERT IGNORE INTO instant_transfers VALUES (4, 36.87, 'ES6920383217998112214616');

INSERT IGNORE INTO credit_cards VALUES (1,'5541147884694773','01/2021','123');
INSERT IGNORE INTO credit_cards VALUES (2,'5496675056390627','02/2021','223');
INSERT IGNORE INTO credit_cards VALUES (3,'5159262819350277','03/2021','124');
INSERT IGNORE INTO credit_cards VALUES (4,'5535869122542955','04/2021','523');

INSERT IGNORE INTO cc_applications VALUES (1,'ACCEPTED');
INSERT IGNORE INTO cc_applications VALUES (2,'PENDING');
INSERT IGNORE INTO cc_applications VALUES (3,'REJECTED');

