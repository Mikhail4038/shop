insert into t_user_address (created,modified,street,house,city,country,locale) values(now(),now(),'Kupaly','12','Hrodno','Belarus','BY');

insert into t_user (created,modified,email,name,password) values(now(),now(),'admin@gmail.com','admin','$2a$12$t0KrYTJEy4N5LV6OA62YvOVoxq7tmBeaR2LoUO8rxp9ObdBlxjlN.');
insert into t_role(created, modified,name) values (now(),now(),'ADMIN');
insert into t_user_roles (users_id,roles_id) values(1,1);

insert into t_user (created,modified,email,name,password,user_address_id) values(now(),now(),'mihaila1984@yandex.by','user','$2a$12$t0KrYTJEy4N5LV6OA62YvOVoxq7tmBeaR2LoUO8rxp9ObdBlxjlN.',1);
insert into t_role(created, modified,name) values (now(),now(),'USER');
insert into t_user_roles (users_id,roles_id) values(2,2);
