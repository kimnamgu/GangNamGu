select * from file_folder
delete from file_folder where folderIdx = 7

update file_folder set folderName = 'imageBoard' where folderIdx = 5

drop table file_folder

--파일 폴더 테이블
create table file_folder(
    folderIdx int not null auto_increment comment '폴더IDX',
    folderSeq int not null comment '폴더순번',
    folderName varchar(100) not null comment '폴더명',
    id varchar(45) NOT NULL  comment '사용자 ID',
    upperFolder varchar(100) comment '상위폴더명',
	folderDepth int comment '폴더깊이',
	del_flag varchar(1) comment '삭제여부',
    ins_dt date comment '입력일',    
    upt_dt date comment '수정일',
    PRIMARY KEY(folderIdx)
);