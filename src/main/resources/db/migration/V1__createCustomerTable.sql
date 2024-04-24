CREATE TABLE customer (
                           account_key varchar(9) primary key,
                           account_number varchar(12) not null unique,
                           first_name VARCHAR(50) not null,
                           last_name VARCHAR(50) not null,
                           email varchar(100) not null,
                           phone_number varchar(12) not null,
                           address text not null,
                           created_at timestamp without time zone default current_timestamp,
                           updated_at timestamp without time zone default null
);