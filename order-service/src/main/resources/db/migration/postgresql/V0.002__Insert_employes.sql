insert into users(name, surname, login, password, email, balance, usr_role, activity_status, reg_date)
values ('Illia', 'Komisarov', 'admin', '$2a$10$8VC3Uo2H7PvTUAeHHZk17OgoQSLqDJt3HZTL/dc3EOWTMN0BCWDQi', 'admin@gmail.com', 0, 'ADMIN', true, now());

insert into users(name, surname, login, password, email, balance, usr_role, activity_status, reg_date)
values ('Vitaliy', 'Kirsanov', 'fitter1', '$2a$10$8VC3Uo2H7PvTUAeHHZk17OgoQSLqDJt3HZTL/dc3EOWTMN0BCWDQi', 'fitter1@gmail.com', 0, 'FITTER', true, now());

insert into users(name, surname, login, password, email, balance, usr_role, activity_status, reg_date)
values ('Oleksandr', 'Garin', 'fitter2', '$2a$10$8VC3Uo2H7PvTUAeHHZk17OgoQSLqDJt3HZTL/dc3EOWTMN0BCWDQi', 'fitter2@gmail.com', 0, 'FITTER', true, now());

insert into users(name, surname, login, password, email, balance, usr_role, activity_status, reg_date)
values ('Sergiy', 'Komarov', 'fitter3', '$2a$10$8VC3Uo2H7PvTUAeHHZk17OgoQSLqDJt3HZTL/dc3EOWTMN0BCWDQi', 'fitter3@gmail.com', 0, 'FITTER', true, now());

insert into users(name, surname, login, password, email, balance, usr_role, activity_status, reg_date)
values ('Roman', 'Belov', 'fitter4', '$2a$10$8VC3Uo2H7PvTUAeHHZk17OgoQSLqDJt3HZTL/dc3EOWTMN0BCWDQi', 'fitter4@gmail.com', 0, 'FITTER', true, now());

insert into users(name, surname, login, password, email, balance, usr_role, activity_status, reg_date)
values ('Valeriy', 'Romanenko', 'sales_manager', '$2a$10$8VC3Uo2H7PvTUAeHHZk17OgoQSLqDJt3HZTL/dc3EOWTMN0BCWDQi', 'sal_mag@gmail.com', 0, 'SALES_MANAGER', true, now());

insert into employees(user_id, position, empl_date, salary) values (2, 'Fitter', now(), 1000);
insert into employees(user_id, position, empl_date, salary) values (3, 'Fitter', now(), 1000);
insert into employees(user_id, position, empl_date, salary) values (4, 'Fitter', now(), 1000);
insert into employees(user_id, position, empl_date, salary) values (5, 'Fitter', now(), 1000);
insert into employees(user_id, position, empl_date, salary) values (6, 'Sales Manager', now(), 2000);