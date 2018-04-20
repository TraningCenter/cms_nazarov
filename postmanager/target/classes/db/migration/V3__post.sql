
drop table if exists CONTENT;
create table CONTENT(
  ID bigint auto_increment primary key,
  HASH varchar(512) not null,
  POST_ID BIGINT not null,
  THROUGH_LINK bool not null,
  foreign key(POST_ID) references POST(ID));