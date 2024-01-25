<img width="100%" alt="image" src="https://github.com/KOSA200OK/KOSA_KOWORKS_Back/assets/125428810/bc68850c-5318-4e4b-b633-ede14234e4a8">

# 🙌 KOWROKS (200OK)

- <b>**서비스 소개**</b> : 자원 낭비 최소화, 자원 관리 효율화를 위한 자원 관리용 그룹웨어 서비스
- <b>**기획 동기**</b> : 회사에는 공간, 차량, 인적자원 등 다양한 자원이 존재하는데, 이 자원이 효율적으로 운영되지 않는 경우가 많습니다. 회사 운영에서 자원 낭비를 최소화하기 위해 자원을 효율적으로 관리하는 회사자원 관리 시스템을 구축하고자 합니다.
- <b>**차별점**</b> : 기존에 있던 그룹웨어 기능에 더해 자원 관리 기능에 초점을 맞추어 개발하였습니다.
<br>

## 🙋‍♀️ 팀원 소개 및 역할 분담
<div align="left">
   <img src="https://github.com/KOSA200OK/.github/assets/119908089/b6ad3629-f07c-4348-8882-f3da5985a7f9" width="100%"></img>
</div>
<a href="https://github.com/CHANOH5" target="_blank"> 
- 오찬석
</a><br>
<a href="https://github.com/nawonhee" target="_blank"> 
- 나원희
</a><br>
<a href="https://github.com/Logan-CatKeeper" target="_blank"> 
- 서재원
</a><br>
<a href="https://github.com/aeokseung" target="_blank"> 
- 옥승호
</a><br>
<a href="https://github.com/ykchoi7" target="_blank"> 
- 최윤경
</a><br>

## 🗓 프로젝트 기간

<div align="left">
  <img width="80%" alt="image" src="https://github.com/KOSA200OK/KOSA_KOWORKS_Back/assets/125428810/7fbaaedc-4731-4d01-9b21-5044797699d9">
</div>

## 🛠 개발 환경

- IDE : Eclipse, VScode, SqlDeveloper

- DataBase : Oracle Cloud(11g), Redis(7.2.3)

- Front-end : JavaScript, Vue.js(5.0.8), Axios, vue-router

- Back-end : JDK(11.0.19), Spring, Springboot, JPA, Mybatis 

- 협업 도구 : Github, Jira, Notion, draw.io, erdCloud

- 디자인 : Figma

<br>

## 🔗 프로젝트 산출물

### 시스템 구조
<div align="left">
  <img width="90%" alt="image" src="https://github.com/KOSA200OK/KOSA_KOWORKS_Back/assets/125428810/c5c31e1a-8186-47cb-87ce-ab8d39b1452d">
</div>
<hr>

### ERD
<div align="left">
  <img width="90%" alt="image" src="https://github.com/KOSA200OK/KOSA_KOWORKS_Back/assets/125428810/eba16d3a-ceae-455b-a8f7-9974a08a62cd">
</div>
<hr>

### 서비스 흐름도
<div align="left">
  <img width="90%" alt="image" src="https://github.com/KOSA200OK/KOSA_KOWORKS_Back/assets/125428810/83998b22-1a17-4f21-a5c7-14d4b169cef8">
