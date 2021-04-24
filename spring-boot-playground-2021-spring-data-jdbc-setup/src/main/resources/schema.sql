use tigerdb;
drop table if exists user ;
drop table if exists team ;
drop table if exists agent ;

CREATE TABLE team(
    id int auto_increment primary key,
    team_name varchar(64)
);

CREATE TABLE agent(
    id int auto_increment primary key,
    user int references team (id),
    team int references team (id)
);

CREATE TABLE user (
    id int auto_increment primary key,
    nickname varchar(64) unique
);