create table genre (
                       id bigint not null,
                       genre varchar(255)
);

alter table genre add constraint FKepp4ts2sg64h9fo329l6doub6 foreign key (id) references webtoon;
