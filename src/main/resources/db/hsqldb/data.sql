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
INSERT INTO clients VALUES (1, 'George', 'Franklin', '110 W. Liberty St.', '23', '1998-04-04', 'Madison', 'WORKER', '2018-06-06', 'MARRIED', '10000.0', 'client1');
INSERT INTO clients VALUES (2, 'Betty', 'Davis', '638 Cardinal Ave.', '23', '1998-04-04', 'Sun Prairie', 'WORKER', '2018-06-06', 'MARRIED', '10000.0', 'client2');
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
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (1, 'ES23 0025 0148 1259 1424', 2567.34, '2019-01-03', 'Viajes',1);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (2, 'ES24 3456 2323 4568 9078', 3445.54, '2017-04-24', 'Ahorro',2);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (3, 'ES25 2323 1247 9236 1245', 543.43, '2016-10-30', '',3);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (4, 'ES26 2589 0123 1085 1626', 5436.87, '2018-06-17', 'Coche nuevo',4);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (5, 'ES27 2414 2564 1235 1256', 123, '2015-11-08', 'Regalos',5);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (6, 'ES28 1236 2352 0258 0214', 10000.00, '2019-01-03', 'Negocios',1);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (7, 'ES29 1258 1010 1064 2579', 2000., '2017-04-24', 'Ahorro',1);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (8, 'ES30 2323 6398 1245 5412', 150., '2016-10-30', '',10);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (9, 'ES31 1237 2596 3614 1278', 250000., '2018-06-17', 'Coche nuevo',9);
INSERT INTO bank_accounts(id,account_number,amount,created_at,alias,client_id) VALUES (10, 'ES32 1578 1385 2050 0031', 123., '2015-11-08', 'Regalos',5);

-- TRANSFER APPLICATIONS
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (1,'ES90 9763 8820 9194 3456','2000.','ACCEPT',6,1);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (2,'ES57 1000 5683 9655 0960','220.2','REJECTED',6,1);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (3,'ES57 1000 5683 9655 0960','100.','ACCEPT',6,1);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (4,'ES29 1258 1010 1064 2579','1200.','PENDING',6,1);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (5,'ES47 1200 1289 9655 1269','140.54','ACCEPT',8,10);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (6,'ES24 3456 2323 4568 9078','256.34','PENDING',8,10);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (7,'ES29 1258 1010 1064 2579','150.','PENDING',10,5);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (8,'ES57 1254 5483 9655 4569','1267.','ACCEPT',9,9);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (9,'ES24 3456 2323 4568 9078','223.50','REJECTED',9,9);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (10,'ES27 2414 2564 1235 1256','200.','ACCEPT',9,9);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (11,'ES27 2414 2564 1235 1256','108.','PENDING',3,3);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (12,'ES23 0025 0148 1259 1424','1205.67','ACCEPT',1,1);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (13,'ES30 2323 6398 1245 5412','256.34','PENDING',8,10);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (14,'ES28 1236 2352 0258 0214','150.','PENDING',10,5);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (15,'ES28 1236 2352 0258 0214','1267.','PENDING',9,9);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (16,'ES23 0025 0148 1259 1424','223.50','REJECTED',9,9);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (17,'ES23 0025 0148 1259 1424','200.','PENDING',9,9);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (18,'ES32 1578 1385 2050 0031','108.','PENDING',3,3);
INSERT INTO transfer_applications(id,account_number_destination,amount,status,bank_account_id,client_id) VALUES (19,'ES32 1578 1385 2050 0031','1205.67','REJECTED',1,1);

