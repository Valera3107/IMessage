create table message (
  id bigint not null auto_increment,
  file longtext,
  text varchar(600) not null,
  tag varchar(255),
  user_id bigint,
  primary key (id)) engine=MyISAM;

create table user (
  id bigint not null auto_increment,
  activation_code varchar(255),
  active bit, email varchar(255),
  name varchar(255),
  password varchar(255) not null,
  username varchar(255) not null,
  primary key (id)) engine=MyISAM;

create table user_role (
  user_id bigint not null,
  roles varchar(255)) engine=MyISAM;

alter table message
add constraint message_people_fk
foreign key (user_id) references user (id);

alter table user_role
add constraint people_user_role_fk
foreign key (user_id) references user (id);
