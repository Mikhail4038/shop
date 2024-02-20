insert into t_user_address (created,modified,street,house,city,country,postcode) values(now(),now(),'вуліца Вольгі Соламавай','38','Гродна','Беларусь','230027');
insert into t_user_address (created,modified,street,house,city,country,postcode) values(now(),now(),'вуліца Гагарына','35','Гродна','Беларусь','230024');

insert into t_user (created,modified,email,name,password,user_address_id) values(now(),now(),'mihaila4038@gmail.com','admin','$2a$12$t0KrYTJEy4N5LV6OA62YvOVoxq7tmBeaR2LoUO8rxp9ObdBlxjlN.',1);
insert into t_role(created, modified,name) values (now(),now(),'ADMIN');
insert into t_user_roles (users_id,roles_id) values(1,1);

insert into t_user (created,modified,email,name,password,user_address_id) values(now(),now(),'mihaila1984@yandex.by','user','$2a$12$t0KrYTJEy4N5LV6OA62YvOVoxq7tmBeaR2LoUO8rxp9ObdBlxjlN.',2);
insert into t_role(created, modified,name) values (now(),now(),'USER');
insert into t_user_roles (users_id,roles_id) values(2,2);
