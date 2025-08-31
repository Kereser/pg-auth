INSERT INTO roles (role_id, name, description)
VALUES
(gen_random_uuid(), 'CLIENT', 'Registered client at Credi-Ya'),
(gen_random_uuid(), 'ADMIN', 'Admin role'),
(gen_random_uuid(), 'MANAGER', 'Manager role')