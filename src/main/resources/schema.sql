DROP TABLE customers;
CREATE TABLE customers (
    customer_id   BIGINT NOT NULL AUTO_INCREMENT,
    first_name    VARCHAR(50) NOT NULL,
    last_name     VARCHAR(50) NOT NULL,
    email_address VARCHAR(100) NOT NULL UNIQUE,
    address       VARCHAR(225),
    city          VARCHAR(50) NOT NULL,
    state         VARCHAR(25),
    zip           VARCHAR(10),
    CONSTRAINT pk_customers PRIMARY KEY (customer_id)
);
