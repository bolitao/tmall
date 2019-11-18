create table category
(
    id   int auto_increment
        primary key,
    name varchar(255) null
);

create table order_
(
    id           int auto_increment
        primary key,
    orderCode    varchar(255) null,
    address      varchar(255) null,
    post         varchar(255) null,
    receiver     varchar(255) null,
    mobile       varchar(255) null,
    userMessage  varchar(255) null,
    createDate   datetime     null,
    payDate      datetime     null,
    deliveryDate datetime     null,
    confirmDate  datetime     null,
    uid          int          null,
    status       varchar(255) null
);

create index fk_order_user
    on order_ (uid);

create table orderitem
(
    id     int auto_increment
        primary key,
    pid    int null,
    oid    int null,
    uid    int null,
    number int null
);

create index fk_orderitem_product
    on orderitem (pid);

create index fk_orderitem_user
    on orderitem (uid);

create table product
(
    id            int auto_increment
        primary key,
    name          varchar(255) null,
    subTitle      varchar(255) null,
    originalPrice float        null,
    promotePrice  float        null,
    stock         int          null,
    cid           int          null,
    createDate    datetime     null
);

create index fk_product_category
    on product (cid);

create table productimage
(
    id   int auto_increment
        primary key,
    pid  int          null,
    type varchar(255) null
);

create index fk_productimage_product
    on productimage (pid);

create table property
(
    id   int auto_increment
        primary key,
    cid  int          null,
    name varchar(255) null
);

create index fk_property_category
    on property (cid);

create table propertyvalue
(
    id    int auto_increment
        primary key,
    pid   int          null,
    ptid  int          null,
    value varchar(255) null
);

create index fk_propertyvalue_property
    on propertyvalue (ptid);

create table review
(
    id         int auto_increment
        primary key,
    content    varchar(4000) null,
    uid        int           null,
    pid        int           null,
    createDate datetime      null
);

create index fk_review_product
    on review (pid);

create index fk_review_user
    on review (uid);

create table user
(
    id       int auto_increment
        primary key,
    name     varchar(255) null,
    password varchar(255) null,
    salt     varchar(255) null
);

