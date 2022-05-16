-- Should be single-quoted
-- Large number for ID: Workaround for creating user as Hibernate increment ID from 1 everytime
insert into user values(100001, sysdate(), 'AB');
insert into user values(100002, sysdate(), 'Jack');
insert into user values(100003, sysdate(), 'Jill');

insert into postjpa values(110001, 'My First post', 'https://media.istockphoto.com/photos/hand-arranging-wood-block-stacking-as-step-stair-on-paper-pink-picture-id1169974807?s=612x612', 'Post title 1', 100001);
insert into postjpa values(110002, 'My Second post', 'https://media.istockphoto.com/photos/hand-arranging-wood-block-stacking-as-step-stair-on-paper-pink-picture-id1169974807?s=612x612,https://media.istockphoto.com/photos/software-developer-freelancer-working-at-home-picture-id1174690086?s=612x612', 'Post title 2', 100001);