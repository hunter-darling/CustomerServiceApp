DROP TABLE customers;
CREATE TABLE customers (
    customer_id   BIGINT NOT NULL AUTO_INCREMENT,
    first_name    VARCHAR(50),
    last_name     VARCHAR(50),
    email_address VARCHAR(100) UNIQUE,
    address       VARCHAR(225),
    city          VARCHAR(50),
    state         VARCHAR(25),
    zip           VARCHAR(10),
    CONSTRAINT pk_customers PRIMARY KEY (customer_id)
);
