DROP TABLE IF EXISTS crawl_queue;
DROP TABLE IF EXISTS crawl_product_queue;
DROP TABLE IF EXISTS crawl_request;
DROP TABLE IF EXISTS category;

create table crawl_request
(
    request_id int(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    url        varchar(255) NOT NULL,
    code       varchar(100) DEFAULT ''
);

create table crawl_queue
(
    queue_id          int(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    target_date       DATETIME NOT NULL,
    status            enum ('NEW','IN_PROGRESS','FAILED','DONE'),
    cause             text,
    created_date      timestamp,
    last_updated_date timestamp default now() on update now(),
    index (target_date)
);

create table crawl_product_queue
(
    product_queue_id  int(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    queue_id          int(11) NOT NULL,
    request           varchar(255) DEFAULT '',
    status            enum ('NEW','IN_PROGRESS','FAILED','DONE'),
    cause             text,
    created_date      timestamp,
    last_updated_date timestamp default now() on update now()
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

insert into crawl_request(url, code)
VALUES ('https://shopee.vn/api/v4/search/search_items?match_id=%s&limit=%s&newest=%s',
        'GET_PRODUCT_BY_CAT'),
       ('https://shopee.vn/api/v4/pages/get_category_tree',
        'GET_ALL_CATEGORY');