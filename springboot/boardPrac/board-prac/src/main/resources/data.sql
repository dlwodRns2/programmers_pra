show tables;
desc member;
desc user;
desc board;
ALTER TABLE member
    ADD COLUMN role VARCHAR(20) NOT NULL DEFAULT 'ROLE_MEMBER';
ALTER TABLE member MODIFY COLUMN password VARCHAR(100) NOT NULL;

select * from member;
select * from board;