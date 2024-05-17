DROP TABLE IF EXISTS brand;
CREATE TABLE brand
(
    id         BIGINT      NOT NULL AUTO_INCREMENT,
    brand_name VARCHAR(30) NOT NULL,
    PRIMARY KEY (id),
    INDEX      idx_brand_name (brand_name)
);

DROP TABLE IF EXISTS product;
CREATE TABLE product
(
    id       BIGINT NOT NULL AUTO_INCREMENT,
    brand_id BIGINT NOT NULL,
    price    BIGINT,
    category INT NOT NULL,
    PRIMARY KEY (id),
    INDEX    idx_product_category (category)
);
