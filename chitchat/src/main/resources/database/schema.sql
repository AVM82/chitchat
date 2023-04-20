
CREATE TABLE IF NOT EXISTS users(
    id                          integer,
    username                    varchar(255) UNIQUE,
    email                       varchar(255) UNIQUE,
    password                    varchar(255),
    is_enabled                  boolean,
    is_account_non_expired      boolean,
    is_account_non_locked       boolean,
    is_credentials_non_expired  boolean,
    PRIMARY KEY (id)
);


-- CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 2 INCREMENT BY 1;


CREATE TABLE IF NOT EXISTS roles(
    id     integer,
    name   varchar(255),
    PRIMARY KEY (id)
);
-- CREATE SEQUENCE IF NOT EXISTS role_id_seq START WITH 3 INCREMENT BY 1;


CREATE TABLE IF NOT EXISTS users_roles(
    user_id     integer,
    role_id     integer,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_authorities_users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_authorities_roles FOREIGN KEY (role_id) REFERENCES roles (id)
);
