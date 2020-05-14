CREATE SCHEMA `internet_shop` DEFAULT CHARACTER SET utf8;

CREATE TABLE `internet_shop`.`products`
(
    `product_id`    BIGINT(11)     NOT NULL AUTO_INCREMENT,
    `product_name`  VARCHAR(256)   NOT NULL,
    `price` DECIMAL(25, 2) NOT NULL,
    PRIMARY KEY (`product_id`),
    UNIQUE INDEX `product_id_UNIQUE` (`product_id` ASC) VISIBLE,
    UNIQUE INDEX `product_name_UNIQUE` (`product_name` ASC) VISIBLE
);

CREATE TABLE `internet_shop`.`users`
(
    `user_id`    BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `user_name`  VARCHAR(256) NOT NULL,
    `pass`  VARCHAR(256) NOT NULL,
    `login` VARCHAR(256) NOT NULL,
    PRIMARY KEY (`user_id`),
    UNIQUE INDEX `user_id_UNIQUE` (`user_id` ASC) VISIBLE,
    UNIQUE INDEX `user_login_UNIQUE` (`login` ASC) VISIBLE
);

CREATE TABLE `internet_shop`.`orders`
(
    `order_id` BIGINT(11) NOT NULL AUTO_INCREMENT,
    `user_id`  BIGINT(11) NOT NULL,
    PRIMARY KEY (`order_id`),
    UNIQUE KEY `order_id_UNIQUE` (`order_id`),
    KEY `orders_users_fk_idx` (`user_id`),
    CONSTRAINT `orders_users_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `internet_shop`.`roles`
(
    `role_id`   BIGINT(11)   NOT NULL AUTO_INCREMENT,
    `role_name` VARCHAR(256) NOT NULL,
    PRIMARY KEY (`role_id`),
    UNIQUE INDEX `role_id_UNIQUE` (`role_id` ASC) VISIBLE,
    UNIQUE INDEX `role_name_UNIQUE` (`role_name` ASC) VISIBLE
);

CREATE TABLE `internet_shop`.`shopping_carts_products`
(
    `cart_id`    BIGINT NOT NULL,
    `product_id` BIGINT NOT NULL,
    INDEX `cart_cart_fk_idx` (`cart_id` ASC) VISIBLE,
    INDEX `cart_product_fk_idx` (`product_id` ASC) VISIBLE,
    CONSTRAINT `cart_cart_fk`
        FOREIGN KEY (`cart_id`)
            REFERENCES `internet_shop`.`shopping_carts` (`cart_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `cart_product_fk`
        FOREIGN KEY (`product_id`)
            REFERENCES `internet_shop`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `internet_shop`.`shopping_carts_products`
(
    `order_id`   BIGINT(11) NOT NULL,
    `product_id` BIGINT(11) NOT NULL,
    INDEX `carts_orders_fk_idx` (`order_id` ASC) VISIBLE,
    INDEX `carts_products_fk_idx` (`product_id` ASC) VISIBLE,
    CONSTRAINT `carts_orders_fk`
        FOREIGN KEY (`order_id`)
            REFERENCES `internet_shop`.`orders` (`order_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `carts_products_fk`
        FOREIGN KEY (`product_id`)
            REFERENCES `internet_shop`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `internet_shop`.`users_roles`
(
    `user_id` BIGINT(11) NOT NULL,
    `role_id` BIGINT(11) NOT NULL,
    INDEX `users_roles_fk_idx` (`role_id` ASC) VISIBLE,
    INDEX `users_users_fk_idx` (`user_id` ASC) VISIBLE,
    CONSTRAINT `users_roles_fk`
        FOREIGN KEY (`role_id`)
            REFERENCES `internet_shop`.`roles` (`role_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `users_users_fk`
        FOREIGN KEY (`user_id`)
            REFERENCES `internet_shop`.`users` (`user_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);

CREATE TABLE `internet_shop`.`orders_products`
(
    `order_id`   BIGINT(11) NOT NULL,
    `product_id` BIGINT(11) NOT NULL,
    INDEX `orders_orders_fk_idx` (`order_id` ASC) VISIBLE,
    INDEX `orders_products_fk_idx` (`product_id` ASC) VISIBLE,
    CONSTRAINT `orders_orders_fk`
        FOREIGN KEY (`order_id`)
            REFERENCES `internet_shop`.`orders` (`order_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION,
    CONSTRAINT `orders_products_fk`
        FOREIGN KEY (`product_id`)
            REFERENCES `internet_shop`.`products` (`product_id`)
            ON DELETE NO ACTION
            ON UPDATE NO ACTION
);
