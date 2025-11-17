CREATE TABLE task(
   id UUID primary key,
   title VARCHAR(150) NOT NULL,
   description VARCHAR(250) NOT NULL,
   status VARCHAR(20) CHECK(status IN('TODO', 'DOING', 'DONE')),
   priority VARCHAR(20) CHECK(priority IN('LOW', 'MEDIUM', 'HIGH')),
   due_date DATE,
   project_id UUID,
   CONSTRAINT fk_task_project FOREIGN KEY(project_id) REFERENCES project(id)
);

