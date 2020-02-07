alter table notification
    add notifier_name varchar(100) not null;

alter table notification
    add outer_title varchar(256) not null;