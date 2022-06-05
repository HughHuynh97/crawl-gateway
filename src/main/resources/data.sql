DROP TABLE IF EXISTS crawl_queue;
DROP TABLE IF EXISTS crawl_request;
DROP TABLE IF EXISTS category;
DROP TABLE IF EXISTS product;
DROP TABLE IF EXISTS category_product;

create table crawl_request
(
    request_id int(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    url        varchar(255) NOT NULL,
    code       varchar(100) DEFAULT ''
);

create table crawl_queue
(
    queue_id          int(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    status            enum ('NEW','IN_PROGRESS','FAILED','DONE'),
    cause             text,
    start_date        DATETIME NOT NULL,
    end_date          DATETIME NOT NULL,
    created_date      timestamp,
    last_updated_date timestamp default now() on update now(),
    index (start_date, end_date)
);

create table category
(
    cat_id            int(11) UNSIGNED PRIMARY KEY,
    code              varchar(100),
    name              varchar(100),
    image             varchar(100),
    level             int(6),
    parent_cat_id     int(11),
    created_date      timestamp,
    last_updated_date timestamp default now() on update now()
);

create table product
(
    prod_id int(11) UNSIGNED PRIMARY KEY,
    shop_id int(11),
    name    varchar(255)

);

create table category_product
(
    cat_id  int(11) UNSIGNED,
    prod_id int(11) UNSIGNED,
    primary key (cat_id, prod_id)
);

insert into crawl_request(url, code)
VALUES ('https://shopee.vn/api/v4/search/search_items?match_id=%s&limit=%s&newest=%s',
        'GET_PRODUCT_BY_CAT'),
       ('https://shopee.vn/api/v4/pages/get_category_tree',
        'GET_ALL_CATEGORY');