</div>
<hr>
<details>
  <summary>Backend 폴더 구조</summary>
  <div markdown="1">
    
        main
         ┣ java
         ┃ ┗ com
         ┃ ┃ ┗ my
         ┃ ┃ ┃ ┣ address
         ┃ ┃ ┃ ┃ ┣ controller
         ┃ ┃ ┃ ┃ ┣ dto
         ┃ ┃ ┃ ┃ ┗ service
         ┃ ┃ ┃ ┣ advice
         ┃ ┃ ┃ ┃ ┣ MyControllerAdvice.java
         ┃ ┃ ┃ ┃ ┗ ValidTestControllerAdvice.java
         ┃ ┃ ┃ ┣ attendance
         ┃ ┃ ┃ ┃ ┣ control
         ┃ ┃ ┃ ┃ ┣ dao
         ┃ ┃ ┃ ┃ ┣ dto
         ┃ ┃ ┃ ┃ ┣ entity
         ┃ ┃ ┃ ┃ ┗ service
         ┃ ┃ ┃ ┣ car
         ┃ ┃ ┃ ┃ ┣ control
         ┃ ┃ ┃ ┃ ┣ dto
         ┃ ┃ ┃ ┃ ┣ entity
         ┃ ┃ ┃ ┃ ┣ repository
         ┃ ┃ ┃ ┃ ┗ service
         ┃ ┃ ┃ ┣ chat
         ┃ ┃ ┃ ┃ ┣ controller
         ┃ ┃ ┃ ┃ ┣ dto
         ┃ ┃ ┃ ┃ ┣ entity
         ┃ ┃ ┃ ┃ ┣ pubsub
         ┃ ┃ ┃ ┃ ┣ repository
         ┃ ┃ ┃ ┃ ┗ service
         ┃ ┃ ┃ ┣ config
         ┃ ┃ ┃ ┃ ┣ AttendanceConfig.java
         ┃ ┃ ┃ ┃ ┣ MyApplicationContext.java
         ┃ ┃ ┃ ┃ ┣ MyMVCContext.java
         ┃ ┃ ┃ ┃ ┣ RedisConfig.java
         ┃ ┃ ┃ ┃ ┣ SchedulerConfig.java
         ┃ ┃ ┃ ┃ ┗ WebSockConfig.java
         ┃ ┃ ┃ ┣ department
         ┃ ┃ ┃ ┃ ┣ dto
         ┃ ┃ ┃ ┃ ┣ entity
         ┃ ┃ ┃ ┃ ┗ repository
         ┃ ┃ ┃ ┣ exception
         ┃ ┃ ┃ ┃ ┣ AddException.java
         ┃ ┃ ┃ ┃ ┣ DuplicateException.java
         ┃ ┃ ┃ ┃ ┣ FindException.java
         ┃ ┃ ┃ ┃ ┣ ModifyException.java
         ┃ ┃ ┃ ┃ ┣ RemoveException.java
         ┃ ┃ ┃ ┃ ┗ UnavailableException.java
         ┃ ┃ ┃ ┣ login
         ┃ ┃ ┃ ┃ ┣ controller
         ┃ ┃ ┃ ┃ ┣ dto
         ┃ ┃ ┃ ┃ ┗ service
         ┃ ┃ ┃ ┣ meetingroom
         ┃ ┃ ┃ ┃ ┣ control
         ┃ ┃ ┃ ┃ ┣ dto
         ┃ ┃ ┃ ┃ ┣ entity
         ┃ ┃ ┃ ┃ ┣ repository
         ┃ ┃ ┃ ┃ ┗ service
         ┃ ┃ ┃ ┣ member
         ┃ ┃ ┃ ┃ ┣ dto
         ┃ ┃ ┃ ┃ ┣ entity
         ┃ ┃ ┃ ┃ ┗ repository
         ┃ ┃ ┃ ┣ notice
         ┃ ┃ ┃ ┃ ┣ control
         ┃ ┃ ┃ ┃ ┣ dto
         ┃ ┃ ┃ ┃ ┣ entity
         ┃ ┃ ┃ ┃ ┣ repository
         ┃ ┃ ┃ ┃ ┗ service
         ┃ ┃ ┃ ┣ notification
         ┃ ┃ ┃ ┃ ┣ control
         ┃ ┃ ┃ ┃ ┣ dao
         ┃ ┃ ┃ ┃ ┣ dto
         ┃ ┃ ┃ ┃ ┣ entity
         ┃ ┃ ┃ ┃ ┗ service
         ┃ ┃ ┃ ┣ schedule
         ┃ ┃ ┃ ┃ ┣ control
         ┃ ┃ ┃ ┃ ┣ dto
         ┃ ┃ ┃ ┃ ┣ entity
         ┃ ┃ ┃ ┃ ┣ repository
         ┃ ┃ ┃ ┃ ┗ service
         ┃ ┃ ┃ ┣ stuff
         ┃ ┃ ┃ ┃ ┣ control
         ┃ ┃ ┃ ┃ ┣ dto
         ┃ ┃ ┃ ┃ ┣ entity
         ┃ ┃ ┃ ┃ ┣ repository
         ┃ ┃ ┃ ┃ ┣ service
         ┃ ┃ ┃ ┃ ┗ util
         ┃ ┃ ┃ ┗ KosaKoworksBackApplication.java
         ┗ resources
         ┃ ┣ application.properties
         ┃ ┣ db.properties
         ┃ ┣ log4j.xml
         ┃ ┗ log4jdbc.log4j2.properties
        
  </div>
