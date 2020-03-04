-- One admin user, named admin1 with passwor 4dm1n and authority admin
INSERT INTO users(username,password,enabled) VALUES ('admin1','4dm1n',TRUE);
INSERT INTO authorities VALUES ('admin1','admin');
-- One owner user, named owner1 with passwor 0wn3r
INSERT INTO users(username,password,enabled) VALUES ('owner1','0wn3r',TRUE);
INSERT INTO authorities VALUES ('owner1','owner');
-- One vet user, named vet1 with passwor v3t
INSERT INTO users(username,password,enabled) VALUES ('vet1','v3t',TRUE);
INSERT INTO authorities VALUES ('vet1','veterinarian');


-- TRANSFERS
INSERT INTO transfers VALUES (1,'ES123456789012345678','200.30','ES09876543225852246');
INSERT INTO transfers VALUES (2,'ES123456789009876543','150.00','ES09876544642686232');
INSERT INTO transfers VALUES (3,'ES123456789056789012','2000.80','ES09847634576735432');
INSERT INTO transfers VALUES (4,'ES123436742694567890','304.90','ES09876543346356342');

--TRANSFER APPLICATIONS
INSERT INTO transfer_applications VALUES (5,1);
INSERT INTO transfer_applications VALUES (6,2);
INSERT INTO transfer_applications VALUES (7,2);
INSERT INTO transfer_applications VALUES (8,1);