create table admin_user
(
    id          BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    username    VARCHAR(255) NOT NULL,
    password    VARCHAR(255) NOT NULL,
    name        VARCHAR(15)  NOT NULL,
    created_at  DATETIME(6)  NOT NULL,
    modified_at DATETIME(6)  NOT NULL,

    CONSTRAINT UC_Person UNIQUE (username)
);

create index admin_user_username_idx on admin_user (username)
