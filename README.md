# 🗃️프롬ProM
## - 프로젝트 관리 협업 툴 플랫폼 개발 프로젝트
### 📍목차
[1. 프로젝트 개요](#프로젝트-개요)

[2. 기획 내용](#기획-내용)

[3. 화면 구성](#화면-구성)

[4. 역할 및 담당 업무](#역할-및-담당-업무)

[5. 자체 총평](#자체-총평-및-소감)

---

## 프로젝트 개요
### 💡주제 선정 배경
팀 프로젝트를 진행하면서 팀원들 간의 유기적인 커뮤니케이션의 필요성을 느꼈고,

프로젝트의 전체적인 진행 상황을 모든 팀원이 시각적으로 확인할 수 있는 협업 툴을 적극 활용했습니다.

개발 프로젝트 기획 당시 많이 활용하는 팀 프로젝트를 관리하는 플랫폼을 구현하고자 하였습니다.

### 🌐배포 서버
http://13.124.132.111:3000/

### 🗓️개발 수행 절차
![프로젝트 수행 절차](https://github.com/ddoonsim/ProM_project_FE/assets/143992194/c2a6c153-279b-4e7c-862f-699606326bcc)

### 🛠️Skills
![그림1](https://github.com/ddoonsim/ProM_project_FE/assets/143992194/3915e612-d4cc-49ec-86b3-3423de39cbbc)
---

## 기획 내용
<details>
  <summary>공통</summary>
  <ul>
    <li>파일 업로드 : 파일 업로드는 공통기능을 이용하고, gid와 location 속성으로 파일의 업로드 위치를 구분</li>
    <li>모달창 ➡️ react-modal, ModalBox 컴포넌트를 import하는 것으로 모달창을 손쉽게 띄울 수 있게 구현</li>
  </ul>
</details>
<details>
  <summary> 로그인 및 회원가입</summary>
  <ul>
    <li>스프링 시큐리티를 이용한 로그인 및 회원가입</li>
    <li>회원가입 시 이메일 인증 : JWT 토큰 방식의 로그인 기능을 채택함으로써 세션을 사용할 수 없어 인증번호를 일시적으로 저장하기 위해 redis 방식의 메모리 기반 데이터 저장 방식을 채택</li>
    <li>JWT 토큰 방식을 이용한 로그인 및 로그인 유지</li>
    <li>인증 : DB에 등록된 아이디와 패스워드를 입력값과 비교하여 일치 여부를 확인하고, 로그인에 성공하면 서버는 사용자에게 토큰을 전달</li>
    <li>인가 : 토큰 유무를 통해 사용자가 리소스에 접근할 권리가 있는지 확인하고 허가</li>
    <li>BCryptPasswordEncoder를 이용한 비밀번호 암호화</li>
    <li>비밀번호 찾기 ➡️ 비밀번호 초기화 및 임시 발급 비밀번호 이메일 전송</li>
  </ul>
</details>
<details>
  <summary>프로젝트</summary>
  <ul>
    <li>프로젝트 생성 ➡️ 생성자 : 팀원 초대 가능, 모든 팀원이 참여하는 단체 채팅방 생성</li>
    <li>회원과 프로젝트는 다대다 관계 매핑</li>
    <li>새 프로젝트를 생성하면 프로젝트의 참여자에 생성자 본인이 기본으로 추가되게 구현하기 위해 SpringSecurity로 로그인 정보를 불러와 추가</li>
    <li>프로젝트 하위에 업무, 업무 하위에 To Do 구조</li>
    <li>입력한 이메일로 초대장의 링크를 전송 ➡️ 링크를 클릭하면 로그인이 되어 있는지 확인 후, 로그인을 먼저 유도하여 로그인 후 초대장 페이지로 이동</li>
    <li>공지사항 등록 가능</li>
    <li>프로젝트 진행 상황에 따른 칸반보드 구현</li>
  </ul>
</details>
<details>
  <summary>채팅</summary>
  <ul>
    <li>프로젝트를 생성하면 프로젝트 별 팀원이 모두 참여하는 단체 채팅방 개설</li>
    <li>WebSocket으로 구현</li>
    <li>afterConnectionEstablished() 메서드로 세션 생성</li>
    <li>handleTextMessage() 메서드로 메시지 작성 처리</li>
    <li>afterConnectionClosed() 메서드로 세션 제거</li>
  </ul>
</details>
<details>
  <summary>공지사항</summary>
  <ul>
    <li>업무와 같은 엔터티를 사용하되, bType을 BOARD로 설정하고, isNotice 값을 true로 설정하여 업무와 구분</li>
    <li>상세한 내용을 입력하기 위한 툴로 CKEditor를 사용, 이미지와 파일 업로드가 가능</li>
    <li>생성 시의 컴포넌트와 구성을 동일하게 하고 저장되어 있는 데이터를 조회하여 useEffect 훅을 이용하여 폼에 값이 출력되어 있게 처리</li>
  </ul>
</details>
<details>
  <summary>업무 추가 및 조회</summary>
  <ul>
    <li>Subject와 Project는 다대일 관계</li>
    <li>업무 제목, 담당자, 진행 상태, 시작일, 종료일, 하위업무 양식을 등록. 등록된 게시글이면 동일한 양식에 출력하여 조회</li>
    <li>담당자를 정하는 select는 react-select 사용</li>
    <li>시작일 종료일을 정하는 달력은 react-calendar 사용</li>
  </ul>
</details>
<details>
  <summary>대시보드</summary>
  <ul>
    <li>참여 중인 프로젝트 목록</li>
    <li>react-big-calendar 라이브러리 추가</li>
    <li>Calendar의 events 배열 객체에 로그인한 사용자가 담당하고 있는 업무를 배열로 추가</li>
    <li>각 배열에는 start(시작일), end(종료일), title(업무제목) 속성에 업무 엔터티의 sdate(시작일), edate(종료일), tname(업무제목)을 대입</li>
    <li>색상을 다르게 하기 위해 업무 진행상태(status)를 events 배열 객체의 속성값으로 추가함</li>
  </ul>
</details>
<details>
  <summary>ERD 구조</summary>
  
  ![ERD](https://github.com/ddoonsim/ProM_project_FE/assets/143992194/dd8ead99-f97e-4219-856c-c7bde2d1d306)
</details>

## 화면 구성
<details>
  <summary>자세히 보기</summary>
  
  --- 
  
  |로그인 전 메인 홈|회원가입|
  |:-:|:-:|
  |![main](https://github.com/ddoonsim/ProM_project_BE/assets/143992194/8a0ee810-6016-4388-b2b6-76e1fa208333)|![회원가입](https://github.com/ddoonsim/ProM_project_BE/assets/143992194/85a932ef-d504-44e4-a167-b11534218777)|

  |로그인|아이디찾기 & 비밀번호 찾기|
  |:-:|:-:|
  |![로그인](https://github.com/ddoonsim/ProM_project_BE/assets/143992194/bf4680a7-4662-4bf3-a43f-72cbea512230)|![아이디_비밀번호_찾기](https://github.com/ddoonsim/ProM_project_BE/assets/143992194/d131fe25-32bb-4809-b443-d1359716276d)|

  |회원정보 수정|대시보드|
  |:-:|:-:|
  |![회원정보수정](https://github.com/ddoonsim/ProM_project_FE/assets/143992194/36b4319e-d629-46a4-b880-9c4e37cb8f84)|![대시보드](https://github.com/ddoonsim/ProM_project_FE/assets/143992194/25182eb3-0bb4-4780-af63-bc73d59ddd3a)|

  |프로젝트 생성|프로젝트 페이지|
  |:-:|:-:|
  |![새프로젝트생성](https://github.com/ddoonsim/ProM_project_BE/assets/143992194/5f273433-6f04-447f-a9e6-77fe9935ee19)|![프로젝트페이지](https://github.com/ddoonsim/ProM_project_BE/assets/143992194/ebece9e0-c397-46a7-bdbd-784dd8ca89f4)|

  |초대 이메일 보내기|초대 링크 클릭|
  |:-:|:-:|
  |![팀원초대하기](https://github.com/ddoonsim/ProM_project_BE/assets/143992194/db9b4626-2167-45d3-a798-5d29913f172c)|![초대장](https://github.com/ddoonsim/ProM_project_BE/assets/143992194/e669e002-82cd-4bc8-9708-f4d60e733ad3)|

  |단체 채팅창|공지사항 등록|
  |:-:|:-:|
  |![채팅창](https://github.com/ddoonsim/ProM_project_BE/assets/143992194/ca68995c-54d6-4943-b486-b43d825b86cd)|![공지사항등록](https://github.com/ddoonsim/ProM_project_BE/assets/143992194/1e19a648-8abd-4bf4-b0de-854a2f21e83b)|

  |업무 및 할 일 추가|업무 칸반보드|
  |:-:|:-:|
  |![addTask](https://github.com/ddoonsim/ProM_project_FE/assets/143992194/4c539df2-fc29-409a-a236-f0ac2dba8609)|![KanbanBoard](https://github.com/ddoonsim/ProM_project_FE/assets/143992194/56bea95e-1378-4c4a-a8b9-930348cf7cd3)|
</details>

## 역할 및 담당 업무
<details>
  <summary>🐧 이수정</summary>
  <ul>
    <li>회원가입 시 이메일 인증코드 발송 및 인증 기능 구현</li>
    <li>프로젝트 생성 및 CRUD 작업</li>
    <li>모달창 구현</li>
    <li>팀원 초대(이메일) 기능 구현</li>
    <li>프로젝트와 대시보드 등 전체 UI 구성</li>
    <li>공지사항 CRUD 작업</li>
    <li>대시보드의 업무 캘린더</li>
  </ul>
</details>
<details>
  <summary>🐦 이지오</summary>
  <ul>
    <li>채팅 기능 전체 구현</li>
    <li>업무 및 할일 CRUD 작업</li>
  </ul>
</details>
<details>
  <summary>🦜 신재민</summary>
  <ul>
    <li>회원가입 및 인증, 인가 구현</li>
    <li>아이디 찾기 & 비밀번호 찾기 기능</li>
    <li>할일 추가 페이지 구현</li>
  </ul>
</details>
<details>
  <summary>🦉 이준범</summary>
  <ul>
    <li>회원가입 시 프로필이미지 추가 기능 구현</li>
    <li>회원정보 수정 기능 구현</li>
    <li>대시보드의 날씨 위젯 추가</li>
  </ul>
</details>

## 자체 총평 및 소감
(추후 추가)
