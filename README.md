## :seedling:Goal: "스프링 부트로 로그인 기능이 없는 나만의 항해 블로그 백엔드 서버 만들기"

[완성] (http://54.180.108.65/api/post/)

### :blossom:유스케이스 그리기
![usecase](https://user-images.githubusercontent.com/110078157/185021967-5ebad089-36d9-454a-b6ef-a84df21e5d38.png)


### :blossom:API 설계하기

|                       | Method |       URL      |                                            Request                                           |                                                                                                                                                                                                                                        Response                                                                                                                                                                                                                                        |
|-----------------------|:------:|:--------------:|:--------------------------------------------------------------------------------------------:|:--------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| 전체 게시글 목록 조회 | GET    | /api/post      | -                                                                                            | {         "createdAt": "2022-08-16T15:39:32.825658", "title": "title2",,         "author": "test",},     {         "createdAt": "2022-08-16T15:40:02.76205",        "title": "test",         "content": "test",         "author": "test"   } |
| 게시글 작성           | POST   | /api/post      | {     "title":"test1",     "content":"test1",     "author":"test1",     "password":"test1" } | {     "createdAt": "2022-08-16T15:41:33.9904442",     "modifiedAt": "2022-08-16T15:41:33.9904442",      "title": "test1",     "content": "test1",     "author": "test1",     "password": "test1" }                                                                                                                                                                                                                                                                         |
| 게시글 조회           | GET    | /api/post/{id} | -                                                                                            | { "createdAt": "2022-08-16T15:39:32.825658"    "title": "title2",     "content": "test",     "author": "test",     }                                                                                                                                                                                                                                                                             |
| 게시글 비밀번호 확인  | POST   | /api/post/{id} | { "password" :"password" }                                                                   | true                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   |
| 게시글 수정           | PUT    | /api/post/{id} | {   "title" : "test",   "content" : "test",   "author" : "test",   "password" : "test"  }    | {     "createdAt": "2022-08-16T15:39:32.825658",     "modifiedAt": "2022-08-16T15:43:06.250449",     "id": 2,     "title": "test",     "content": "test",     "author": "test",     "password": "test" }                                                                                                                                                                                                                                                                               |
| 게시글 삭제           | DELETE | /api/post/{id} |                                                                                              |                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        |


### :blossom:AWS 배포
 - [x] RDS연결 => MySQL을 이용하기
 - [x] EC2배포 => Ubuntu EC2 를 구매한 뒤, 8080 포트와 80번 포트를 연결하여 포트 번호 없이도 서비스에 접속 가능하게 하기

### :sunflower: WHY?

**1.수정, 삭제 API의 request를 어떤 방식으로 사용하셨나요? (param, query, body)**

=>body - JSON 방식

**2.어떤 상황에 어떤 방식의 request를 써야하나요?**

=> param :  id와 같이 식별가능한 변수를 활용해야 할 때
   query :  &을 통해 여러 개의 데이터를 넘길수 있을 때 , 조건이 여러개인걸 조회할때, 최저가검색
   body :  XML, JSON, Multi Form 등의 데이터를 담음, 기록된 많은 변수를 사용자가 뽑아 사용할 때
   
**3.RESTful한 API를 설계했나요? 어떤 부분이 그런가요? 어떤 부분이 그렇지 않나요?**

=><RESTful 의 기준>

①Client–server 구조 : 클라이언트와 서버는 서로 독립적

②Stateless(무상태성) : 클라이언트의 모든요청에는 해당요청을 이해할수 있는 모든 정보가 포함되어야 한다.

③Cacheable : 해당 요청이 캐싱이 가능한지 여부를 제공해야한다

④Uniform interface(일관된 인터페이스) : 보현적인 소프트웨어 엔지니어링 원칙을 적용

⑤Layered system(다중 계층) : 다중계층을 가질수 있도록 허용

⑥Code on demand (optional)  : 서버가 클라이언트에서 실행시킬 수 있는 로직을 전송하여 클라이언트의 기능을 확장 시킬 수 있다. 이를 통해 클라이언트가 사전에 구현해야 하는 기능의 수를 줄여 간소화 시킬수 있다

=>1,2,4,5는 잘 한 것 같고 3,6은 몰라서 잘 못했던 것 같습니다.

**4.적절한 관심사 분리를 적용하였나요? (Controller, Repository, Service)**

=>  Controller에서 클라이언트에게 요청을 받으면 Sevice에서 직접 요청을 수행하며 이후 DB에서 정보를 Repository에게 주면, DB가 CRUD작업을 처리한다

**5.작성한 코드에서 빈(Bean)을 모두 찾아보세요!**

=>Controller ,Service, Repository


