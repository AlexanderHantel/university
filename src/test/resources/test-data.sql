INSERT INTO university (name) VALUES ('Stanford');
INSERT INTO university (name) VALUES ('CollTech');

INSERT INTO faculty (name, university_id) VALUES ('Arts', 1);
INSERT INTO faculty (name, university_id) VALUES ('Medicine', 1);

INSERT INTO groups (name, faculty_id) VALUES ('AR-01', 1);
INSERT INTO groups (name, faculty_id) VALUES ('AR-02', 1);
INSERT INTO groups (name, faculty_id) VALUES ('MD-01', 2);
INSERT INTO groups (name, faculty_id) VALUES ('MD-02', 2);

INSERT INTO student (name, group_id) VALUES ('Johnson', 1);
INSERT INTO student (name, group_id) VALUES ('Jameson', 1);
INSERT INTO student (name, group_id) VALUES ('Stevenson', 2);
INSERT INTO student (name, group_id) VALUES ('Harrison', 2);
INSERT INTO student (name, group_id) VALUES ('Black', 3);
INSERT INTO student (name, group_id) VALUES ('Brown', 3);
INSERT INTO student (name, group_id) VALUES ('White', 4);
INSERT INTO student (name, group_id) VALUES ('Green', 4);

INSERT INTO department (name, faculty_id) VALUES ('Classics', 1);
INSERT INTO department (name, faculty_id) VALUES ('Culture', 1);
INSERT INTO department (name, faculty_id) VALUES ('Nursing', 2);
INSERT INTO department (name, faculty_id) VALUES ('Midwifery', 2);

INSERT INTO room (number, university_id) VALUES ('A101', 1);
INSERT INTO room (number, university_id) VALUES ('B102', 1);
INSERT INTO room (number, university_id) VALUES ('C104', 2);
INSERT INTO room (number, university_id) VALUES ('D105', 2);

INSERT INTO subject (name, hours, faculty_id) VALUES ('Math', 12, 1);
INSERT INTO subject (name, hours, faculty_id) VALUES ('Biology', 12, 1);
INSERT INTO subject (name, hours, faculty_id) VALUES ('Geometry', 12, 2);
INSERT INTO subject (name, hours, faculty_id) VALUES ('Music', 12, 2);

INSERT INTO theme (description, subject_id) VALUES ('Aaaaaa', 1);
INSERT INTO theme (description, subject_id) VALUES ('Bbbbbb', 1);
INSERT INTO theme (description, subject_id) VALUES ('Cccccc', 2);
INSERT INTO theme (description, subject_id) VALUES ('Dddddd', 2);

INSERT INTO teacher (name, faculty_id, department_id) VALUES ('Edison', 1, 1);
INSERT INTO teacher (name, faculty_id, department_id) VALUES ('Tesla', 1, 2);
INSERT INTO teacher (name, faculty_id, department_id) VALUES ('Einstein', 2, 3);
INSERT INTO teacher (name, faculty_id, department_id) VALUES ('Howking', 2, 4);

INSERT INTO lesson (date_time, theme_id, room_id, group_id, teacher_id, university_id)
VALUES ('2000-01-01 09:00:00', 1, 1, 1, 1, 1);
INSERT INTO lesson (date_time, theme_id, room_id, group_id, teacher_id, university_id)
VALUES ('2000-01-01 11:00:00', 3, 2, 1, 2, 1);
