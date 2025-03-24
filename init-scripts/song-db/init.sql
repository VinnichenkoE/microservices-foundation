drop table if exists song;

create table song (
    id          serial    not null primary key,
    name        text,
    artist      text,
    album       text,
    duration    text,
    year        text
);
