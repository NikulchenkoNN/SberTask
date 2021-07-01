set schema public;

drop table IF EXISTS USER;
drop table IF EXISTS CARD;

create table USER
(
    id   INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(100)                   NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS UNIQUE_USER ON USER(name);

create table CARD
(
    id           INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    number       VARCHAR(19)                    NOT NULL,
    BANK_USER_ID INT                            NOT NULL,
    foreign key (BANK_USER_ID) references USER (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS UNIQUE_CARD ON CARD(number);