drop table if exists resource;

create table resource (
    id         serial    not null    primary key,
    payload    bytea
);