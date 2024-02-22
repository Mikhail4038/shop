insert into t_producer_address (created,modified,street,house,city,country,postcode) values(now(),now(),'Kupaly','12','Hrodno','Belarus',230025);

insert into t_producer (created,modified,name,producer_address_id) values(now(),now(),'Conte',1);

insert into t_price(created,modified,cost,is_promotional) values(now(),now(),'45.0',1);
insert into t_price(created,modified,cost,is_promotional) values(now(),now(),'30.0',1);

insert into t_product(created,modified,ean,name,price_id,producer_id) values(now(),now(),'1234','sweater',1,1);
insert into t_product(created,modified,ean,name,price_id,producer_id) values(now(),now(),'5678','dress',2,1);

insert into t_review(created,modified,user_id,message,assessment,product_id) values(now(),now(),1,'A good product',9,1);
insert into t_review(created,modified,user_id,message,assessment,product_id) values(now(),now(),1,'A very nice product',10,2);

insert into t_rating (created,modified,average_assessment,count_reviews) values(now(),now(),9,1);
insert into t_rating (created,modified,average_assessment,count_reviews) values(now(),now(),10,1);

update t_product set rating_id=1 where id=1;
update t_product set rating_id=2 where id=2;