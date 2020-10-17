DROP TABLE IF EXISTS university CASCADE;
CREATE TABLE university (
id BIGSERIAL PRIMARY KEY,
name TEXT
);

DROP TABLE IF EXISTS faculty CASCADE;
CREATE TABLE faculty (
id BIGSERIAL PRIMARY KEY,
name TEXT,
university_id BIGINT,
CONSTRAINT faculty_university_fk
FOREIGN KEY (university_id) REFERENCES university (id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS groups CASCADE;
CREATE TABLE groups (
id BIGSERIAL PRIMARY KEY,
name TEXT,
faculty_id BIGINT,
CONSTRAINT groups_faculty_fk
FOREIGN KEY (faculty_id) REFERENCES faculty (id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS student CASCADE;
CREATE TABLE student (
id BIGSERIAL PRIMARY KEY,
name TEXT,
group_id BIGINT,
CONSTRAINT student_group_fk
FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS department CASCADE;
CREATE TABLE department (
id BIGSERIAL PRIMARY KEY,
name TEXT,
faculty_id BIGINT,
CONSTRAINT department_faculty_fk
FOREIGN KEY (faculty_id) REFERENCES faculty (id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS room CASCADE;
CREATE TABLE room (
id BIGSERIAL PRIMARY KEY,
number TEXT,
university_id BIGINT,
CONSTRAINT room_university_fk
FOREIGN KEY (university_id) REFERENCES university (id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS subject CASCADE;
CREATE TABLE subject (
id BIGSERIAL PRIMARY KEY,
name TEXT,
hours INT,
faculty_id BIGINT,
CONSTRAINT subject_faculty_fk
FOREIGN KEY (faculty_id) REFERENCES faculty (id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS theme CASCADE;
CREATE TABLE theme (
id BIGSERIAL PRIMARY KEY,
description TEXT,
subject_id BIGINT,
CONSTRAINT theme_subject_fk
FOREIGN KEY (subject_id) REFERENCES subject (id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS teacher CASCADE;
CREATE TABLE teacher (
id BIGSERIAL PRIMARY KEY,
name TEXT,
faculty_id BIGINT,
CONSTRAINT teacher_faculty_fk
FOREIGN KEY (faculty_id) REFERENCES faculty (id) ON DELETE SET NULL,
department_id BIGINT,
CONSTRAINT teacher_department_fk
FOREIGN KEY (department_id) REFERENCES department (id) ON DELETE SET NULL
);

DROP TABLE IF EXISTS lesson CASCADE;
CREATE TABLE lesson (
id BIGSERIAL PRIMARY KEY,
date_time TIMESTAMP,
theme_id BIGINT,
CONSTRAINT lesson_theme_fk
FOREIGN KEY (theme_id) REFERENCES theme (id) ON DELETE SET NULL,
room_id BIGINT,
CONSTRAINT lesson_room_fk
FOREIGN KEY (room_id) REFERENCES room (id) ON DELETE SET NULL,
group_id BIGINT,
CONSTRAINT lesson_group_fk
FOREIGN KEY (group_id) REFERENCES groups (id) ON DELETE SET NULL,
teacher_id BIGINT,
CONSTRAINT lesson_teacher_fk
FOREIGN KEY (teacher_id) REFERENCES teacher (id) ON DELETE SET NULL,
university_id BIGINT,
CONSTRAINT lesson_university_fk
FOREIGN KEY (university_id) REFERENCES university (id) ON DELETE SET NULL
);
