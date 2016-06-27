--default admin User
INSERT INTO USER_ROLES (USERNAME, ROLE) VALUES ('prit','ROLE_ADMIN'),('prit','ROLE_USER');
INSERT INTO USER_ROLES (USERNAME, ROLE) VALUES ('student','ROLE_STUDENT'),('student','ROLE_USER');



insert into users (username, password, enabled) values ('student','+XRmgFqcz3raUYTazgrblWfks3ezO9RzwCQwlqWROqI=', 1);
insert into users (username, password, enabled) values ('prit','Of2VibkzZuY1v0Ht9YJj+ZOK8tDPKbAvX6lBCy9YpVU=', 1);


--sample questions .
INSERT INTO QUESTIONS (QUES_TEXT, QUES_OP1, QUES_OP2, QUES_OP3, QUES_OP4,  QUES_MRKS, CORRECT_OPS)  VALUES 
('Ques1', 'ops1', 'ops2', 'op3', 'ops4', 4, '{"ops1", "ops3"}'),('Ques2', 'ops21', 'ops22', 'op23', 'ops24', 3, '{"ops1"}') ;

INSERT INTO USERS_DETAILS (USERNAME,FIRSTNAME,MIDDLENAME,LASTNAME,ADDRESS)
VALUES
('prit','Prithvish','Debashis','Mukherjee','Kolkata'),
('student','Prithvish','Debashis','Mukherjee','Kolkata');