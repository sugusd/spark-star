create table if not exists server
(
    id       varchar(100) null,
    host     varchar(100) null,
    username varchar(100) null,
    password varchar(100) null,
    name     varchar(100) null,
    port     varchar(100) null,
    path     varchar(100) null,
    status   varchar(100) null
);

create table if not exists datasource
(
    id       varchar(100) null,
    name     varchar(100) null,
    comment varchar(100) null,
    url varchar(100) null,
    host     varchar(100) null,
    port     varchar(100) null,
    database     varchar(100) null,
    type   varchar(100) null,
    username   varchar(100) null,
    password   varchar(100) null
    );
