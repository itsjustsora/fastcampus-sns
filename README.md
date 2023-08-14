### 프로젝트 목표
간단한 sns 서비스를 만든 후, 대규모 트래픽을 고려할 때 어떤 점을 개선시켜야 하는지 배우고 실습한다.

<br>

### 기술 스택
- java 11
- Spring boot 2.6.7
- Spring Data JPA
- Spring Security
- Gradle
- Lombok
- Github
- JUnit5
- ~~Kafka~~
- PostgreSQL
- Redis
- Heroku
- SSE

<br>

### 대규모 트래픽 고려하기

**[기존 Architecture]**

<img width="735" alt="스크린샷 2023-08-12 오후 3 12 13" src="https://github.com/itsjustsora/fastcampus-sns/assets/80027033/d9afdb36-722d-4ccc-ba33-3658431d0d56">

만약 이 SNS 서비스의 이용자들이 늘어나 트래픽이 증가한다고 가정해 볼 때 어떤 부분에서 문제가 발생할까?
- 토큰 인증 시 user를 조회하고, 그 이후 또 user를 조회
- 매 API 요청 시마다 조회하는 user
- Alarm까지 생성해야 응답을 하는 API
- Alarm List API를 요청해야만 갱신되는 알람 목록
- 데이터베이스로 날라가는 Query들은 최적화가 되었을까?
- 즉
    - 코드의 비최적화
    - 수많은 DB I/O
    - 기능 간의 강한 결합성

 
 <br>

**[개선된 Architecture]**

<img width="815" alt="스크린샷 2023-08-12 오후 3 12 58" src="https://github.com/itsjustsora/fastcampus-sns/assets/80027033/3ba1a7b7-9280-4c19-9040-8a313af36052">

1. Redis cache를 활용해 DB 부하를 줄이면서 데이터를 가져오자.
3. SSE를 활용해 페이지를 새로고침하지 않아도 새로운 알람을 확인할 수 있도록 하자.
4. ~~Kafka를 활용해 비동기적으로 데이터를 처리하자.~~
   -> 이전 프로젝트에서 활용했던 간단한 수준의 내용이라 스킵하고 다른 강의에서 더 학습하기로 함.

