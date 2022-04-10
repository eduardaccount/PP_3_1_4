insert into roles_table (id, name) values (1, 'USER');
insert into roles_table (id, name) values (2, 'ADMIN');
insert into users_table (user_age,user_email,user_name, user_password) values (25, 'user@gmail.com', 'user', '$2a$12$3SjRd2m.hryA6pn5aOgP.e9lGDVFESgwwkjBp8bxtGdvLZfZC9u6a');
insert into users_table (user_age,user_email,user_name, user_password) values (35, 'admin@gmail.com', 'admin', '$2a$12$GfAvtEYclTY91ry/qGyx/.YCQyKmp5beul/WcisH1BGzqcOON4mni');
insert into users_roles (row_id, user_id, role_id) values (1, 1, 1);
insert into users_roles (row_id, user_id, role_id) values (2, 2, 2);