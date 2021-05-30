create table admin_user
(
    id          BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(15)  NOT NULL,
    password    VARCHAR(255) NOT NULL,
    username    VARCHAR(255) NOT NULL,
    created_at  DATETIME(6)  NOT NULL,
    modified_at DATETIME(6)  NOT NULL
);

create index admin_user_username_idx on admin_user (username)
