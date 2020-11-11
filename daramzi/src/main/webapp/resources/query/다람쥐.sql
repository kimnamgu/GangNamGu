delete from board;
delete from board_log;
delete from file_house;
delete from file_house_log;

select * from board;
select * from file_house;


select * from file



update file_house set board_idx = '2'


select * from user
delete from user;
delete from user_log;

select * from file_folder
delete from file_folder

select * from file_recommend
delete from file_recommend

SELECT
    (
        SELECT
            COUNT(1)
        FROM
            file_recommend
        WHERE
            id = '1234'
            AND fileidx = '406'
            AND recommendyn = 'Y'
    ) AS getrecommendycnt,
    (
        SELECT
            COUNT(1)
        FROM
            file_recommend
        WHERE
            id = '1234'
            AND fileidx = '406'
            AND recommendyn = 'N'
    ) AS getrecommendycnt