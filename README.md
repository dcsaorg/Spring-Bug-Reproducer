# Reproducer for behaviour difference between Spring-Boot 2.7 and 3.0

We observed a behaviour difference between Spring-Boot 2.7 and 3.0
with JPA.  To trigger the behaviour, the following features have to
interact:

 1) An entity needs a `@OneToMany` (or possibly a `@ManyToMany`) relation.
 2) The data extraction needs to use a `Specification` that traverses the `@OneToMany` relation from 1).
 3) The data extraction needs to be pageable (`PageRequest`).


## Behaviour observed

 * In 2.7, the pagination is applied to the root entity.
 * In 3.0, the pagination is applied to the "rows" of the combined query causing the page
   size to truncate/"split" objects and return fewer items than you would expect.

### The test case

To reproduce the problem, start by doing the [setup](#setup).

Invoke a GET request to `/root-entity?limit=3` and compare the result.
 * `curl http://localhost:8080/root-entity?limit=3`  # SB-2.7
 * `curl http://localhost:8081/root-entity?limit=3`  # SB-3.0

Observe that the `SB-2.7` (`:8080`) returns 3 items in the `content` attribute,
but `SB-3.0` (`:8081`) only returns 2 items.

### Spring-Boot 2.7 SQL Queries

Query 1:

        select rootentity0_.id as id1_0_ 
          from root_entity rootentity0_
    inner join root_entity_sub_entity subentitie1_ on rootentity0_.id=subentitie1_.root_entity_id
    inner join sub_entity subentity2_ on subentitie1_.sub_entity_id=subentity2_.id
    where 1=1 limit ?

Query 2:

        select count(rootentity0_.id) as col_0_0_ 
          from root_entity rootentity0_
    inner join root_entity_sub_entity subentitie1_ on rootentity0_.id=subentitie1_.root_entity_id
    inner join sub_entity subentity2_ on subentitie1_.sub_entity_id=subentity2_.id
    where 1=1

Query 3 and 4:

        select subentitie0_.root_entity_id as root_ent1_1_0_, subentitie0_.sub_entity_id as sub_enti2_1_0_, subentity1_.id as id1_2_1_, subentity1_.name as name2_2_1_
          from root_entity_sub_entity subentitie0_
    inner join sub_entity subentity1_ on subentitie0_.sub_entity_id=subentity1_.id
         where subentitie0_.root_entity_id=?

      
### Spring-Boot 3.0 SQL Queries


Query 1:

       select r1_0.id
         from root_entity r1_0 join root_entity_sub_entity s1_0 on r1_0.id=s1_0.root_entity_id
        where 1=1 offset ? rows fetch first ? rows only

Query 2:

       select s1_0.root_entity_id,s1_1.id,s1_1.name
         from root_entity_sub_entity s1_0
         join sub_entity s1_1 on s1_1.id=s1_0.sub_entity_id
        where s1_0.root_entity_id=?

Query 3:

       select s1_0.root_entity_id,s1_1.id,s1_1.name
         from root_entity_sub_entity s1_0
         join sub_entity s1_1 on s1_1.id=s1_0.sub_entity_id
        where s1_0.root_entity_id=?

## Setup

 1) Start with an SQL database.
 2) Load the SQL. There is a [psql-version](datamodel.sql) or a [plain-sql](plain-sql.sql).
    * If you use the plain-sql version, you may need to update the applications.yml files to
      tweak the connection settings.
 3) Compile and run the `SB-2.7`. It listens on localhost:8080 by default.
 4) Compile and run the `SB-3.0`. It listens on localhost:8081 by default.
