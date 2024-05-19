DROP TABLE IF EXISTS brand;
CREATE TABLE brand
(
    id             BIGINT      NOT NULL AUTO_INCREMENT COMMENT '브랜드 ID',
    brand_name     VARCHAR(30) NOT NULL COMMENT '브랜드명',
    created_at     datetime(6) DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at     datetime(6) DEFAULT NULL COMMENT '수정일시',
    deactivated_at datetime(6) DEFAULT NULL COMMENT '삭제일시',
    PRIMARY KEY (id),
    INDEX          idx_brand_name (brand_name)
);

DROP TABLE IF EXISTS product;
CREATE TABLE product
(
    id             BIGINT NOT NULL AUTO_INCREMENT,
    brand_id       BIGINT NOT NULL,
    price          BIGINT,
    category       INT    NOT NULL,
    created_at     datetime(6) DEFAULT CURRENT_TIMESTAMP COMMENT '생성일시',
    updated_at     datetime(6) DEFAULT NULL COMMENT '수정일시',
    deactivated_at datetime(6) DEFAULT NULL COMMENT '삭제일시',
    PRIMARY KEY (id),
    INDEX          idx_product_category (category)
);
