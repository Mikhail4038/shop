CREATE TABLE employee (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(50),
    email VARCHAR(50),
    age INT
);

insert into employee(name,email,age) values('Mikhail','miha@yandex,ru',33)

CREATE TABLE address (
    id INT AUTO_INCREMENT PRIMARY KEY,
    city VARCHAR(255),
    state VARCHAR(255),
    employee_id INT,
    FOREIGN KEY (employee_id) REFERENCES employee(id)
);

insert into address(city,state,employee_id) values('Hrodno','Popovicha',1)

select em.name,  em.email, em.age, ad.city, ad.state from employee as em
join address as ad on ad.employee_id = em.id where ad.employee_id = 1