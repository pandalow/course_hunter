CREATE DATABASE IF NOT EXISTS mockhunter;
create table college
(
    id             bigint auto_increment
        primary key,
    name           varchar(255) not null,
    institution_id bigint       not null
);

create table comment
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    content     text                 not null comment '评论内容',
    user_id     bigint               not null comment '发表评论的用户ID',
    course_id   bigint               null comment '关联的课程ID（可为空）',
    teacher_id  bigint               null comment '关联的教师ID（可为空）',
    is_deleted  tinyint(1) default 0 null comment '逻辑删除标识：0未删除，1已删除',
    create_time datetime(6)          null comment '创建时间'
)
    collate = utf8mb4_unicode_ci;

create index idx_course_id
    on comment (course_id);

create index idx_teacher_id
    on comment (teacher_id);

create index idx_user_id
    on comment (user_id);

create table country
(
    id            bigint auto_increment
        primary key,
    english_name  varchar(255) not null,
    country_code  varchar(255) not null,
    code          int          null,
    chinese_name  varchar(255) null,
    french_name   varchar(255) null,
    italian_name  varchar(255) null,
    japanese_name varchar(255) null,
    russian_name  varchar(255) null,
    german_name   varchar(255) null,
    spanish_name  varchar(255) null
);

create table course_teacher
(
    course_id  bigint not null,
    teacher_id bigint not null,
    primary key (course_id, teacher_id)
);

create table institution
(
    id           bigint auto_increment
        primary key,
    website      varchar(255) null,
    country_code varchar(255) not null,
    country_id   bigint       null,
    name         varchar(255) null
);

create table course
(
    id             bigint auto_increment
        primary key,
    code           varchar(255)  null,
    title          varchar(255)  not null,
    semester       varchar(255)  null,
    credits        int default 0 null,
    outline        text          null,
    outcomes       text          null,
    assessments    text          null,
    institution_id bigint        null,
    country_id     bigint        null,
    constraint fk_course_country
        foreign key (country_id) references country (id),
    constraint fk_course_institution
        foreign key (institution_id) references institution (id)
);

create table operation_log
(
    id             bigint auto_increment comment 'primary key'
        primary key,
    user_id        bigint       null comment 'operation user id',
    operation_time datetime     null comment 'operation time which record by system',
    class_name     varchar(255) null comment 'method class name',
    method_name    varchar(255) null comment 'operation method name',
    method_params  varchar(255) null comment 'operation args to String',
    return_values  varchar(255) null comment 'return values ',
    cost_time      bigint       null comment 'end time - begin time'
);

create table rating
(
    id          bigint auto_increment comment '主键ID'
        primary key,
    score       int          not null comment '评分分数 (例如1-5)',
    content     varchar(500) null comment '简短的评价理由',
    user_id     bigint       not null comment '评分用户ID',
    course_id   bigint       null comment '关联课程ID',
    teacher_id  bigint       null comment '关联教师ID（如果你想给老师也打分，建议预留）',
    create_time datetime(6)  null comment '创建时间',
    update_time datetime(6)  null comment '更新时间'
);

create index idx_course_id
    on rating (course_id);

create index idx_user_id
    on rating (user_id);

create table teacher
(
    id           bigint auto_increment
        primary key,
    name         varchar(255) not null,
    email        varchar(255) null,
    profile_link varchar(255) null
);

create table users
(
    id         bigint auto_increment
        primary key,
    google_id  varchar(50)                         null,
    email      varchar(255)                        not null,
    name       varchar(255)                        null,
    avatar     varchar(512)                        null,
    role       enum ('User', 'Admin')              not null,
    created_at timestamp default CURRENT_TIMESTAMP not null,
    updated_at timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP,
    constraint email
        unique (email),
    constraint google_id
        unique (google_id)
);

