create table profiles
(
    id      serial primary key,
    name    varchar(100),
    email   varchar(100) unique,
    age     integer not null,
    created timestamp
);

create table errors
(
    id      serial primary key,
    msg     varchar(100),
    created timestamp
);