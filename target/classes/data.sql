DROP TABLE IF EXISTS task;
DROP TABLE IF EXISTS test;
DROP TABLE IF EXISTS question;
DROP TABLE IF EXISTS records;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS photo;
DROP TABLE IF EXISTS familymember;

CREATE TABLE task (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) NOT NULL,
  description VARCHAR(250) NOT NULL,
  prompt VARCHAR(1024) DEFAULT NULL,
  url VARCHAR(1024) DEFAULT NULL,
  schedule BIGINT,
  repeat TINYINT,
  repeatstart BIGINT,
  repeatend BIGINT,
  myuser VARCHAR(100) DEFAULT NULL,
  image BLOB
);

CREATE TABLE test (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) DEFAULT NULL,
  score INT,
  subscores VARCHAR(250) DEFAULT NULL,
  myuser VARCHAR(100) DEFAULT NULL,
  myrecord VARCHAR(100) DEFAULT NULL,
  starttime BIGINT DEFAULT NULL,
  endtime BIGINT DEFAULT NULL
);

CREATE TABLE question (
  qid BIGINT AUTO_INCREMENT PRIMARY KEY,
  test VARCHAR(250) DEFAULT NULL,
  testname VARCHAR(250) DEFAULT NULL,
  description VARCHAR(2048) NOT NULL,
  hint VARCHAR(1024) DEFAULT NULL,
  solution VARCHAR(2048) NOT NULL,
  answer VARCHAR(1024) DEFAULT NULL,
  timetoanswer BIGINT DEFAULT NULL,
  category VARCHAR(64) NOT NULL,
  score INT,
  image BLOB
);

CREATE TABLE records (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  rdate BIGINT,
  apptime BIGINT,
  phototime BIGINT,
  testtime BIGINT,
  testnumber INT,
  averagescore INT,
  commentnumber INT,
  myuser VARCHAR(250) DEFAULT NULL
);

CREATE TABLE users (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  firstname VARCHAR(64) NOT NULL,
  lastname VARCHAR(64) NOT NULL,
  description VARCHAR(2048) DEFAULT NULL,
  hobbies VARCHAR(1024) DEFAULT NULL,
  dob VARCHAR(64) NOT NULL,
  gender VARCHAR(64) NOT NULL,
  nickname VARCHAR(64) DEFAULT NULL,
  songs VARCHAR(2048) DEFAULT NULL,
);

CREATE TABLE familymember (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  firstname VARCHAR(64) NOT NULL,
  lastname VARCHAR(64) NOT NULL,
  hobbies VARCHAR(1024) DEFAULT NULL,
  description VARCHAR(1024) DEFAULT NULL,
  dob VARCHAR(64) NOT NULL,
  gender VARCHAR(64) NOT NULL,
  nickname VARCHAR(64) DEFAULT NULL,
  relationship VARCHAR(128) DEFAULT NULL,
  myuser VARCHAR(250) DEFAULT NULL
);

CREATE TABLE photo (
  id BIGINT AUTO_INCREMENT PRIMARY KEY,
  name VARCHAR(250) DEFAULT NULL,
  description VARCHAR(2048) DEFAULT NULL,
  title VARCHAR(250) DEFAULT NULL,
  personinpic VARCHAR(250) DEFAULT NULL,
  datetoshow VARCHAR(64) DEFAULT NULL,
  datecreated VARCHAR(64) DEFAULT NULL,
  datelastviewed VARCHAR(64) DEFAULT NULL,
  dob VARCHAR(64) DEFAULT NULL,
  comment VARCHAR(1024) DEFAULT NULL,
  ptype VARCHAR(64) DEFAULT NULL,
  myuser VARCHAR(100) DEFAULT NULL,
  task VARCHAR(100) DEFAULT NULL,
  myrecord VARCHAR(100) DEFAULT NULL,
  familymember VARCHAR(250) DEFAULT NULL,
  image BLOB
);

-- INSERT INTO task (id, name, description, url, schedule, repeat, image) VALUES
 -- ('1111', 'name1', 'This is name 1', 'http://sample1.com',1673317253, 0, '9fad5e9eefdfb449'),
 -- ('1112', 'name2', 'This is name 2','http://sample2.com', 1673317253, 1, '9fad5e9eefdfb449'),
 -- ('1113','name3', 'This is name 3','http://sample3.com', 1673317253, 2, '9fad5e9eefdfb449');
  
  
-- INSERT INTO test (id,name,score,subscores, starttime,endtime) VALUES
--  ('133','MaryB1-20-23','30/30','10/10,10/10/10/10',1673317253, 1673317253),
--  ('134','MaryB1-20-25','30/30','10/10,10/10/10/10',1673317253, 1673317253),
--  ('135','MaryB1-20-27','30/30','10/10,10/10/10/10',1673317253, 1673317253);
  
  
-- INSERT INTO question (qid, test, description, hint, solution, category) VALUES
--  ('2230',null, 'Which day of the week is today?', '', 'monday', 'general'),
--  ('2231',null, 'Which month of the year is it?', '', 'janurary', 'general'),
--  ('2232','133', 'How many 20 cent pieces are in $2.40?', 'relax', '12', 'money'),
--  ('2233','133', 'You are buying $19.40 of groceries. How much change would you receive back from a $20 note?', 'relax', '60, sixty', 'money'),
--  ('2234','133', 'Memory Test: Remember these 3 words: blue, bicycle, rose. In 5 minutes you will be asked to repeat them.', 'try to build a story using the words.','+blue, +bicycle, +rose', 'memory'),
--  ('2235','133', 'Memory Test: Remember these 3 words: elephant, cheese, jump. In 5 minutes you will be asked to repeat them.', 'try to build a story using the words.','+elephant, +cheese, +jump', 'memory'),
--  ('2236','133', 'How are a watch and a ruler similar? Write down how they are alike. They both are… what?', '','measure', 'relationship'),
--  ('2237','133', 'How are a candle and a lamp similar? Write down how they are alike. They both are… what?', '','bright, light', 'relationship');
  
  