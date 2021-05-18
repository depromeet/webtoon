CREATE TABLE account
(
    id          BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    device_id   VARCHAR(255) NOT NULL,
    nickname    VARCHAR(255) NOT NULL,
    created_at  DATETIME(6) NOT NULL,
    modified_at DATETIME(6) NOT NULL
);

CREATE TABLE author
(
    id          BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    created_at  DATETIME(6) NOT NULL,
    modified_at DATETIME(6) NOT NULL
);

CREATE TABLE webtoon
(
    id          BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    popularity  INT,
    site        VARCHAR(255) NOT NULL,
    summary     LONGTEXT,
    thumbnail   VARCHAR(255) NOT NULL,
    title       VARCHAR(255) NOT NULL,
    url         VARCHAR(255) NOT NULL,
    created_at  DATETIME(6),
    modified_at DATETIME(6)
);

CREATE TABLE webtoon_author
(
    webtoon_id BIGINT NOT NULL,
    author_id  BIGINT NOT NULL,
    CONSTRAINT webtoon_author_webtoon_id_fk FOREIGN KEY (webtoon_id) REFERENCES webtoon (id),
    CONSTRAINT webtoon_author_author_id_fk FOREIGN KEY (author_id) REFERENCES author (id)
);

CREATE TABLE webtoon_week_day
(
    webtoon_id BIGINT       NOT NULL,
    weekdays   VARCHAR(255) NOT NULL,
    CONSTRAINT week_day_webtoon_id_fk FOREIGN KEY (webtoon_id) REFERENCES webtoon (id)
);

CREATE TABLE review
(
    id            BIGINT   NOT NULL PRIMARY KEY AUTO_INCREMENT,
    account_id    BIGINT   NOT NULL,
    webtoon_id    BIGINT   NOT NULL,
    comment       LONGTEXT NOT NULL,
    drawing_score DOUBLE,
    story_score   DOUBLE,
    modified_at   DATETIME(6) NOT NULL,
    created_at    DATETIME(6) NOT NULL,
    CONSTRAINT review_account_id_fk FOREIGN KEY (account_id) REFERENCES account (id),
    CONSTRAINT review_webtoon_id_fk FOREIGN KEY (webtoon_id) REFERENCES webtoon (id)
);
