-- psql ... -f datamodel.sql

\set ON_ERROR_STOP true
DROP DATABASE IF EXISTS sb3_bug_repo;
DROP USER IF EXISTS sb3_bug_owner;
CREATE USER sb3_bug_owner WITH PASSWORD 'test1234';
\c sb3_bug_repo
SET ROLE sb3_bug_owner;

BEGIN;
CREATE TABLE root_entity (
    id bigint PRIMARY KEY
);

CREATE TABLE sub_entity (
    id bigint NOT NULL REFERENCES root_entity (id),
    name TEXT NOT NULL
);

INSERT INTO root_entity VALUES (1), (2), (3), (4), (5);

INSERT INTO sub_entity VALUES
   (1, 'first'), (1, 'shared-name-with-2'),
   (2, 'second'), (2, 'shared-name-with-2'), (2, 'unique-name-for-two'),
   (3, 'third'),
   (4, 'fourth'),
   (5, 'fifth')
;
COMMIT;
