set schema public;

drop table IF EXISTS CARD;
drop table IF EXISTS USER;

create table USER
(
    id   BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    name VARCHAR(100)                      NOT NULL
);
CREATE UNIQUE INDEX IF NOT EXISTS UNIQUE_USER ON USER (name);

create table CARD
(
    id           BIGINT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    number       VARCHAR(19)                       NOT NULL,
    balance      DECIMAL(20, 2) DEFAULT 0.0        NOT NULL,
    BANK_USER_ID BIGINT,
    foreign key (BANK_USER_ID) references USER (id)
);
CREATE UNIQUE INDEX IF NOT EXISTS UNIQUE_CARD ON CARD (number);