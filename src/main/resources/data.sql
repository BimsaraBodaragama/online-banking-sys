delete from user_role;
delete from role;
delete from user;

INSERT INTO role (role_id, name) VALUES
(1, 'User'),
(2, 'Admin');

INSERT INTO user (user_id, email, enabled, first_name, last_name, password, phone, username, primary_account_id, savings_account_id) VALUES
(1, 'bodaragamadb.office@gmail.com', b'1', 'Dinuka Bimsara', 'BodaragamaB', '$2a$10$s3WZpdg3jTTDWuXvfW5dvO0loovVQ58g8fUMAf09nqlw022WVHn9a', '0772922757', 'BimsaraB', 1, 1),
(2, 'admin@gmail.com', b'1', 'Admin', '1', '$2a$10$s3WZpdg3jTTDWuXvfW5dvO0loovVQ58g8fUMAf09nqlw022WVHn9a', '0779876543', 'Admin1', 2, 2),
(3, 'abc@gmail.com', b'1', 'Sakuni', 'Leader', '$2a$10$s3WZpdg3jTTDWuXvfW5dvO0loovVQ58g8fUMAf09nqlw022WVHn9a', '0711111111', 'Hirikitha', NULL, NULL);


INSERT INTO user_role (user_role_id, role_id, user_id) VALUES
(1, 1, 1),
(2, 2, 2);
