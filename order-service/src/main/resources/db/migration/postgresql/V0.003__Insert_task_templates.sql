insert into task_templates(name, type, description) values ('Initialize Order', 'AUTOMATIC_TASK', 'Task for order and instances creating. Also this task starts provisioning flow.');
insert into task_templates(name, type, description) values ('Reserve SIM Card', 'RESERVE_TASK', 'Reserve SIM Card for the user.');
insert into task_templates(name, type, description) values ('Reserve Device', 'RESERVE_TASK', 'Reserve the device for the user.');
insert into task_templates(name, type, description) values ('Reserve Cable', 'RESERVE_TASK', 'Reserve specified cable length for the user.');
insert into task_templates(name, type, description) values ('Notify User', 'NOTIFY_TASK', 'Remind the user about the order delivery date.');
insert into task_templates(name, type, description) values ('Notify the fitter about installation date', 'NOTIFY_TASK', 'Send notification to the fitter about the order installation date.');
insert into task_templates(name, type, description) values ('Activate 5G', 'AUTOMATIC_TASK', 'Activate 5G for SIM card.');
insert into task_templates(name, type, description) values ('Waiting Fitter Confirmation of Installation', 'EVENT_LISTENER_TASK', 'Waiting when fitter confirm installation.');
insert into task_templates(name, type, description) values ('Waiting MAC Address for Internet connection', 'EVENT_LISTENER_TASK', 'Waiting when fitter enter MAC-address of user.');
insert into task_templates(name, type, description) values ('Waiting Deliveryman Confirmation of Delivery', 'EVENT_LISTENER_TASK', 'Waiting when the deliveryman confirm success delivery.');
insert into task_templates(name, type, description) values ('Connect User to Network', 'AUTOMATIC_TASK', 'Finalize user connection to network.');
insert into task_templates(name, type, description) values ('Activate DTV Channels', 'AUTOMATIC_TASK', 'Finalize activation of DTV.');
insert into task_templates(name, type, description) values ('Provide Fixed IP', 'AUTOMATIC_TASK', 'Providing fixed IP to user.');
insert into task_templates(name, type, description) values ('NRC Payment', 'AUTOMATIC_TASK', 'Get NRC payment from the card.');
insert into task_templates(name, type, description) values ('Complete Order', 'AUTOMATIC_TASK', 'Final processes for order fulfilment.');

insert into tt_products values (1, 'Mobile Product');
insert into tt_products values (1, 'Internet Product');
insert into tt_products values (1, 'DTV Product');
insert into tt_products values (2, 'Mobile Product');
insert into tt_products values (3, 'Internet Product');
insert into tt_products values (3, 'Mobile Product');
insert into tt_products values (3, 'DTV Product');
insert into tt_products values (4, 'Internet Product');
insert into tt_products values (5, 'Internet Product');
insert into tt_products values (5, 'Mobile Product');
insert into tt_products values (5, 'DTV Product');
insert into tt_products values (6, 'Internet Product');
insert into tt_products values (6, 'DTV Product');
insert into tt_products values (7, 'Mobile Product');
insert into tt_products values (8, 'Internet Product');
insert into tt_products values (8, 'DTV Product');
insert into tt_products values (9, 'Internet Product');
insert into tt_products values (10, 'Mobile Product');
insert into tt_products values (11, 'Internet Product');
insert into tt_products values (12, 'DTV Product');
insert into tt_products values (13, 'Internet Product');
insert into tt_products values (14, 'Internet Product');
insert into tt_products values (14, 'Mobile Product');
insert into tt_products values (14, 'DTV Product');
insert into tt_products values (15, 'DTV Product');
insert into tt_products values (15, 'Internet Product');
insert into tt_products values (15, 'Mobile Product');

insert into task_template_params(tt_id, param_name, "value") values (8, 'after_delivery', 'true');
insert into task_template_params(tt_id, param_name, "value") values (9, 'after_delivery', 'true');
insert into task_template_params(tt_id, param_name, "value") values (10, 'after_delivery', 'true');
insert into task_template_params(tt_id, param_name, "value") values (11, 'after_delivery', 'true');
insert into task_template_params(tt_id, param_name, "value") values (12, 'after_delivery', 'true');
insert into task_template_params(tt_id, param_name, "value") values (13, 'after_delivery', 'true');
insert into task_template_params(tt_id, param_name, "value") values (14, 'after_delivery', 'true');
insert into task_template_params(tt_id, param_name, "value") values (15, 'after_delivery', 'true');

insert into task_template_dependencies values (2, 1);
insert into task_template_dependencies values (3, 1);
insert into task_template_dependencies values (4, 1);
insert into task_template_dependencies values (5, 2);
insert into task_template_dependencies values (5, 3);
insert into task_template_dependencies values (5, 4);
insert into task_template_dependencies values (6, 3);
insert into task_template_dependencies values (6, 4);
insert into task_template_dependencies values (7, 2);
insert into task_template_dependencies values (7, 5);
insert into task_template_dependencies values (8, 6);
insert into task_template_dependencies values (9, 6);
insert into task_template_dependencies values (10, 5);
insert into task_template_dependencies values (11, 8);
insert into task_template_dependencies values (11, 9);
insert into task_template_dependencies values (12, 11);
insert into task_template_dependencies values (12, 8);
insert into task_template_dependencies values (13, 11);
insert into task_template_dependencies values (14, 13);
insert into task_template_dependencies values (14, 12);
insert into task_template_dependencies values (14, 11);
insert into task_template_dependencies values (14, 10);
insert into task_template_dependencies values (15, 14);