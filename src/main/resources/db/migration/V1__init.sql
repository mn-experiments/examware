CREATE TABLE student(
id SERIAL PRIMARY KEY,
name VARCHAR(200) NOT NULL,
has_payed_fee BOOLEAN NOT NULL DEFAULT FALSE,
lesson_count INT NOT NULL DEFAULT 0
);

CREATE TABLE exam(
id SERIAL PRIMARY KEY,
name VARCHAR(200) NOT NULL
);

CREATE TYPE attempt_status
AS ENUM('pending', 'passed', 'failed');

CREATE TABLE attempt(
id SERIAL PRIMARY KEY,
attempt_date TIMESTAMPTZ NOT NULL,
status ATTEMPT_STATUS NOT NULL DEFAULT 'pending',
score INTEGER,
student_id INTEGER NOT NULL,
exam_id INTEGER NOT NULL,
CONSTRAINT fk_student
    FOREIGN KEY(student_id)
    REFERENCES student(id),
CONSTRAINT fk_exam
    FOREIGN KEY(exam_id)
    REFERENCES exam(id)
);