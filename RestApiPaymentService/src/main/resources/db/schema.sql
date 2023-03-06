create table "Payment_DAO" (
    id serial primary key,
    name_service varchar(2000),
    amount integer,
    metadate varchar(2000),
    correlation_id uuid,
    date_payment timestamp without time zone DEFAULT current_timestamp,
    status_payment varchar(2000),
    user_creator varchar(2000)
);

-- create table "Payment_DAO" (
--   id serial primary key,
--   name_service varchar(2000),
--   amount integer,
--   metadate varchar(2000),
--   correlation_id uuid,
--   date_payment timestamp without time zone,
--   status_payment varchar(2000),
--   user_creator varchar(2000) NOT NULL
-- );