CREATE TABLE users
(
    id         varchar(60)  NOT NULL,
    "name"     varchar(60)  NOT NULL,
    email      varchar(255) NOT NULL,
    "password" varchar(60)  NULL,
    deleted    bool         NOT NULL,
    cart_id    varchar(60)  NULL,
    orders     json,
    cards      json,
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE product
(
    id       varchar(60) NOT NULL,
    "name"   varchar(60) NOT NULL,
    price    int4        NOT NULL,
    unit     varchar(60) NULL,
    quantity int4        NULL,
    CONSTRAINT firstkey PRIMARY KEY (id)
);

CREATE TABLE cart
(
    id               varchar(60) NOT NULL,
    items            json        NULL,
    user_id          varchar(60) NULL,
    total_price      varchar(60) NULL,
    update_at        date        NULL,
    canceledoractive bool        NOT NULL,
    CONSTRAINT cart_pkey PRIMARY KEY (id)
);

CREATE TABLE itemcart
(
    id                 varchar(60) NOT NULL,
    product_id         varchar(60) NULL,
    price_unit_product varchar(60) NULL,
    quantity           varchar(60) NULL,
    deleted            bool        NOT NULL,
    CONSTRAINT itemcart_pkey PRIMARY KEY (id)
);

create table salesorder
(
    id              varchar(60) not null,
    cart_id         varchar(60) not null,
    number          serial,
    date_generation date        not null,
    constraint order_pkey primary key (id)
);

CREATE TABLE creditCard
(
    id              varchar(60) not null,
    user_id         varchar(60) not null,
    number          varchar(100) not null,
    expiration_data date not null,
    card_name       varchar(60) not null
)