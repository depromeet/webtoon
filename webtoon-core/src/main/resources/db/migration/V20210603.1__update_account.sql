alter table account change device_id auth_token varchar(255);
insert into account ( auth_token, nickname, created_at, modified_at ) values ( 'testToken', 'tester', now(), now());