</details>
<details>
  <summary>Frontend 폴더 구조</summary>
  <div markdown="2">

      src
       ┣ components
       ┃ ┣ Footer.vue
       ┃ ┣ Header.vue
       ┃ ┣ Main.vue
       ┃ ┣ PageGroup.vue
       ┃ ┣ Section.vue
       ┃ ┗ Sidebar.vue
       ┣ pages
       ┃ ┣ address
       ┃ ┃ ┗ AddressList.vue
       ┃ ┣ attendance
       ┃ ┃ ┣ AttendanceItem.vue
       ┃ ┃ ┗ AttendanceList.vue
       ┃ ┣ car
       ┃ ┃ ┣ CarAllRentList.vue
       ┃ ┃ ┣ CarAllRentListItem.vue
       ┃ ┃ ┣ CarList.vue
       ┃ ┃ ┣ CarListItem.vue
       ┃ ┃ ┣ CarManage.vue
       ┃ ┃ ┣ CarManageList.vue
       ┃ ┃ ┣ CarManageListItem.vue
       ┃ ┃ ┣ CarMyRentList.vue
       ┃ ┃ ┣ CarMyRentListItem.vue
       ┃ ┃ ┣ CarNoReturnList.vue
       ┃ ┃ ┣ CarNoReturnListItem.vue
       ┃ ┃ ┣ CarRentList.vue
       ┃ ┃ ┣ CarRentListItem.vue
       ┃ ┃ ┣ CarWaitingList.vue
       ┃ ┃ ┣ CarWaitingListItem.vue
       ┃ ┃ ┗ CarsMap.vue
       ┃ ┣ chat
       ┃ ┃ ┣ RoomDetail.vue
       ┃ ┃ ┗ RoomList.vue
       ┃ ┣ contacts
       ┃ ┃ ┗ Contacts.vue
       ┃ ┣ dashboard
       ┃ ┃ ┣ AttendanceInfo.vue
       ┃ ┃ ┣ CarStatus.vue
       ┃ ┃ ┣ Dashboard.vue
       ┃ ┃ ┣ NoticeItemDash.vue
       ┃ ┃ ┣ NotificationItemDash.vue
       ┃ ┃ ┗ StuffStatus.vue
       ┃ ┣ meetingroom
       ┃ ┃ ┣ FullCalendarTest.vue
       ┃ ┃ ┣ MeetingRoomItem.vue
       ┃ ┃ ┣ MeetingRoomList.vue
       ┃ ┃ ┣ MeetingRoomResDelete.vue
       ┃ ┃ ┣ MeetingRoomResInfo.vue
       ┃ ┃ ┣ MeetingRoomResItem.vue
       ┃ ┃ ┣ MeetingRoomResList.vue
       ┃ ┃ ┣ MeetingRoomResModify.vue
       ┃ ┃ ┣ MeetingRoomTimeline.vue
       ┃ ┃ ┗ ReservationModal.vue
       ┃ ┣ notice
       ┃ ┃ ┣ NoticeInfo.vue
       ┃ ┃ ┣ NoticeItem.vue
       ┃ ┃ ┗ NoticeList.vue
       ┃ ┣ notification
       ┃ ┃ ┣ notificationItem.vue
       ┃ ┃ ┗ notify.vue
       ┃ ┣ schedule
       ┃ ┃ ┣ Calendar.vue
       ┃ ┃ ┣ ScheduleInfo.vue
       ┃ ┃ ┣ Test.vue
       ┃ ┃ ┗ TodayTodoItem.vue
       ┃ ┣ stuff
       ┃ ┃ ┣ StuffManage.vue
       ┃ ┃ ┣ StuffManageItem.vue
       ┃ ┃ ┣ StuffManageItemModal.vue
       ┃ ┃ ┣ StuffRequest.vue
       ┃ ┃ ┣ StuffRequestList.vue
       ┃ ┃ ┣ StuffRequestListItem.vue
       ┃ ┃ ┣ StuffRequestListItemModal.vue
       ┃ ┃ ┗ StuffRequestSend.vue
       ┃ ┗ Login.vue
       ┣ router
       ┃ ┗ index.js
       ┣ App.vue
       ┗ main.js
       
  </div>
</details>
<hr>

