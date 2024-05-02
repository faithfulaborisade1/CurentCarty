-- Create the 'cartymeats' database
CREATE DATABASE   cartymeats;
USE cartymeats;

use loginregister;

drop database cartymeats;

 
 
drop table if exists users;
drop table if exists product_client;
drop table if exists clients;
drop table if exists product;

-- Create the 'users' table
CREATE TABLE users (
    user_id INT AUTO_INCREMENT PRIMARY KEY,
    fullname VARCHAR(255) NOT NULL,
    username VARCHAR(255) UNIQUE NOT NULL,
    `password` TEXT NOT NULL,
    email VARCHAR(255) UNIQUE NOT NULL,
    contact VARCHAR(20)
);

select * from users;

select * from clients;

select * from priceSurvey;

drop table clients;
-- Create the 'client' table with client_id as the primary key
CREATE TABLE clients (
    client_id INT AUTO_INCREMENT PRIMARY KEY,
    client_name VARCHAR(255) NOT NULL,
    client_address VARCHAR(255) ,
    facing VARCHAR(255)
);
INSERT INTO clients (client_name, client_address, facing) VALUES
('SuperValu Lucan', 'Lucan, Co. Dublin, Ireland', 'A'),
('Centra Griffen', 'Griffen Valley, Co. Dublin, Ireland', 'C'),
('Dunnes Stores Clondalkin', 'The Mill Shopping Centre, Clondalkin, Dublin 22', 'B'),
('SuperValu Palmerstown', 'Palmerstown Shopping Centre, Palmerstown, Dublin 20', 'A');

drop table product;

CREATE TABLE product (
    prod_id INT AUTO_INCREMENT PRIMARY KEY,
    prod_name VARCHAR(255) NOT NULL,
    prod_pic VARCHAR(512) NOT NULL, 
    category ENUM('free_range', 'organic', 'pork_rashers', 'turkey') NOT NULL,
    description VARCHAR(255) NOT NULL,
    prod_stock INT,
    price DECIMAL(10, 2) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

SELECT p.prod_id, p.prod_name, p.prod_pic, p.category, p.description, p.prod_stock
FROM product p
JOIN product_client pc ON p.prod_id = pc.prod_id
JOIN clients c ON pc.client_id = c.client_id
WHERE c.client_id = 4;
 
 
-- Create the 'feedback' table
CREATE TABLE feedback (
    feed_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT NOT NULL,
    client VARCHAR(255) NOT NULL,
    product VARCHAR(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Create the 'orders' table
CREATE TABLE orders (
    order_id INT AUTO_INCREMENT PRIMARY KEY,
    quantity INT,
    client_id INT,
    order_date DATE,
    FOREIGN KEY (client_id) REFERENCES clients(client_id) ON UPDATE CASCADE ON DELETE SET NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE product_client (
    prod_id INT,
    client_id INT,
    FOREIGN KEY (prod_id) REFERENCES product(prod_id),
    FOREIGN KEY (client_id) REFERENCES clients(client_id),
    PRIMARY KEY (prod_id, client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE facing (
    facing_id INT AUTO_INCREMENT PRIMARY KEY,
    facing VARCHAR(255) NOT NULL,
    prod_id INT,
    client_id INT,
    FOREIGN KEY (prod_id) REFERENCES product(prod_id),
    FOREIGN KEY (client_id) REFERENCES clients(client_id),
    PRIMARY KEY (prod_id, client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE ord_pro (
    prod_id INT,
    order_id INT,
    FOREIGN KEY (prod_id) REFERENCES product(prod_id),
    FOREIGN KEY (order_id) REFERENCES clients(order_id),
    PRIMARY KEY (prod_id,order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;


CREATE TABLE priceSurvey (
    ps_id INT AUTO_INCREMENT PRIMARY KEY,
	location VARCHAR(255) NOT NULL,
    product VARCHAR(255) NOT NULL,
    barcode TEXT NOT NULL    
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

CREATE TABLE user_client (
    user_id INT,
    client_id INT,
    PRIMARY KEY (user_id, client_id),
    FOREIGN KEY (user_id) REFERENCES users(user_id),
    FOREIGN KEY (client_id) REFERENCES clients(client_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 COLLATE=utf8_unicode_ci;

-- Example insert to associate users with clients
INSERT INTO user_client (user_id, client_id) VALUES
(7, 8),-- User 'eoin' associated with SuperValu Palmerstown
(7, 7); -- User 'eoin' associated with SuperValu Palmerstown

SELECT c.client_id, c.client_name, c.client_address, c.facing
FROM clients c
JOIN user_client uc ON c.client_id = uc.client_id
WHERE uc.user_id = 4;

SELECT c.client_id, c.client_name, c.client_address, c.facing FROM clients c
            INNER JOIN user_client uc ON c.client_id = uc.client_id
            WHERE uc.user_id = 4;


INSERT INTO users (fullname, username, password, email, contact)
VALUES ('John Doe', 'john', '123', 'johndoe@example.com', '1234567890');

INSERT INTO users (fullname, username, password, email, contact)
VALUES ('Jane Smith', 'janesmith', 'password456', 'janesmith@example.com', '0987654321');



ALTER TABLE priceSurvey
ADD COLUMN price DECIMAL(10, 2) NOT NULL;



 

