-- Should be single-quoted
-- Large number for ID: Workaround for creating user as Hibernate increment ID from 1 everytime
insert into user values(100001, sysdate(), 'AB');
insert into user values(100002, sysdate(), 'Jack');
insert into user values(100003, sysdate(), 'Jill');