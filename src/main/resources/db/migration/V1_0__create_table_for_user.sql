create table app_user
(
    id             int GENERATED ALWAYS AS IDENTITY,
    email          varchar(80)       not null,
    username       varchar(30)       not null,
    password       varchar(100)      not null,
    created_date    timestamp default current_timestamp not null,
    last_modified_date     timestamp,
    created_by     int,
    last_modified_by      int,
    deleted   boolean  default  false,
    primary key (id),
    constraint user_fk_created_by
        foreign key (created_by) references app_user (id),
    constraint user_fk_last_modified_by
        foreign key (last_modified_by) references app_user(id)
);


create table role
(
    id   int GENERATED ALWAYS AS IDENTITY,
    name varchar(64) not null,
    created_date    timestamp default current_timestamp not null,
    last_modified_date     timestamp,
    created_by     int,
    last_modified_by      int,
    deleted   boolean  default  false,
    primary key (id),
    constraint role_fk_created_by
        foreign key (created_by) references app_user (id),
    constraint role_fk_last_modified_by
        foreign key (last_modified_by) references app_user (id),
    constraint role_unique_name
        unique (name)
);

create table user_role
(
    role_id int not null,
    user_id int not null,
    primary key (role_id, user_id),
    created_date    timestamp default current_timestamp,
    last_modified_date     timestamp,
    created_by     integer,
    last_modified_by      integer,
    deleted   boolean  default  false,
    constraint user_role_fk_created_by
        foreign key (created_by) references app_user (id),
    constraint user_role_fk_last_modified_by
        foreign key (last_modified_by) references app_user (id),
    constraint user_role_fk_role_id
        foreign key (role_id) references role (id),
    constraint user_role_fk_user_id
        foreign key (user_id) references app_user (id)
);