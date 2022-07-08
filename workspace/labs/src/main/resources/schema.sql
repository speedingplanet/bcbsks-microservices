drop table if exists members CASCADE;

create table members (
id bigint not null,
first_name varchar(255),
last_name varchar(255),
policy_holder boolean not null,
primary key (id)
);