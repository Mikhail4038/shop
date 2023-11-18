insert into t_user (email,password,name,enabled) values('admin@gmail.com','password','test',0);
insert into t_user (email,password,name,enabled) values('user@gmail.com','password','test',0);
insert into t_verification_token(token,expiry_date) values('value',now());
insert into t_verification_token(token,expiry_date,user_id) values('token',now(),2)