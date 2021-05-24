create table banner
(
    id                      BIGINT       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    webtoon_id              BIGINT,
    banner_type             VARCHAR(255) NOT NULL,
    caption                 VARCHAR(255) NOT NULL,
    priority                INT          NOT NULL,
    display_start_date_time DATETIME(6),
    display_end_date_time   DATETIME(6),
    created_at              DATETIME(6)  NOT NULL,
    modified_at             DATETIME(6)  NOT NULL,

    CONSTRAINT banner_webtoon_id_fk FOREIGN KEY (webtoon_id) REFERENCES webtoon (id)
);

create index banner_search_idx on banner (banner_type, display_start_date_time, display_end_date_time);
