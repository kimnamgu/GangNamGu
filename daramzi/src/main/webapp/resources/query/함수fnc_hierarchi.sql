DROP FUNCTION IF EXISTS fnc_hierarchi;
 
 
CREATE FUNCTION  fnc_hierarchi() RETURNS INT
 
NOT DETERMINISTIC
 
READS SQL DATA
 
BEGIN
 
    DECLARE v_replyIdx INT;
    DECLARE v_upperReplyIdx INT;    
    DECLARE CONTINUE HANDLER FOR NOT FOUND SET @replyIdx = NULL;
 
    SET v_upperReplyIdx = @replyIdx;
    SET v_replyIdx = -1;
 
    IF @replyIdx IS NULL THEN
        RETURN NULL;
    END IF;
 
    LOOP
    
    SELECT MIN(replyIdx)
      INTO @replyIdx 
      FROM reply
     WHERE upperReplyIdx = v_upperReplyIdx
       AND replyIdx > v_replyIdx;
 
    IF (@replyIdx IS NOT NULL) OR (v_upperReplyIdx = @start_with) THEN
       SET @level = @level + 1;
    RETURN @replyIdx;
    END IF;
    
    SET @level := @level - 1;
 
    SELECT replyIdx, upperReplyIdx
      INTO v_replyIdx , v_upperReplyIdx 
        FROM reply
       WHERE replyIdx = v_upperReplyIdx;
   
    END LOOP;
 
END
 