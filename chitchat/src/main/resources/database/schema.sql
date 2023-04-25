
CREATE TABLE IF NOT EXISTS users(
    id                          int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
    username                    varchar(255) NOT NULL UNIQUE,
    email                       varchar(255) NOT NULL UNIQUE,
    password                    varchar(255) NOT NULL,
    is_enabled                  boolean,
    is_account_non_expired      boolean,
    is_account_non_locked       boolean,
    is_credentials_non_expired  boolean,
    PRIMARY KEY (id)
);


-- CREATE SEQUENCE IF NOT EXISTS user_id_seq START WITH 2 INCREMENT BY 1;


CREATE TABLE IF NOT EXISTS roles(
    id     int4 NOT NULL GENERATED ALWAYS AS IDENTITY,
    name   varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);
-- CREATE SEQUENCE IF NOT EXISTS role_id_seq START WITH 3 INCREMENT BY 1;


CREATE TABLE IF NOT EXISTS users_roles(
    user_id     int4,
    role_id     int4,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_authorities_users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_authorities_roles FOREIGN KEY (role_id) REFERENCES roles (id)
);

CREATE TABLE IF NOT EXISTS Languages(
    id varchar(2) PRIMARY KEY,
    language varchar(255) NOT NULL UNIQUE
);
