drop table ANIMAL

select * from ANIMAL

CREATE TABLE `ANIMAL` (
 
`id` int  NOT NULL AUTO_INCREMENT,
 
`p_id` int  default '0',
 
`nm` varchar(50),
 
primary key(`id`)
 
) 



insert into ANIMAL(p_id, nm) values ( 0, 'µ¿¹°');
 
insert into ANIMAL(p_id, nm) values ( 1, '¸»');
 
insert into ANIMAL(p_id, nm) values ( 1, '´ß');
 
insert into ANIMAL(p_id, nm) values ( 2, '¾ó·è¸»');
 
insert into ANIMAL(p_id, nm) values ( 2, 'Á¶¶û¸»');
 
insert into ANIMAL(p_id, nm) values ( 3, 'Èò´ß');
 
insert into ANIMAL(p_id, nm) values ( 3, '°ËÀº´ß');
 
insert into ANIMAL(p_id, nm) values ( 5, '¸Á¾ÆÁö');
 
insert into ANIMAL(p_id, nm) values ( 6, 'Èòº´¾Æ¸®');
 
insert into ANIMAL(p_id, nm) values ( 7, '°ËÀºº´¾Æ¸®');
 
insert into ANIMAL(p_id, nm) values ( 9, 'Èò´Þ°¿');
 
insert into ANIMAL(p_id, nm) values ( 10, '°ËÀº´Þ°¿');



DROP FUNCTION IF EXISTS fnc_hierarchi_test;

 
CREATE FUNCTION  fnc_hierarchi_test() RETURNS INT
 
NOT DETERMINISTIC
 
READS SQL DATA
 
BEGIN
 
    DECLARE v_id INT;
    DECLARE v_parent INT;    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET @id = NULL;
 
    SET v_parent = @id;
    SET v_id = -1;
 
    IF @id IS NULL THEN
        RETURN NULL;
    END IF;
 
    LOOP
    
    SELECT MIN(id)
      INTO @id 
      FROM ANIMAL
     WHERE p_id = v_parent
       AND id > v_id;
 
    IF (@id IS NOT NULL) OR (v_parent = @start_with) THEN
       SET @level = @level + 1;
    RETURN @id;
    END IF;
    
    SET @level := @level - 1;
 
    SELECT id, p_id
      INTO v_id , v_parent 
        FROM ANIMAL
       WHERE id = v_parent;
   
    END LOOP;
 
END




SELECT CASE WHEN LEVEL-1 > 0 then CONCAT(CONCAT(REPEAT('    ', level  - 1),'¦±'), ani.nm)
                 ELSE ani.nm
           END AS nm
     , ani.id
     , ani.p_id
     , fnc.level
  FROM
     (SELECT fnc_hierarchi_test() AS id, @level AS level
        FROM (SELECT @start_with:=0, @id:=@start_with, @level:=0) vars
          JOIN ANIMAL
         WHERE @id IS NOT NULL) fnc
  JOIN ANIMAL ani ON fnc.id = ani.id
  
  
  
  SELECT fnc_hierarchi_test() AS id, @level AS level
        FROM (SELECT @start_with:=0, @id:=@start_with, @level:=0) vars
          JOIN ANIMAL
         WHERE @id IS NOT NULL

 



