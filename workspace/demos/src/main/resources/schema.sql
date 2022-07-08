drop table if exists movies CASCADE;

create table movies (
id bigint not null, 
rating integer not null, 
title varchar(255), 
release_year integer, 
primary key (id)
);