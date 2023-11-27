insert into t_address (created,modified,street,house,city,country,locale) values(now(),now(),'Kupaly','12','Hrodno','Belarus','BY');
insert into t_producer (created,modified,name,address_id) values(now(),now(),'Conte',1);
insert into t_product(created,modified,ean,name,price,expiration_date,producer_id) values(now(),now(),'1234','sweater',50.0,'2027-9-15',1);
insert into t_product(created,modified,ean,name,price,expiration_date,producer_id) values(now(),now(),'5678','dress',50.0,'2026-7-04',1)