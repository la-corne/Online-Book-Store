
DELIMITER $$
    CREATE TRIGGER before_book_insert BEFORE INSERT ON `book`
    FOR EACH ROW BEGIN    
	IF (NEW.publisherid in  ( SELECT id
						FROM publisher
						where publisher.id=new.publisherid)
					) THEN
            insert into book (TITLE,PUBLICATIONYEAR,SELLINGPRICE,CATEGORY,PUBLISHERID,QUANTITY)
            values(new.title,new.publicationyear,new.SELLINGPRICE,new.CATEGORY,new.PUBLISHERID,new.QUANTITY);
      END IF;
    END$$
DELIMITER ;


DELIMITER $$
    CREATE TRIGGER before_book_update BEFORE update ON `book`
    FOR EACH ROW BEGIN
	IF (NEW.quantity <0) THEN
	  signal sqlstate '45000' set message_text='error quantity < 0';
         END IF;
    END$$
DELIMITER ;



