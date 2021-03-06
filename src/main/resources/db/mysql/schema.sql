CREATE TABLE IF NOT EXISTS bank_accounts (
  id         INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  account_number VARCHAR(20),
  amount  	DOUBLE,
  created_at    DATE,
  alias       VARCHAR(30),
  INDEX(account_number)
)engine=InnoDB;

CREATE TABLE IF NOT EXISTS instant_transfers (
  id         INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  amount  	DOUBLE,
  destination    VARCHAR(20),
    INDEX(id)
)engine=InnoDB;

CREATE TABLE IF NOT EXISTS credit_cards (
  id         INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  number VARCHAR(20),
  deadline  	 VARCHAR(20),
  cvv    VARCHAR(20),
    INDEX(account_number_origin)
)engine=InnoDB;

CREATE TABLE IF NOT EXISTS cc_applications (
  id         INT(4) UNSIGNED NOT NULL AUTO_INCREMENT PRIMARY KEY,
  status	 VARCHAR(20) CHECK (status in ('ACCEPTED','REJECTED','PENDING'),
    INDEX(account_number_origin)
)engine=InnoDB;
