delete from user_role;
delete from user;

insert into user(id, name, username, password, active)
 values (1, 'Paul', 'admin', '$2a$08$jbgCaTKsE909qD5blnx4Vu6Yb0NLD6N9ZamDgi3JkjK7TbGpNL80K', true),
(2, 'b', 'b', '$2a$08$zSiLoCHhRZyrGWYBqnxVpeGxCNb9A9Mou6tUJA24nmxJJHypE1uC.', true);

insert into user_role(user_id, roles) values
(1, 'USER'), (1, 'ADMIN'),
(2, 'USER');
