-- Last modification date: 2016-10-03 02:35:18.051

-- tables
-- Table: checkings_accounts
CREATE TABLE checkings_accounts (
    id int NOT NULL,
    external_users_id int NOT NULL,
    checkings_account_no int NOT NULL,
    checkings_card_no int NOT NULL,
    balance double, 
    CONSTRAINT checkings_accounts_pk PRIMARY KEY (id)
);

-- Table: credit_card_account
CREATE TABLE credit_card_account (
    id int NOT NULL,
    external_users_id int NOT NULL,
    interest int NOT NULL,
    credit_card_no int NULL,
    CONSTRAINT credit_card_account_pk PRIMARY KEY (id)
);

-- Table: external_users
CREATE TABLE external_users (
    id INT(10) NOT NULL AUTO_INCREMENT,
    name VARCHAR(50) NOT NULL,
    designation VARCHAR(15) NOT NULL,
    address VARCHAR(50) NOT NULL,
    city VARCHAR(20) NOT NULL,
    state VARCHAR(20) NOT NULL,
    country VARCHAR(20) NOT NULL,
    pincode INT(10) NOT NULL,
    phone INT(20) NOT NULL,
    CONSTRAINT external_users_pk PRIMARY KEY (id)
);

-- Table: savings_accounts
CREATE TABLE savings_accounts (
    id int NOT NULL,
    external_users_id int NOT NULL,
    savings_acc_no int NOT NULL,
    savings_card_no int NOT NULL,
    balance double,
    CONSTRAINT savings_accounts_pk PRIMARY KEY (id)
);

-- Table: transaction
CREATE TABLE transaction (
    id int NOT NULL,
    amount int NOT NULL,
    timeStamp int NOT NULL,
    payee int NOT NULL,
    owner int NOT NULL,
    payment_type int NOT NULL,
    Description varchar(255) NOT NULL,
    CONSTRAINT id PRIMARY KEY (id)
);

-- foreign keys
-- Reference: checkings_accounts_external_users (table: checkings_accounts)
ALTER TABLE checkings_accounts ADD CONSTRAINT checkings_accounts_external_users FOREIGN KEY checkings_accounts_external_users (external_users_id)
    REFERENCES external_users (id);

-- Reference: credit_card_account_external_users (table: credit_card_account)
ALTER TABLE credit_card_account ADD CONSTRAINT credit_card_account_external_users FOREIGN KEY credit_card_account_external_users (external_users_id)
    REFERENCES external_users (id);

-- Reference: savings_accounts_external_users (table: savings_accounts)
ALTER TABLE savings_accounts ADD CONSTRAINT savings_accounts_external_users FOREIGN KEY savings_accounts_external_users (external_users_id)
    REFERENCES external_users (id);

-- Reference: transaction_external_users (table: transaction)
ALTER TABLE transaction ADD CONSTRAINT transaction_external_users FOREIGN KEY transaction_external_users (payee)
    REFERENCES external_users (id);

-- End of file.


