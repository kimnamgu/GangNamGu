ALTER TABLE file_house ADD COLUMN contentIdx varchar(32) NOT NULL;

select * from file_house
delete from file_house



--파일 테이블 작업
drop table file_recommend;
drop table file_house;
drop table file_house_log;

SHOW TRIGGERS;

DROP TRIGGERS after_file_house_insert;
DROP TRIGGERS after_file_house_delete;
DROP TRIGGERS after_file_house_update;

create table file_house (
    fileIdx int not null auto_increment  comment '파일IDX',
    fileSeq int not null comment '파일순번',
    fileName varchar(100) not null comment '파일명',
	fileExtension varchar(10) not null comment '파일확장자명',
    fileRealName varchar(512) not null comment '파일원본명',
    fileSize int comment '파일크기',
    categoryCode varchar(20) comment '게시판카테고리',
    boardIdx int comment '게시글IDX',
    id varchar(45) NOT NULL  comment '사용자 ID',
    folderPath varchar(200) comment '폴더경로',    
    del_yn varchar(1) DEFAULT 'Y' comment '삭제YN',
	open_yn varchar(1) DEFAULT 'Y' comment '공개YN',
	down_yn varchar(1) DEFAULT 'Y'comment '다운YN',
    fileDescription varchar(300) comment '파일설명',
    ins_dt timestamp comment '입력일',
    upt_dt timestamp comment '수정일',
    PRIMARY KEY(fileIdx)
);


--파일 로그 테이블 생성
create table file_house_log (
	actionType varchar(10) comment '입력상태',
    fileIdx int comment '파일IDX',
    fileSeq int comment '파일순번',
    fileName varchar(100) comment '파일명',
	fileExtension varchar(10) comment '파일확장자명',
    fileRealName varchar(512) comment '파일원본명',
    fileSize int comment '파일크기',
    categoryCode varchar(20) comment '게시판카테고리명',
    boardIdx int comment '게시글IDX',
    id varchar(45) comment '사용자 ID',
    folderPath varchar(200) comment '폴더경로',  
    del_yn varchar(1) DEFAULT 'Y' comment '삭제YN',
	open_yn varchar(1) DEFAULT 'Y' comment '공개YN',
	down_yn varchar(1) DEFAULT 'Y'comment '다운YN',
    fileDescription varchar(300) comment '파일설명',
    ins_dt timestamp comment '입력일'
);


CREATE TRIGGER after_file_house_insert
AFTER INSERT ON file_house
FOR EACH ROW
BEGIN

INSERT INTO file_house_log
SET
actionType = 'INSERT',
fileIdx = NEW.fileIdx ,
fileSeq = NEW.fileSeq ,
fileName = NEW.fileName ,
fileExtension = NEW.fileExtension ,
fileRealName = NEW.fileRealName ,
fileSize = NEW.fileSize ,
categoryCode = NEW.categoryCode ,
boardIdx = NEW.boardIdx ,
id = NEW.id ,
folderPath = NEW.folderPath ,
del_yn = NEW.del_yn,
open_yn = NEW.open_yn,
down_yn = NEW.down_yn,
fileDescription = NEW.fileDescription,
ins_dt = sysdate()
;
END;


CREATE TRIGGER after_file_house_update
AFTER UPDATE ON file_house
FOR EACH ROW
BEGIN

INSERT INTO file_house_log
SET
actionType = 'UPDATE',
fileIdx = NEW.fileIdx ,
fileSeq = NEW.fileSeq ,
fileName = NEW.fileName ,
fileExtension = NEW.fileExtension ,
fileRealName = NEW.fileRealName ,
fileSize = NEW.fileSize ,
categoryCode = NEW.categoryCode ,
boardIdx = NEW.boardIdx ,
id = NEW.id ,
folderPath = NEW.folderPath ,
del_yn = NEW.del_yn,
open_yn = NEW.open_yn,
down_yn = NEW.down_yn,
fileDescription = NEW.fileDescription,
ins_dt = sysdate()
;
END  ;


CREATE TRIGGER after_file_house_delete
AFTER DELETE ON file_house
FOR EACH ROW
BEGIN

INSERT INTO file_house_log
SET
actionType = 'DELETE',
fileIdx = OLD.fileIdx ,
fileSeq = OLD.fileSeq ,
fileName = OLD.fileName ,
fileExtension = OLD.fileExtension ,
fileRealName = OLD.fileRealName ,
fileSize = OLD.fileSize ,
categoryCode = OLD.categoryCode ,
boardIdx = OLD.boardIdx ,
id = OLD.id ,
folderPath = OLD.folderPath ,
del_yn = OLD.del_yn,
open_yn = OLD.open_yn,
down_yn = OLD.down_yn,
fileDescription = OLD.fileDescription,
ins_dt = sysdate()
;
END ;
