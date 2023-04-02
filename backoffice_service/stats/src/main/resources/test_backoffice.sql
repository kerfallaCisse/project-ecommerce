CREATE DATABASE IF NOT EXISTS test_backoffice;

use test_backoffice;

CREATE TABLE  user(
	id bigint not null AUTO_INCREMENT,
    email Varchar(50),
    admin int,
    PRIMARY KEY(id)
);

CREATE TABLE statistic (
    id bigint not null AUTO_INCREMENT,
    number_of_user_connected int,
    number_of_user_compte int,
    number_of_user_commands int,
    number_of_abandoned_bag int,
    PRIMARY KEY(id)

);

CREATE TABLE color (
    id bigint not null AUTO_INCREMENT,
    color_name Varchar(50),
    number_of_commands int,
    PRIMARY KEY(id)
)