insert into t_producer_address (created,modified,street,house,city,country,postcode) values(now(),now(),'Kupaly','12','Hrodno','Belarus','230026');

insert into t_producer (created,modified,name,producer_address_id) values(now(),now(),'Conte',1);

insert into t_price(created,modified,cost,is_promotional) values(now(),now(),'65.0',1);
insert into t_price(created,modified,cost,is_promotional) values(now(),now(),'32.0',0);

insert into t_product(created,modified,ean,name,price_id,producer_id) values(now(),now(),'123','jersey',1,1);
insert into t_product(created,modified,ean,name,price_id,producer_id) values(now(),now(),'456','shorts',2,1);

insert into t_review(created,modified,user_id,message,assessment,product_id) values(now(),now(),1,'Beautiful thing',5,1);
insert into t_review(created,modified,user_id,message,assessment,product_id) values(now(),now(),1,'A very nice thing',10,2);

insert into t_rating (created,modified,average_assessment,count_reviews) values(now(),now(),5,1);
insert into t_rating (created,modified,average_assessment,count_reviews) values(now(),now(),10,1);

update t_product set rating_id=1 where id=1;
update t_product set rating_id=2 where id=2;