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
