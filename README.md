# 실습을 위한 개발 환경 세팅
* https://github.com/slipp/web-application-server 프로젝트를 자신의 계정으로 Fork한다. Github 우측 상단의 Fork 버튼을 클릭하면 자신의 계정으로 Fork된다.
* Fork한 프로젝트를 eclipse 또는 터미널에서 clone 한다.
* Fork한 프로젝트를 eclipse로 import한 후에 Maven 빌드 도구를 활용해 eclipse 프로젝트로 변환한다.(mvn eclipse:clean eclipse:eclipse)
* 빌드가 성공하면 반드시 refresh(fn + f5)를 실행해야 한다.

# 웹 서버 시작 및 테스트
* webserver.WebServer 는 사용자의 요청을 받아 RequestHandler에 작업을 위임하는 클래스이다.
* 사용자 요청에 대한 모든 처리는 RequestHandler 클래스의 run() 메서드가 담당한다.
* WebServer를 실행한 후 브라우저에서 http://localhost:8080으로 접속해 "Hello World" 메시지가 출력되는지 확인한다.

# 각 요구사항별 학습 내용 정리
* 구현 단계에서는 각 요구사항을 구현하는데 집중한다. 
* 구현을 완료한 후 구현 과정에서 새롭게 알게된 내용, 궁금한 내용을 기록한다.
* 각 요구사항을 구현하는 것이 중요한 것이 아니라 구현 과정을 통해 학습한 내용을 인식하는 것이 배움에 중요하다. 

### 요구사항 1 - http://localhost:8080/index.html로 접속시 응답
* Closeable: try 와 같은 구문의 바디 부분이 실행된 후 자동으로 닫히길 원할 때 사용
* BufferedReader 클래스를 이용해 HTTP Header 한 줄씩 읽기
* 헤더의 마지막은 while(!line.equals(""))으로 확인하기
* line == null return 필요
* 요청 라인을 우선적으로 읽어서 요청 URL 판단하기
* Files.readAllBytes()를 이용해 Bytes 배열로 파일 읽어들이기
* HTTP 요청(응답) 구조: 요청(응답) 라인, 요청(응답) 헤더, 공백 라인, 요청 본문(body)
* 서버는 웹 페이지를 구성하는 모든 자원(HTML, CSS, 자바스크립트, 이미지 등)을 한 번에 응답으로 보내지 않는다.
* 브라우저는 HTML 을 분석해 추가 자원이 필요한 경우 서버에 다시 해당 자원을 요청하게 된다.

### 요구사항 2 - get 방식으로 회원가입
* Arrays 클래스: 배열 조작 기능(복사, 정렬, 검색 등)
* stream(arr): 배열 혹은 컬렉션 인스턴스를 함수형으로 처리할 수 있음
* 생성하기, 가공하기, 결과 만들기의 과정으로 나누어 볼 수 있음
* filter(): 스트림 내 요소를 평가해 걸러냄
* map(): 스트림 내 요소를 하나씩 특정 값으로 변환해줌
* collect(): 필터, 매핑된 요소들을 새로운 컬렉션에 수집해서 반환해줌
* Collectors.toMap(): Map 형태의 컬렉션 만들기

### 요구사항 3 - post 방식으로 회원가입
* QueryString 대신 Content-Length 에 본문의 길이를 명시함
* Request Body 가 전달되는 시점 생각하여 Body read 하기
* form 태그가 지원하는 method 는 GET, POST 뿐

### 요구사항 4 - redirect 방식으로 이동
* Response Header 의 "Location" field 에 redirect url 명시
* 클라이언트는 Location 을 보고 서버에 redirect url 재요청

### 요구사항 5 - 로그인 (cookie)
* 서버가 클라이언트에 전송하여 클라이언트가 저장하고 있음
* Response Header 의 "Set-Cookie" field 에 <cookie-name>=<cookie-value> 형태로 명시
* 클라이언트는 응답 헤더에 Set-Cookie 가 존재할 경우 값을 읽어 서버에 다시 전송한다.

### 요구사항 6 - 사용자 목록 출력
* Boolean.parseBoolean(String)
* StringBuilder 사용 
* String 은 불변이기 때문에 직접적인 연산(+)시 객체 생성, 삭제가 내부적으로 수행되고
* 그에 따라 성능 이슈(메모리,속도)가 생김 따라서 Builder 로 그 문제를 해결함

### 요구사항 7 - stylesheet 적용
* 요청 헤더의 Accept 필드 확인 후 그에 맞춰 응답 헤더의 Content-Type 필드 명시
* 

### heroku 서버에 배포 후
* 