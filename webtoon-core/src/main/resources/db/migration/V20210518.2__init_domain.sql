CREATE TABLE account
(
    id          BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    device_id   VARCHAR(255) NOT NULL,
    nickname    VARCHAR(255) NOT NULL,
    created_at  DATETIME(6)  NOT NULL,
    modified_at DATETIME(6)  NOT NULL
);

CREATE TABLE author
(
    id          BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    name        VARCHAR(255) NOT NULL,
    created_at  DATETIME(6)  NOT NULL,
    modified_at DATETIME(6)  NOT NULL
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

CREATE TABLE webtoon_genre
(
    webtoon_id BIGINT       NOT NULL,
    genre      VARCHAR(255) NOT NULL,
    CONSTRAINT genre_webtoon_id_fk FOREIGN KEY (webtoon_id) REFERENCES webtoon (id)
);

CREATE TABLE rating
(
    id              BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    webtoon_id      BIGINT      NOT NULL,
    account_id      BIGINT      NOT NULL,
    story_score     DOUBLE,
    drawing_score   DOUBLE,
    created_at      DATETIME(6) NOT NULL,
    modified_at     DATETIME(6) NOT NULL,
    CONSTRAINT rating_webtoon_id_fk FOREIGN KEY (webtoon_id) REFERENCES webtoon (id),
    CONSTRAINT rating_account_id_fk FOREIGN KEY (account_id) REFERENCES account (id)
);

CREATE TABLE webtoon_rating_average
(
    id                  BIGINT      NOT NULL PRIMARY KEY AUTO_INCREMENT,
    webtoon_id          BIGINT      NOT NULL,
    total_story_score   DOUBLE,
    total_drawing_score DOUBLE,
    votes               BIGINT,
    story_average       DOUBLE,
    drawing_average     DOUBLE,
    total_average       DOUBLE,
    created_at          DATETIME(6) NOT NULL,
    modified_at         DATETIME(6) NOT NULL,
    CONSTRAINT rating_average_webtoon_id_fk FOREIGN KEY (webtoon_id) REFERENCES webtoon (id)
);
