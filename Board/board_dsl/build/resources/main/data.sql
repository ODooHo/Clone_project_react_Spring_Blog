insert into user(user_address, user_email, user_nickname, user_password, user_phone_number, user_profile) values
                    ('test','dooho','두호','1234','1234',null);

insert into board(board_write_date, click_count, likes_count,comments_count,content, file, image, title, user_email, video) values
                    ('2024-01-30',0,0,0,'content',null,null,'title','dooho',null);

insert into comment(board_id,id, comment_write_date, comment_content, user_email) VALUES
                    (1,1,'2024-01-30','commentContent','dooho');