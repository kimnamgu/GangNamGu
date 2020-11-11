drop table tbl_board

select * from board




CREATE TABLE board (
    boardIdx   int not null auto_increment comment '게시판IDX',
    id       VARCHAR(20) DEFAULT NULL comment '아이디',
    title      VARCHAR(50) DEFAULT NULL comment '제목',
    content    VARCHAR(4000) DEFAULT NULL comment '내용',
    hitCnt      INT DEFAULT NULL comment '조회수',
    ip         VARCHAR(50) DEFAULT NULL comment '아이피',
    ins_dt timestamp comment '입력일',
    upt_dt timestamp comment '수정일',
    PRIMARY KEY ( boardIdx )
);

CREATE TABLE board_log (
    actionType varchar(10) comment '입력상태',
    boardIdx   int comment '게시판IDX',
    id       VARCHAR(20) comment '아이디',
    title      VARCHAR(50) comment '제목',
    content    VARCHAR(4000) comment '내용',
    hitCnt      INT comment '조회수',
    ip         VARCHAR(50) comment '아이피',
    ins_dt timestamp comment '입력일'
);


CREATE TRIGGER after_board_insert
AFTER INSERT ON board
FOR EACH ROW
BEGIN

INSERT INTO board_log
SET
actionType = 'INSERT',
boardIdx = NEW.boardIdx,
id = NEW.id,
title = NEW.title,
content = NEW.content,
hitCnt = NEW.hitCnt,
ip = NEW.ip,
ins_dt = sysdate()
;
END;


CREATE TRIGGER after_board_update
AFTER UPDATE ON board
FOR EACH ROW
BEGIN

INSERT INTO board_log
SET
actionType = 'UPDATE',
boardIdx = NEW.boardIdx,
id = NEW.id,
title = NEW.title,
content = NEW.content,
hitCnt = NEW.hitCnt,
ip = NEW.ip,
ins_dt = sysdate()
;
END  ;


CREATE TRIGGER after_board_delete
AFTER DELETE ON board
FOR EACH ROW
BEGIN

INSERT INTO board_log
SET
actionType = 'DELETE',
boardIdx = OLD.boardIdx,
id = OLD.id,
title = OLD.title,
content = OLD.content,
hitCnt = OLD.hitCnt,
ip = OLD.ip,
ins_dt = sysdate()
;
END ;
