SELECT
        TO_CHAR(CREATE_DATE, 'YYYY-MM-DD') -- 단일함수
  FROM
        BOARD;
        
        
SELECT 
        LENGTH(BOARD_NO) -- 단일함수
  FROM
        BOARD;
        
-- MIN MAX SUM ACG COUNT (그룹함수)
SELECT
        COUNT(BOARD_NO)
  FROM
        BOARD;
--------------------------------------------------------------------------------
-- INLINE-VIEW // SELECT문을 할때는 순서를 !!
SELECT 
            BOARD_NO,
            BOARD_TITLE,
            BOARD_WRITER,
            COUNT,
            CREATE_DATE,
            ORIGIN_NAME,
            RNUM
            -- (중간서브쿼리)까지 진행한 것을 다시 순서를 
  FROM
            (SELECT 
                    BOARD_NO,
                    BOARD_TITLE,
                  	BOARD_WRITER,
                  	COUNT,
                    CREATE_DATE,
                    ORIGIN_NAME,
                    ROWNUM RNUM 
                    -- 정렬이 끝난상태의 ROWNUM을 조회해와서 RNUM으로 별칭부여.
                    -- (가장안쪽서브쿼리)한 것을 (ROWNUM)RUNUM으로 새로운 ROWNUM 값을 부여하는 것
              FROM
                   	 (SELECT 
                             BOARD_NO,
                             BOARD_TITLE,
                             BOARD_WRITER,
                             COUNT,
                             CREATE_DATE,
                             ORIGIN_NAME
                        FROM
                             BOARD
                       WHERE
                             STATUS = 'Y' -- 삭제되지 않은것만
                        -- AND
                          --   ROWNUM BETWEEN 1 AND 10 -- 행 개수
                                     --오라클에서 정렬된 결과를 받고 싶으면 무조건 ORDER BY절을 사용해야한다.
                       ORDER
                          BY
                             BOARD_NO DESC))
                             --가장 안쪽 서브쿼리 : BOARD테이블에서 삭제되지 않은것만(STATUS=Y) 1번부터 10번 행 선택(ROWNUM)하여 BOARD_NO을 기준으로 내림차순으로 정렬
WHERE
      RNUM BETWEEN 11 AND 16; -- 2번페이지의 행 *ROWNUM : 1부터(오라클의 특징) 그래서 RNUM으로 변경해준거