### 시퀀스 다이어그램
<div align="left">
  <img width="90%" alt="image" src="https://github.com/KOSA200OK/KOSA_KOWORKS_Back/assets/125428810/79ed410a-9755-4f78-9ebf-bd498fec80d2">
</div>
<div align="left">
  <img width="90%" alt="image" src="https://github.com/KOSA200OK/KOSA_KOWORKS_Back/assets/125428810/9f80a87a-0e66-47b9-a645-8a942f5cc95f">
</div>
<div align="left">
  <img width="90%" alt="image" src="https://github.com/KOSA200OK/KOSA_KOWORKS_Back/assets/125428810/f1b03495-4989-4840-a8b9-c501541cf7a9">
</div>
<hr>

### Figma
<div align="left">
  <img width="90%" alt="image" src="https://github.com/KOSA200OK/KOSA_KOWORKS_Back/assets/125428810/39a7f70f-56b5-4c6a-abd6-f81061fa6d9e">
</div>
<br>

## 🔍 주요기능

- 일정 관리

  ㄴ FullCalendar API 이용한 일정 조회/추가/수정/삭제

- 차량 예약/관리

  ㄴ 차량 예약 신청/취소, 신청 내역 조회 <br>
  ㄴ Traccar/카카오맵 API 이용한 실시간 위치 업데이트 및 표시, 신청 승인/반려/반납처리 기능

- 메인 페이지

  ㄴ vue-router를 활용한 SPA 방식의 사이드 메뉴바 구현 <br>
  ㄴ 대시보드 화면 구성 및 각 기능 연결

- 비품 요청/관리

  ㄴ 비품 요청과 승인/반려 기록 추적을 위한 로직 구현 <br>
  ㄴ 요청 테이터 조건에 따른 메서드 호출 로직 구현

- 공지사항

  ㄴ 공지 목록 전체 조회, 상세보기

- 회의실 예약

  ㄴ 예약 등록, 자원 예약 효율화를 위한 중복 예약 방지 로직 구현, 예약 Timeline 프론트 구현 <br>
  ㄴ 30분 전 알림 스케줄러 구현 <br>
  ㄴ 회의 참여자 공유 추가/수정, 예약 취소 기능

- 근태관리

  ㄴ 출근, 퇴근, 서비스 이용자에 따라 설정 가능한 출퇴근 시간 및 지각, 결근 시간 설정 <br>
  ㄴ 근태 현황 조회 및 월별 조회

- 알림

  ㄴ 실시간 알림을 위한 SSE 활용, 최근 알림 조회 <br>
  ㄴ 단체 알림 구현, 회의실 예약 알림, 회의실 참여자에 대한 단체 알림, 차량 예약 알림, 차량 승인 및 반려 알림, 비품 승인 및 반려 알림

- 채팅

  ㄴ Redis 이용한 실시간 채팅 <br>
  ㄴ 인원수에 상관 없이 실시간 채팅 가능, 채팅방 개설 <br>
  ㄴ 채팅방 입장시 이름과 사원번호로 입장 <br>
  ㄴ 채팅 입력 시 본인의 채팅은 화면 오른쪽, 본인을 제외한 사람들의 채팅은 화면 왼쪽에 표시, 채팅 입력 시 입력한 시간 표시

- 로그인
  
  ㄴ 세션 로그인, 로그아웃

- 주소록
  
  ㄴ 주소록 조회, 검색 기능
<br>

## 📖 프로젝트 시작하기

### Installation

### Vue
    npm install @fullcalendar/core 
    npm install @fullcalendar/vue3
    npm install @fullcalendar/daygrid
    npm install @fullcaldendar/interaction
    npm install @fullcalendar/bootstrap5
    npm install --save bootstrap bootstrap-icons
    npm install @fullcalendar/resource-timeline
    
    npm install @vuepic/vue-datepicker


### 채팅
docker
```
$docker run redis
```
vue.js
```
$cd KOSA_KOWORKS_Front
$npm install sockjs-client
$npm install stompjs
```
application.properties
```
$spring.redis.host='본인이 사용할 포트번호'
$spring.redis.port=6379
```
pom.xml
```
$spring-boot-starter-data-redis
$spring-boot-starter-websocket 
```
<br>


## 🏆 팀 프로젝트 대상 수상

<div align="left">
   <img src="https://github.com/KOSA200OK/KOSA200OK/assets/119908089/5ae037a5-aa3d-4fcb-9fdb-57d068d0521a" width="60%"></img>
</div>
