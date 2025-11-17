CREATE TABLE project(
  id UUID PRIMARY KEY,
  name VARCHAR(100) NOT NULL,
  description VARCHAR(255),
  start_date DATE NOT NULL,
  end_date DATE
);