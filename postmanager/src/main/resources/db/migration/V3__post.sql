
drop table if exists POST;
create table POST(
  ID bigint auto_increment primary key,
  TITLE varchar(255) not null unique,
  USER_ID bigint not null,
  foreign key(USER_ID) references USER(ID)
);

drop table if exists POST_CONTENT;
create table POST_CONTENT(
  POST_ID bigint,
  CONTENT_ID bigint,
  primary key(POST_ID, CONTENT_ID),
  foreign key(POST_ID) references POST(ID),
  foreign key(CONTENT_ID) references CONTENT(ID)
);

INSERT INTO POST (TITLE, USER_ID) VALUES ('test-post1', '1');
INSERT INTO POST (TITLE, USER_ID) VALUES ('test-post2', '1');

INSERT INTO POST_CONTENT (POST_ID, CONTENT_ID) VALUES ('1', '1');
INSERT INTO POST_CONTENT (POST_ID, CONTENT_ID) VALUES ('1', '2');
INSERT INTO POST_CONTENT (POST_ID, CONTENT_ID) VALUES ('2', '3');
