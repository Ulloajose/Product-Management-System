create table category
(
    id                    int GENERATED ALWAYS AS IDENTITY,
    name                  varchar(64) not null,
    created_date          timestamp,
    last_modified_date    timestamp,
    created_by            integer,
    last_modified_by      integer,
    deleted               boolean  default  false,
    primary key (id),
    constraint category_fk_created_by
        foreign key (created_by) references app_user (id),
    constraint category_fk_last_modified_by
        foreign key (last_modified_by) references app_user (id)
);

create table product
(
    id                    int GENERATED ALWAYS AS IDENTITY   ,
    price                 decimal not null,
    cost                  decimal not null,
    description           text,
    name                  varchar(150),

    created_date          timestamp,
    last_modified_date    timestamp,
    created_by            integer,
    last_modified_by      integer,
    deleted               boolean  default  false,
    primary key (id),
    constraint product_fk_created_by
        foreign key (created_by) references app_user (id),
    constraint product_fk_last_modified_by
        foreign key (last_modified_by) references  app_user(id)
);

create table product_category
(
    product_id              integer not null,
    category_id             integer not null,
    primary key (product_id, category_id),
    constraint product_category_fk_product_id
        foreign key (product_id) references product (id),
    constraint product_category_fk_category_id
        foreign key (category_id) references category(id)
);