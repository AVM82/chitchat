CREATE TABLE IF NOT EXISTS users
(
    id                         int4         NOT NULL GENERATED ALWAYS AS IDENTITY,
    username                   varchar(255) NOT NULL UNIQUE,
    email                      varchar(255) NOT NULL UNIQUE,
    password                   varchar(255) NOT NULL,
    is_enabled                 boolean,
    is_account_non_expired     boolean,
    is_account_non_locked      boolean,
    is_credentials_non_expired boolean,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS roles
(
    id   int4         NOT NULL GENERATED ALWAYS AS IDENTITY,
    name varchar(255) NOT NULL UNIQUE,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS users_roles
(
    user_id int4,
    role_id int4,
    PRIMARY KEY (user_id, role_id),
    CONSTRAINT fk_authorities_users FOREIGN KEY (user_id) REFERENCES users (id),
    CONSTRAINT fk_authorities_roles FOREIGN KEY (role_id) REFERENCES roles (id)
);


CREATE TABLE IF NOT EXISTS languages
(
    id   varchar(2) PRIMARY KEY,
    name varchar(255) NOT NULL UNIQUE
);


CREATE TABLE IF NOT EXISTS categories
(
    id       int4         NOT NULL GENERATED ALWAYS AS IDENTITY,
    name     varchar(255) NOT NULL UNIQUE,
    priority int4 DEFAULT 0,
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS chitchats
(
    id          int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    author_id   int4 REFERENCES users (id),
    header      varchar(100) NOT NULL,
    category_id int4 REFERENCES categories (id),
    description varchar(255),
    language_id varchar(2) REFERENCES languages (id),
    level       varchar(6)  NOT NULL,
    capacity    int4        NOT NULL,
    time        TIMESTAMP WITHOUT TIME ZONE,
    link        varchar(50),
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS chitchat_users
(
    chitchat_id int4,
    user_id     int4,
    PRIMARY KEY (chitchat_id, user_id),
    CONSTRAINT fk_participating_chitchats FOREIGN KEY (chitchat_id) REFERENCES chitchats (id),
    CONSTRAINT fk_participating_users FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE IF NOT EXISTS refresh_tokens
(
    id             int8         NOT NULL GENERATED ALWAYS AS IDENTITY,
    user_id        int4         UNIQUE,
    refresh_tokens varchar(255) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_participating_users FOREIGN KEY (user_id) REFERENCES users (id)
);


CREATE TABLE IF NOT EXISTS user_data
(
    user_id   int4 PRIMARY KEY,
    firstname varchar(20) NOT NULL,
    lastname varchar(20) NOT NULL,
    avatar varchar(100) NULL,
    gender varchar(6) NOT NULL,
    dob DATE NOT NULL,
    native_language varchar(2) NOT NULL,
    FOREIGN KEY(user_id) REFERENCES users(id)
    );


CREATE TABLE IF NOT EXISTS messages
(
    id                 int8 NOT NULL GENERATED ALWAYS AS IDENTITY,
    author_id          int8 REFERENCES users (id),
    chitchat_id        int8 REFERENCES chitchats (id),
    message            varchar(255) NOT NULL,
    created_time       TIMESTAMP WITHOUT TIME ZONE,
    subscription_type  varchar(50),
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS message_users
(
    messages_id   int8 REFERENCES messages (id),
    user_id       int4 REFERENCES users (id),
    read_status   boolean,
    PRIMARY KEY (messages_id, user_id)
);


CREATE TABLE IF NOT EXISTS permissions
(
    id      int4 GENERATED ALWAYS AS IDENTITY,
    name    varchar(20),
    PRIMARY KEY (id)
);


CREATE TABLE IF NOT EXISTS users_permissions
(
    user_id        int4 REFERENCES users (id),
    permission_id  int4 REFERENCES permissions (id),
    PRIMARY KEY (user_id, permission_id)
);


CREATE TABLE IF NOT EXISTS reminder_data
(
    chitchat_id   int8 REFERENCES chitchats (id),
    time          TIMESTAMP WITHOUT TIME ZONE,
    link          varchar(255),
    reminded      boolean,
    PRIMARY KEY (chitchat_id)
);


CREATE TABLE IF NOT EXISTS reminder_emails
(
    data_id        int8 REFERENCES reminder_data (chitchat_id),
    email          varchar(255)
);


CREATE TABLE IF NOT EXISTS translation
(
    id        int4,
    key       varchar(100),
    locale    varchar(2),
    message   varchar(2000),
    PRIMARY KEY (id)
);