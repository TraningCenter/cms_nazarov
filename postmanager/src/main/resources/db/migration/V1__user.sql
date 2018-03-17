
drop table if exists USER;
create table USER(
  ID bigint auto_increment primary key,
  USERNAME varchar(255) not null unique,
  PASSWORD varchar(255) not null
);

INSERT INTO USER (USERNAME, PASSWORD) VALUES ('test-username1', 'test-password1');
INSERT INTO USER (USERNAME, PASSWORD) VALUES ('test-username2', 'test-password2');
