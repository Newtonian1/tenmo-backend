BEGIN TRANSACTION;

DROP TABLE IF EXISTS tenmo_user, account, transfer;

DROP SEQUENCE IF EXISTS seq_user_id, seq_account_id, seq_transfer_id;

-- Sequence to start user_id values at 1001 instead of 1
CREATE SEQUENCE seq_user_id
  INCREMENT BY 1
  START WITH 1001
  NO MAXVALUE;

CREATE TABLE tenmo_user (
	user_id int NOT NULL DEFAULT nextval('seq_user_id'),
	username varchar(50) NOT NULL,
	password_hash varchar(200) NOT NULL,
	CONSTRAINT PK_tenmo_user PRIMARY KEY (user_id),
	CONSTRAINT UQ_username UNIQUE (username)
);

-- Sequence to start account_id values at 2001 instead of 1
-- Note: Use similar sequences with unique starting values for additional tables
CREATE SEQUENCE seq_account_id
  INCREMENT BY 1
  START WITH 2001
  NO MAXVALUE;

CREATE TABLE account (
	account_id int NOT NULL DEFAULT nextval('seq_account_id'),
	user_id int NOT NULL,
	balance decimal(13, 2) NOT NULL,
	CONSTRAINT PK_account PRIMARY KEY (account_id),
	CONSTRAINT FK_account_tenmo_user FOREIGN KEY (user_id) REFERENCES tenmo_user (user_id)
);

-- Sequence to start transfer_id values at 3001 instead of 1
CREATE SEQUENCE seq_transfer_id
  INCREMENT BY 1
  START WITH 3001
  NO MAXVALUE;

CREATE TABLE transfer (
	transfer_id int NOT NULL DEFAULT nextval('seq_transfer_id'),
	sender_id int NOT NULL,
	receiver_id int NOT NULL,
	amount decimal(13, 2) NOT NULL,
	status varchar(15) NOT NULL,
	date_time timestamp NOT NULL,
	CONSTRAINT PK_transfer PRIMARY KEY (transfer_id),
	CONSTRAINT FK_sender_account FOREIGN KEY (sender_id) REFERENCES account (account_id),
	CONSTRAINT FK_receiver_account FOREIGN KEY (receiver_id) REFERENCES account (account_id)
);

INSERT INTO tenmo_user (username, password_hash)
VALUES ('bob', '$2a$10$G/MIQ7pUYupiVi72DxqHquxl73zfd7ZLNBoB2G6zUb.W16imI2.W2'),
       ('user', '$2a$10$Ud8gSvRS4G1MijNgxXWzcexeXlVs4kWDOkjE7JFIkNLKEuE57JAEy');

INSERT INTO account (user_id, balance)
VALUES (1001, 950.00),
       (1002, 1050.00);

INSERT INTO transfer (sender_id, receiver_id, amount, status, date_time)
VALUES (2001, 2002, 50.00, 'Approved', '2022-08-24 04:44:00'),
        (2001, 2002, 10.00, 'Approved', '2022-08-24 04:45:00'),
        (2002, 2001, 10.00, 'Approved', '2022-08-24 04:46:00'),
        (2002, 2002, 100.00, 'Approved', '2022-08-24 04:47:00');

COMMIT;