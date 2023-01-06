-- psql ... -f datamodel.sql

\set ON_ERROR_STOP true
DROP DATABASE IF EXISTS sb3_bug_repo;
DROP USER IF EXISTS sb3_bug_owner;
CREATE USER sb3_bug_owner WITH PASSWORD 'test1234';
CREATE DATABASE sb3_bug_repo WITH OWNER sb3_bug_owner;
\c sb3_bug_repo
SET ROLE sb3_bug_owner;

BEGIN;
DROP TABLE IF EXISTS root_entity CASCADE;
CREATE TABLE root_entity (
    id bigint PRIMARY KEY
);

DROP TABLE IF EXISTS sub_entity CASCADE;
CREATE TABLE sub_entity (
    id bigint PRIMARY KEY,
    name TEXT NOT NULL
);

DROP TABLE IF EXISTS root_entity_sub_entity CASCADE;
CREATE TABLE root_entity_sub_entity (
    root_entity_id bigint NOT NULL REFERENCES root_entity (id),
    sub_entity_id bigint NOT NULL REFERENCES sub_entity (id),
    UNIQUE (root_entity_id, sub_entity_id)
);

INSERT INTO root_entity VALUES (1), (2), (3), (4), (5);

INSERT INTO sub_entity VALUES
   (1, 'first'),
   (2, 'second'),
   (3, 'third'),
   (4, 'fourth'),
   (5, 'fifth'),
   (6, 'shared-name-between-1-2'),
   (7, 'unique-name-for-two')
;

INSERT INTO root_entity_sub_entity VALUES
  (1, 1),
  (1, 6),

  (2, 2),
  (2, 6),
  (2, 7),

  (3, 3),
  (4, 4),
  (5, 5)
;

COMMIT;
