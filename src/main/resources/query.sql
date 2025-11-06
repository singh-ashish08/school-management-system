use mvm_school;
create table user_security ( id integer ,name varchar(30) primary key, password varchar(100));
insert into user_security values(1,"deepakamboj","deepa@123"),(2,"ashishsingh","ashish@123");
select * from user_security;