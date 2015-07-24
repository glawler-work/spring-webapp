create user "testUser" identified by "wordOfPass";
create table hello (id int primary key, value varchar(255));
insert into hello values (1, 'hello');

create table user (id int primary key, username varchar(255), password varchar(255));
insert into user values (1, 'user', 'password1');
