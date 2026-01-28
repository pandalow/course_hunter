CREATE DATABASE IF NOT EXISTS mockhunter;
USE mockhunter;

create table book
(
    id        bigint auto_increment
        primary key,
    name      text         not null,
    author    varchar(255) null,
    isbn      varchar(255) null,
    chapters  varchar(255) null,
    publisher varchar(255) null
)
    engine = InnoDB;

create table college
(
    id             bigint auto_increment
        primary key,
    name           varchar(255) not null,
    institution_id bigint       not null
)
    engine = InnoDB;

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
)
    engine = InnoDB;

create table course
(
    id               bigint auto_increment
        primary key,
    code             varchar(255) null,
    title            varchar(255) not null,
    semester         varchar(255) null,
    credits          int(3)       null,
    outline          text         null,
    outcomes         text         null,
    assessments      varchar(255) null,
    institution_name varchar(255) null,
    country_name     varchar(255) null
)
    engine = InnoDB;

create table course_book
(
    course_id bigint not null,
    book_id   bigint not null,
    primary key (course_id, book_id)
)
    engine = InnoDB;

create table course_comment
(
    id          bigint auto_increment comment 'Primary key identifier'
        primary key,
    content     text                                 not null comment 'Comment content',
    course_id   bigint                               not null comment 'Course identifier',
    user_id     bigint                               not null comment 'User identifier',
    level       int                                  not null comment 'Comment level: 0 for course, 1 for 0th level, 2 for 1st level',
    parent_id   bigint                               null comment 'The id of the replied comment, if none exists, this value is null',
    root_id     bigint                               null comment 'The id of the root comment, if none exists, the value is null. This id is used for easy categorization',
    is_deleted  tinyint(1) default 0                 not null comment 'If comment is deleted',
    create_time datetime   default CURRENT_TIMESTAMP not null comment 'Comment creation time'
)
    comment 'Table to store comments' engine = InnoDB;

create table course_rating
(
    id          bigint auto_increment comment 'ID for the rating, primary key'
        primary key,
    user_id     bigint                              not null comment 'User identifier',
    course_id   bigint                              not null comment 'Course identifier',
    rating      int                                 not null comment 'Rating value',
    content     text                                null comment 'Rating text',
    create_time datetime  default CURRENT_TIMESTAMP not null comment 'Creation time',
    update_time timestamp default CURRENT_TIMESTAMP not null on update CURRENT_TIMESTAMP comment 'Update time',
    constraint idx_unique_rating
        unique (user_id, course_id)
)
    comment 'Table to store ratings' engine = InnoDB;

create table course_teacher
(
    course_id  bigint not null,
    teacher_id bigint not null,
    primary key (course_id, teacher_id)
)
    engine = InnoDB;

create table course_ugc
(
    id                  bigint auto_increment comment 'automatically generated primary key'
        primary key,
    rating              double   null comment 'rating of the course',
    latest_comment      text     null comment 'most recent comment on the course',
    comments_count      int      null comment 'number of comments on the course',
    latest_comment_time datetime null comment 'timestamp of the most recent comment',
    country_id          bigint   null comment 'query by country',
    institution_id      bigint   null comment 'query by institution',
    credit              int      null comment 'query by credit',
    status              bigint   null
)
    comment 'user-generated content for courses' engine = InnoDB;

create table institution
(
    id           bigint auto_increment
        primary key,
    website      varchar(255) null,
    country_code varchar(255) not null,
    country_id   bigint       null,
    name         varchar(255) null
)
    engine = InnoDB;

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
)
    engine = InnoDB;

create table program
(
    id             bigint auto_increment
        primary key,
    name           varchar(255) not null,
    college_id     bigint       null,
    degree         varchar(255) null,
    code           varchar(255) null,
    duration       varchar(255) null,
    category       varchar(255) null,
    institution_id bigint       null
)
    engine = InnoDB;

create table program_course
(
    program_id              bigint      not null,
    course_id               bigint      not null,
    optional                varchar(20) null comment 'optional or required',
    program_course_semester varchar(20) null,
    year                    varchar(20) null,
    primary key (course_id, program_id)
)
    engine = InnoDB;

create table teacher
(
    id           bigint auto_increment
        primary key,
    name         varchar(255) not null,
    email        varchar(255) null,
    profile_link varchar(255) null
)
    engine = InnoDB;

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
)
    engine = InnoDB;

