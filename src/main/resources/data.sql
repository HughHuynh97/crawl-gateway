DROP TABLE IF EXISTS crawl_queue;
DROP TABLE IF EXISTS crawl_request;
DROP TABLE IF EXISTS category;

create table crawl_request
(
    request_id  int(11) UNSIGNED AUTO_INCREMENT PRIMARY KEY,
    url         varchar(255) NOT NULL,
    code varchar(100) DEFAULT ''
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

create table category
(
    cat_id       int(11) UNSIGNED PRIMARY KEY,
    code     varchar(100),
    name     varchar(100),
    image        varchar(100),
    level        int(6),
    parent_cat_id int(11),
    created_date      timestamp,
    last_updated_date timestamp default now() on update now()
);

insert into crawl_request(url, code)
VALUES ('https://shopee.vn/api/v4/search/search_items?by=:by&limit=60&match_id=:matchId&newest=:newest&order=desc&page_type=search&scenario=PAGE_OTHERS&version=2',
        'GET_PRODUCT'),
       ('https://shopee.vn/api/v4/pages/get_homepage_category_list', 'GET_HOME_CATEGORY'),
       ('https://shopee.vn/api/v4/banner/get_list?category_id=11035567&type=category&version=34f24216f732e2eba0eaf1ea2b5cde69',
        'GET_CATEGORY_BY_ROOT');