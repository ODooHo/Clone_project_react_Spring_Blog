insert into user(user_address, user_email, user_nickname, user_password, user_phone_number, user_profile) values
    ('test','dooho','두호','1234','1234',null),
    ('test','dooho2','두호2','1234','1234',null),
    ('test','dooho3','두호3','1234','1234',null),
    ('test','dooho4','두호4','1234','1234',null),
    ('test','dooho5','두호5','1234','1234',null);

insert into board(board_write_date, click_count, likes_count,comments_count,content, file, image, title, user_email, video) values
    ('2024-01-30',0,0,0,'content',null,null,'title','dooho',null),
    ('2024-01-30',0,0,0,'content',null,null,'title2','dooho2',null),
    ('2024-01-30',0,0,0,'content',null,null,'title3','dooho3',null),
    ('2024-01-30',0,0,0,'content',null,null,'title4','dooho4',null),
    ('2024-01-30',0,0,0,'content',null,null,'title5','dooho5',null);

insert into comment(board_id,id, comment_write_date, comment_content, user_email) VALUES
    (1,1,'2024-01-30','commentContent','dooho');

insert into liky(board_id, id, user_email) values
    (1,1,'dooho'),
    (1,2,'dooho2'),
    (1,3,'dooho3'),
    (2,4,'dooho'),
    (2,5,'dooho2'),
    (3,6,'dooho');
