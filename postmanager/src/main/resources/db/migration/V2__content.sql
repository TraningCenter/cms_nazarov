
drop table if exists CONTENT;
create table CONTENT(
  ID bigint auto_increment primary key,
  HASH varchar(512) not null unique);

INSERT INTO CONTENT (HASH) VALUES ('test-hash1');
INSERT INTO CONTENT (HASH) VALUES ('test-hash2');
INSERT INTO CONTENT (HASH) VALUES ('test-hash3');
