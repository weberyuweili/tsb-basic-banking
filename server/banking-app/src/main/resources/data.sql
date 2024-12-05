SET @customer_id = 'test';
SET @account_number_1 = '00-0000-0000000-00';
SET @account_number_2 = '00-0000-0000000-01';


INSERT INTO Customer (id, name, email, password, phone_number)
VALUES (@customer_id, 'Tester', 'test@tsb.co.nz', 'password', '0210000000');


INSERT INTO Account (account_number, name, customer_id)
VALUES  (@account_number_1, 'Cheque', @customer_id);

INSERT INTO Account (account_number, name, customer_id)
VALUES  (@account_number_2, 'Saving',@customer_id);


INSERT INTO Transaction (id, amount, description, transaction_date, account_number)
VALUES  ( 0, 100.50, 'default money', NOW(), @account_number_1);

INSERT INTO Transaction (id, amount, description, transaction_date, account_number)
VALUES  ( 0, 21.80, 'default money', NOW(), @account_number_2);
