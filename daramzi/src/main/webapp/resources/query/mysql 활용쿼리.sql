-- mysql 활용 쿼리 모음 --

-- 트리거 생성시
SET Global log_bin_trust_function_creators='ON';

-- 테이블 alter
ALTER TABLE file_house CHANGE `categoryCode` `categoryCode` varchar(20) comment '게시판카테고리';
ALTER TABLE file_house_log CHANGE `categoryCode` `categoryCode` varchar(20) comment '게시판카테고리';

--테이블 생성 구문 보기
show create table tbl_board;

-- 테이블 컬럼 확인
SHOW FULL COLUMNS FROM file_house; 


* 컬럼 정보 조회
SELECT
    table_name,
    column_name,
    column_comment,
    ordinal_position
FROM
    information_schema.columns
WHERE
    table_schema = 'daramzi_db'
    AND table_name = 'reply'
    order by ordinal_position
    ;
    
    
SELECT
*
FROM
    information_schema.columns
WHERE
    table_schema = 'daramzi_db'
    AND table_name = 'tbl_board'
    order by ordinal_position
    ;    
