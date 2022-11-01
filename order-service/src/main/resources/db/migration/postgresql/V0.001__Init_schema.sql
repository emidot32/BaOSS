
create table users
(
    user_id          bigserial primary key,
    activity_status  boolean,
    balance          double precision not null,
    birthday         timestamp,
    contract_number  integer,
    email            varchar(255) unique,
    gender           varchar(10),
    id_card_number   varchar(255),
    login            varchar(255) unique,
    min_refresh_date timestamp,
    name             varchar(255)     not null,
    password         varchar(255)     not null,
    phone_number     varchar(255),
    reg_date         timestamp,
    usr_role         varchar(30),
    surname          varchar(255)     not null
);

alter table users
    owner to postgres;

create table user_address
(
    user_id    bigint not null
        constraint fkrmincuqpi8m660j1c57xj7twr
            references users,
    address_id bigint not null,
    primary key (user_id, address_id)
);

alter table user_address
    owner to postgres;

create table billing_accounts
(
    ba_id          bigserial primary key,
    account_number varchar(255)
        constraint uk_mp4dike4hobtud8d6airfrwfn
            unique,
    balance        double precision,
    user_id        bigint
        constraint fk2vx798osst5aql968arkrnoox
            references users
);

alter table billing_accounts
    owner to postgres;

create table payments
(
    payment_id   bigserial primary key,
    external_id  varchar(255),
    payment_date timestamp        not null,
    purpose      varchar(255)     not null,
    value        double precision not null,
    from_user      bigint
        constraint fk9w5347oaitk2sdsaq4imt1n1l
            references users
);

alter table payments
    owner to postgres;

create table buildings
(
    building_id     bigserial primary key,
    building_number varchar(255) not null,
    city            varchar(255) not null,
    country         varchar(255) not null,
    index           varchar(255),
    latitude        varchar(255),
    longitude       varchar(255),
    street          varchar(255) not null
);

alter table buildings
    owner to postgres;

create table addresses
(
    address_id  bigserial primary key,
    entrance    varchar(255),
    room_number varchar(255) not null,
    building_id bigint
        constraint fksp3ju7my7vgy0dxvrtfp4uev3
            references buildings
);

alter table addresses
    owner to postgres;

create table employees
(
    empl_id        bigserial primary key,
    dismissal_date date,
    empl_date      date         not null,
    position       varchar(255) not null,
    salary         integer,
    user_id        bigint constraint empl_user_fk references users
);

alter table employees
    owner to postgres;

create table instances
(
    instance_id       bigserial primary key,
    activated_date    timestamp,
    disconnected_date timestamp,
    expired_date      timestamp,
    status            varchar(255) not null,
    user_id           bigint constraint instance_user_fk references users
);

alter table instances
    owner to postgres;

create table internet_products
(
    inet_prod_id      bigserial primary key,
    cable_len         integer,
    device            varchar(255),
    fixed_ip          varchar(255),
    internet_offer_id varchar(255),
    ipv6_support      boolean,
    address_id        bigint
        constraint uk_2by62eft6y3dw4d196t1s4dcy
            unique
        constraint fka7l351likvih6o13j81cb61x
            references addresses,
    instance_id       bigint
        constraint uk_95qfbji5tgcesqdg5f9tgh47o
            unique
        constraint fkr0c5wa9yfxbtlj4k7ptc431wg
            references instances
);

alter table internet_products
    owner to postgres;

create table dtv_products
(
    dtv_prod_id  bigserial primary key,
    dtv_offer_id varchar(255),
    instance_id  bigint
        constraint uk_bm6a3a5ie07irm10t7qv0d3gt
            unique
        constraint fkmcuv4f9ghlr23kfy6qnclm86q
            references instances,
    inet_prod_id bigint
        constraint uk_shv5ivex5xgk86d76xtdxlp7t
            unique
        constraint fk1dtuqtaxnr6htgykxnwhnmbpk
            references internet_products
);

alter table dtv_products
    owner to postgres;

create table mobile_products
(
    mob_prod_id  bigserial primary key,
    balance      integer,
    phone_number varchar(255),
    support_5g   boolean,
    tariff_id    varchar(255),
    instance_id  bigint
        constraint uk_cikjjwojdqub0eycgfp320ui1
            unique
        constraint fk90ficy8gqg8cw68vqj2vesbj8
            references instances
);

