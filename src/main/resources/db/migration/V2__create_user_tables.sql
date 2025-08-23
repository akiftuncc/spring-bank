create table if not exists user_profile (
    id uuid primary key,
    auth_user_id uuid not null references auth_user(id) on delete cascade,
    first_name varchar(100),
    last_name varchar(100),
    phone varchar(15),
    date_of_birth date,
    street_address varchar(255),
    city varchar(100),
    state varchar(100),
    postal_code varchar(20),
    country varchar(100),
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

create unique index if not exists uk_user_profile_auth_user_id on user_profile (auth_user_id);