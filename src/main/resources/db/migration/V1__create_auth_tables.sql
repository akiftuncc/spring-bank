create table if not exists auth_user (
    id uuid primary key,
    email varchar(320) not null,
    password_hash varchar(60) not null,
    created_at timestamp not null default now(),
    updated_at timestamp not null default now()
);

create unique index if not exists uk_auth_user_email on auth_user (lower(email));

create table if not exists auth_user_roles (
    user_id uuid not null references auth_user(id) on delete cascade,
    role varchar(64) not null,
    primary key (user_id, role)
);