--INSTANT TRANSFER
INSERT INTO instant_transfers(id,amount,destination,bank_account_id,client_id) VALUES (1, 67.34,'ES32 2323 2323 2323 2323',9,9);
INSERT INTO instant_transfers(id,amount,destination,bank_account_id,client_id) VALUES (2, 7.34,'ES24 2323 2323 2323 2323',9,9);
INSERT INTO instant_transfers(id,amount,destination,bank_account_id,client_id) VALUES (3, 47.99,'ES24 2323 2323 2323 2323',9,9);
INSERT INTO instant_transfers(id,amount,destination,bank_account_id,client_id) VALUES (4, 12.0,'ES25 2323 2323 2323 2323',9,9);
INSERT INTO instant_transfers(id,amount,destination,bank_account_id,client_id) VALUES (5, 75.0,'ES24 2323 2323 2323 2323',2,2);
INSERT INTO instant_transfers(id,amount,destination,bank_account_id,client_id) VALUES (6, 32.99,'ES24 2323 2323 2323 2323',5,5);
INSERT INTO instant_transfers(id,amount,destination,bank_account_id,client_id) VALUES (7, 12.0,'ES25 2323 2323 2323 2323',10,5);

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
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id,credit_card_application_id) VALUES (1,'123','01/2021','4095742744779740',1,1,1);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id,credit_card_application_id) VALUES (2,'223','02/2022','4441073223421195',2,2,2);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id,credit_card_application_id) VALUES (3,'376','03/2021','4490428524957448',3,3,3);
INSERT INTO credit_cards(id,cvv,deadline,number,bank_account_id,client_id,credit_card_application_id) VALUES (4,'124','01/2023','4845983225854171',10,5,7);

--LOAN
INSERT INTO loans (id,description,minimum_amount,minimum_income,number_of_deadlines,opening_price,monthly_fee,single_loan) VALUES (1, 'Préstamo de estudios', 20000.0 , 700.0, 4, 150.0, 0.01, TRUE);
INSERT INTO loans (id,description,minimum_amount,minimum_income,number_of_deadlines,opening_price,monthly_fee,single_loan) VALUES (2, 'Préstamo hipotecario', 15000.0 , 1200.0, 4, 150.0, 0.02, TRUE);
INSERT INTO loans (id,description,minimum_amount,minimum_income,number_of_deadlines,opening_price,monthly_fee,single_loan) VALUES (3, 'Préstamo personal', 10000.0 , 800.0, 4, 120.0, 0.03, TRUE);
INSERT INTO loans (id,description,minimum_amount,minimum_income,number_of_deadlines,opening_price,monthly_fee,single_loan) VALUES (4, 'Préstamo único', 9000.0 , 700.0, 2, 90.0, 0.01, TRUE);
INSERT INTO loans (id,description,minimum_amount,minimum_income,number_of_deadlines,opening_price,monthly_fee,single_loan) VALUES (5, 'Préstamo de negocio', 30000.0 , 1000.0, 4, 200.0, 0.10, TRUE);

--LOANAPPS
INSERT INTO loan_applications(id,amount,purpose,status,amount_paid,payed_deadlines,bank_account_id,loan_id,client_id) VALUES (1, 900.0, 'Universidad', 'PENDING', 0.0, 3, 1, 1, 1);
INSERT INTO loan_applications(id,amount,purpose,status,amount_paid,payed_deadlines,bank_account_id,loan_id,client_id) VALUES (2, 700.0, 'Comida', 'PENDING', 0.0, 0, 3, 1, 3);
INSERT INTO loan_applications(id,amount,purpose,status,amount_paid,payed_deadlines,bank_account_id,loan_id,client_id) VALUES (3, 1000.0, 'Hipoteca', 'ACCEPTED', 150.0, 0, 1, 2, 1);
INSERT INTO loan_applications(id,amount,purpose,status,amount_paid,payed_deadlines,bank_account_id,loan_id,client_id) VALUES (4, 2500.0, 'Negocio', 'REJECTED', 0.0, 0, 2, 4, 2);
INSERT INTO loan_applications(id,amount,purpose,status,amount_paid,payed_deadlines,bank_account_id,loan_id,client_id) VALUES (5, 10000.0, 'Comercio', 'ACCEPTED', 90.0, 2, 1, 4, 1);