CREATE USER C##BCLASS IDENTIFIED BY BCLASS; // 계정 생성
GRANT CONNECT, RESOURCE TO C##BCLASS; // 권한부여 CONNECT, RESOURCE = 롤(ROLE).

ALTER USER c##bclass QUOTA UNLIMITED ON USERS;
COMMIT;


/*
    <ROLE>
    특정 권한들을 하나의 집합으로 모아놓은 것
*/