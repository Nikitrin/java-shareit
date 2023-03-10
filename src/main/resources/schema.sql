drop table if exists bookings;
drop table if exists comments;
drop table if exists items;
drop table if exists requests;
drop table if exists users;

create table if not exists users
(
    id    bigint generated by default as identity not null,
    name  varchar(255)                            not null,
    email varchar(512)                            not null,
    constraint pk_user primary key (id),
    constraint uq_user_email unique (email)
);

create table if not exists requests
(
    id           bigint generated by default as identity not null,
    description  varchar(512)                            not null,
    requestor_id bigint                                  not null references users (id),
    constraint pk_request primary key (id)
);

create table if not exists items
(
    id           bigint generated by default as identity not null,
    name         varchar(255)                            not null,
    description  varchar(512)                            not null,
    is_available boolean                                 not null,
    owner_id      bigint                                  not null
        constraint items_users_fkey references users,
    request_id   bigint references requests (id),
    constraint pk_item primary key (id)
);

create table if not exists bookings
(
    id         bigint generated by default as identity not null,
    start_date timestamp without time zone             not null,
    end_date   timestamp without time zone             not null,
    item_id    bigint                                  not null references items (id),
    booker_id  bigint                                  not null references users (id),
    status     varchar(16)                             not null,
    constraint pk_booking primary key (id),
    constraint dt_start_before_end check (start_date < end_date)
);

create table if not exists comments
(
    id        bigint generated by default as identity not null,
    message   varchar(512)                            not null,
    item_id   bigint                                  not null references items (id),
    author_id bigint                                  not null references users (id),
    created   timestamp without time zone             not null,
    constraint pk_comment primary key (id)
)