alter table mobile_products
    owner to postgres;

create table orders
(
    order_id        bigserial primary key,
    completion_date timestamp,
    order_aim       varchar(255) not null,
    products        varchar(255),
    start_date      timestamp    not null,
    status          varchar(255) not null,
    total_mrc       double precision,
    total_nrc       double precision,
    user_id         bigint constraint order_user_fk references users,
    workers_num     integer,
    instance_id     bigint
        constraint fkfppshi5mr64j5lsyp8g9vxl7s
            references instances
);

alter table orders
    owner to postgres;

create table deliveries
(
    delivery_id   bigserial primary key,
    delivery_date timestamp    not null,
    duration      integer,
    need_info     boolean,
    status        varchar(255) not null,
    address_id    bigint
        constraint fkc4e0kf56gepm3hv3gqggqrmlb
            references addresses,
    order_id      bigint
        constraint uk_k36n9p5v7dd96hpgkwybvbogt
            unique
        constraint fk7isx0rnbgqr1dcofd5putl6jw
            references orders,
    responsible   bigint
        constraint fksehswm76pstxm17mksixreqyw
            references employees
);

alter table deliveries
    owner to postgres;

create table delivery_worker
(
    empl_id     bigint not null
        constraint fks3l79lxxv54rgn38lxbo9ktgo
            references employees,
    delivery_id bigint not null
        constraint fkoh5mk02qlgs3pkfr9rtabnq3w
            references deliveries,
    primary key (delivery_id, empl_id)
);

alter table delivery_worker
    owner to postgres;

create table task_templates
(
    tt_id       bigserial primary key,
    action_name varchar(255),
    description varchar(255),
    name        varchar(255) not null,
    type        varchar(255) not null
);

alter table task_templates
    owner to postgres;

create table task_template_dependencies
(
    tt_id         bigint not null
        constraint fkpywmlcwloog9bx3p9ggrcfi9h
            references task_templates,
    dependency_id bigint not null
        constraint fko8oayv93rx238x7w5n55bpp34
            references task_templates,
    primary key (tt_id, dependency_id)
);

alter table task_template_dependencies
    owner to postgres;

create table task_template_params
(
    tt_id      bigint       not null
        constraint fka8o7qewhhttx5ymcu4x7dd382
            references task_templates,
    value      varchar(255),
    param_name varchar(255) not null,
    primary key (tt_id, param_name)
);

alter table task_template_params
    owner to postgres;

create table tasks
(
    task_id         bigserial primary key,
    completion_date timestamp,
    start_date      timestamp,
    status          varchar(255) not null,
    order_id        bigint
        constraint fkn304qmduv2vertaty7l916eyl
            references orders,
    tt_id           bigint
        constraint fkfdkso6n52t7t07vmowgsjh34c
            references task_templates
);

alter table tasks
    owner to postgres;

create table errors
(
    error_id bigserial primary key,
    message  varchar(255),
    name     varchar(255) not null,
    task_id  bigint
        constraint fkhhpcxglem8wh1dmg24obsrq4j
            references tasks
);

alter table errors
    owner to postgres;

create table task_dependencies
(
    task_id       bigint not null
        constraint fkerlktvi2bud6uauih348u0loj
            references tasks,
    dependency_id bigint not null
        constraint fknwfxktx9emcwsylqofad5cxbx
            references tasks,
    primary key (task_id, dependency_id)
);

alter table task_dependencies
    owner to postgres;

create table tt_products
(
    tt_id    bigint not null
        constraint fkf7xgwur8l5fiiq85pm1si3y9u
            references task_templates,
    products varchar(255)
);

alter table tt_products
    owner to postgres;

create table used_device
(
    device_id    varchar(255) not null primary key,
    device_name  varchar(255) not null,
    device_num   integer      not null,
    dtv_prod_id  bigint
        constraint fkt1tbx43757j8qu5jag3f0p2oa
            references dtv_products,
    inet_prod_id bigint
        constraint fkhlpwsvk1v9w4m6oi9hatc3og7
            references internet_products
);

alter table used_device
    owner to postgres;
