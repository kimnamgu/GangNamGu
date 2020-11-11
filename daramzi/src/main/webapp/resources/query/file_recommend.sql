CREATE TABLE file_recommend (
	recommendIdx int not null auto_increment  comment '추천IDX',
    fileIdx int comment '파일IDX',
    id varchar(45) NOT NULL  comment '사용자 ID',
    recommendYN varchar(1) comment '추천상태', 
	ins_dt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '가입일',
	upt_dt timestamp ON UPDATE CURRENT_TIMESTAMP  comment '수정일',
	PRIMARY KEY (recommendIdx),
    CONSTRAINT file_recommend_fileIdx_fk FOREIGN KEY (fileIdx) REFERENCES file_house(fileIdx) ON DELETE CASCADE
);

drop table file_recommend


select * from board_recommend

CREATE TABLE board_recommend (
	recommendIdx int not null auto_increment  comment '추천IDX',
    boardIdx int comment '게시글IDX',
    id varchar(45) NOT NULL  comment '사용자 ID',
    recommendYN varchar(1) comment '추천상태', 
	ins_dt timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP comment '가입일',
	upt_dt timestamp ON UPDATE CURRENT_TIMESTAMP  comment '수정일',
	PRIMARY KEY (recommendIdx),
    CONSTRAINT board_recommend_boardIdx_fk FOREIGN KEY (boardIdx) REFERENCES board(boardIdx) ON DELETE CASCADE
);

SELECT
    a.boardidx,
    a.id,
    a.title,
    a.content,
    a.hitcnt,
    a.ip,
    a.ins_dt,
    a.upt_dt,
    (
        SELECT
            COUNT(1)
        FROM
            board_recommend
        WHERE
            boardidx = a.boardidx
            AND recommendyn = 'Y'
    ) AS recommendycnt,
    (
        SELECT
            COUNT(1)
        FROM
            board_recommend
        WHERE
            boardidx = a.boardidx
            AND recommendyn = 'N'
    ) AS recommendncnt,
    (
        SELECT
            recommendyn
        FROM
            board_recommend
        WHERE
            boardidx = a.boardidx
            AND id = 40
    ) AS recommendyn
FROM
    board a
WHERE
    boardidx = 40