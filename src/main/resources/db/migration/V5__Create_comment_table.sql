create table comment
(
	id bigint AUTO_INCREMENT primary key,
	parent_id bigint not null comment 'Parent id',
	type int not null comment 'Parent type',
	commentator int not null comment 'commentator id',
	gmt_create bigint not null comment 'date created',
	gmt_modified bigint not null comment 'modified date',
	like_count bigint default 0 comment 'likes'
);

