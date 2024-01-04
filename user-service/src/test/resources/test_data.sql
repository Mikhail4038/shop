insert into t_user (created,modified,email,name,password) values(now(),now(),'test@gmail.com','test','$2a$12$t0KrYTJEy4N5LV6OA62YvOVoxq7tmBeaR2LoUO8rxp9ObdBlxjlN.');
insert into t_role(created, modified,name) values (now(),now(),'TEST');
insert into t_user_roles (users_id,roles_id) values(1,1);

insert into t_user (created,modified,email,name,password) values(now(),now(),'super@gmail.com','super','$2a$12$t0KrYTJEy4N5LV6OA62YvOVoxq7tmBeaR2LoUO8rxp9ObdBlxjlN.');
insert into t_role(created, modified,name) values (now(),now(),'SUPER_TEST');
insert into t_user_roles (users_id,roles_id) values(2,2);

insert into t_user_roles (users_id,roles_id) values(1,2);
