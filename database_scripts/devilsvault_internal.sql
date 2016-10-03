CREATE TABLE internal_user (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
designation VARCHAR(15) NOT NULL,
address VARCHAR(50) NOT NULL,
city VARCHAR(20) NOT NULL,
state VARCHAR(20) NOT NULL,
country VARCHAR(20) NOT NULL,
pincode INT NOT NULL,
phone INT NOT NULL,
PRIMARY KEY(id)
) ENGINE=InnoDB;

CREATE TABLE external_user (
id INT NOT NULL AUTO_INCREMENT,
name VARCHAR(50) NOT NULL,
address VARCHAR(50) NOT NULL,
city VARCHAR(20) NOT NULL,
state VARCHAR(20) NOT NULL,
country VARCHAR(20) NOT NULL,
pincode INT NOT NULL,
phone INT NOT NULL,
PRIMARY KEY(id)
) ENGINE=InnoDB;

CREATE TABLE internal_log (
activity VARCHAR(255) NOT NULL,
userid INT NOT NULL,
details VARCHAR(255),
timestamp TIMESTAMP NOT NULL,
FOREIGN KEY(userid) REFERENCES internal_user(id) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE external_log (
activity VARCHAR(255) NOT NULL,
userid INT NOT NULL,
details VARCHAR(255),
timestamp TIMESTAMP NOT NULL,
FOREIGN KEY(userid) REFERENCES external_user(id) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE internal_request (
id INT NOT NULL AUTO_INCREMENT,
requesterid INT NOT NULL,
request_type VARCHAR(20) NOT NULL,
current_value VARCHAR(50) NOT NULL,
requested_value VARCHAR(50) NOT NULL,
status VARCHAR(20) NOT NULL,
approver VARCHAR(50) NOT NULL,
description VARCHAR(50),
timestamp_created TIMESTAMP NOT NULL,
timestamp_updated TIMESTAMP NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(requesterid) REFERENCES internal_user(id) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE external_request (
id INT NOT NULL AUTO_INCREMENT,
requesterid INT NOT NULL,
request_type VARCHAR(20) NOT NULL,
current_value VARCHAR(50) NOT NULL,
requested_value VARCHAR(50) NOT NULL,
status VARCHAR(20) NOT NULL,
approver VARCHAR(50) NOT NULL,
description VARCHAR(50),
timestamp_created TIMESTAMP NOT NULL,
timestamp_updated TIMESTAMP NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(requesterid) REFERENCES external_user(id) ON UPDATE CASCADE
) ENGINE=InnoDB;

CREATE TABLE account (
id INT NOT NULL AUTO_INCREMENT,
PRIMARY KEY(id)
) ENGINE=InnoDB;

CREATE TABLE transaction (
id INT NOT NULL AUTO_INCREMENT,
payer_id INT NOT NULL,
payee_id INT NOT NULL,
amount DECIMAL NOT NULL,
hashvalue VARCHAR(255) NOT NULL,
transaction_type VARCHAR(20) NOT NULL,
description VARCHAR(255),
status varchar(20) NOT NULL,
approver VARCHAR(50) NOT NULL,
critical BOOLEAN NOT NULL,
timestamp_created TIMESTAMP NOT NULL,
timestamp_updated TIMESTAMP NOT NULL,
PRIMARY KEY(id),
FOREIGN KEY(payer_id) REFERENCES account(id)  ON UPDATE CASCADE,
FOREIGN KEY(payee_id) REFERENCES account(id)  ON UPDATE CASCADE
) ENGINE=InnoDB;
