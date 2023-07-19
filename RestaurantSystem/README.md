# 식당 예약 시스템


## API 명세서
<br>  
<br>

## Member

    1. 회원가입
    - api :  POST "/auth/signup"
    - request : 
        1. name
        2. password
        3. phone
        4. roles = 
        (MANAGER, CUSTOMER 1개 이상 권한 소유 가능)
    - 정책 :
        1. 중복 아이디 불가


    2. 로그인
    - api : POST "/auth/signin"
    - request :
        1. name
        2. password
    - 정책 : 
        1. 비밀번호 일치
        2. 회원가입  유/무
<br>

## Store
    1. 식당 등록
    - api : POST "/store/registration"
    - auth : MANAGER
    - request
        1. userName
        2. address
        3. storeName
        4. description
    - 정책 :
        1. 점장 회원가입
        2. 식당 중복등록 불가
    
    2. 모든 식당 조회
    - api : GET "/store"

    3. 식당 세부사항 조회(식당 상세설명)
    - api : GET "/store/detail"
    - request : storeId

    4. 식당명 검색 자동완성
    - api : GET "/store/autocomplete"
    - request : KeyWord

    5. 식당 정보 수정
    - api : PUT "/store/adjustment"
    - auth : MANAGER
    - reuqest : 
        1. storeName
        2. address
        3. description
        4. storeCode
    - 정책 : 
        1. 식당 등록 완료 후 가능

 
    6. 식당 삭제
    - api : DELETE "/store/{code}"
    - auth : MANAGER
    - request : storeId
    - 정책 : 
        1. 식당 등록 완료 후 가능
<br>

## Reservation
    1. 예약 등록
    - api : POST "/reservation/"
    - auth : CUSTOMER
    - request : 
        1. userName
        2. phone
        3. storeName
        4. storeCode
        5. Date(LocalDateTime - ISO.DATE_TIME)
    - 정책 : 
        1. 회원가입 필수
        2. 등록된 매장 필수
        3. 중복 시간 대 예약 불가

    2. 고객 예약 조회
    - api : GET "/reservation/customer"
    - auth : CUSTOMER
    - request : 
        1. phone 
    - Page : 
        1. 한 페이지 당 20개

    3. 점장 예약 조회
    - api : GET "/reservation/manager"
    - auth : MANAGER
    - request : 
        1. storeCode
    - Page 형식
        1. 한 페이지 당 20개

    4. 예약 상세 조회
    - api : GET "/reservation/detail"
    - request : 
        1. reservationId

    5. 예약 내역 수정
    - api : PUT "/reservation/adjustment"
    - auth : "CUSTOMER"
    - requset : 
        1. reservationId
        2. date (LocalDateTime - ISO.DATE_TIME)

    6. 예약 내역 삭제
    - api : DELETE "/reservation/"
    - auth : CUSTOMER
    - request :
        1. reservationId
        2. confirm(boolean)

    7. 예약 요청 승인/거절
    - api : POST "/reservation/confirm"
    - auth : MANAGER
    - request :
        1. reservationId
        2. confirm

    8. 예약 체크
    - api : Post "/reservation/check"
    - auth : CUSTOMER
    - request :
        1. storeCode
        2. phone
    - 정책 :
        1. 예약 시간 10분 전에만 예약 접수가능
        2. 예약 시간 30분 전에는
        예약 접수 불가능
