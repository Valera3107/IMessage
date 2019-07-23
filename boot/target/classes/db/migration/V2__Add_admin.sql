insert into user(id, name, username, password, active)
 values (1, 'Paul', 'admin', '$2a$08$jbgCaTKsE909qD5blnx4Vu6Yb0NLD6N9ZamDgi3JkjK7TbGpNL80K', true);

insert into user_role(user_id, roles) values (1, 'USER'), (1, 'ADMIN');
