
drop table if exists POST;
create table POST(
  ID bigint auto_increment primary key,
  TITLE varchar(255) not null,
  USER_ID bigint not null,
  foreign key(USER_ID) references USER(ID)
);

