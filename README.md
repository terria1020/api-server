# api-server

명지대학교 2023 캡스톤디자인 ‘SOPAR’ 프로젝트의 Spring Boot RESTful API Server

### 프로젝트 소개

본 프로젝트에서는 사용되지 않는 민간 주차장을 활성화해 주차난을 해결하는 안드로이드 애플리케이션을 제작하는 것을 목표로 합니다. 주차 공급자가 사용하지 않는 민간 주차장을 공급하고 주차 수요자가 그 민간 주차장을 대여하는 것을 빠르게 연결해주는 기능을 주로 하는 공유주차장 애플리케이션입니다.

### 프로젝트 목표

본 프로젝트는 사용되고 있지 않은 민간 주차장의 활성화를 통해 주차난 해소와 불법주차 감소, 주차난과 불법주차로 인한 부가적인 문제 해결을 목표로 하고 있습니다.

### 프로젝트 일정

캡스톤디자인 일정 중 개발 가능한 기간: 2023.04.03 ~ 2023.06.02

### 프로젝트 아키텍처 · 구조도

<img width="740" alt="스크린샷 2023-06-02 오후 12 17 47" src="https://github.com/terria1020/api-server/assets/38485612/5d8eceae-0343-4964-8b01-3d21c5429f95">
<img width="736" alt="스크린샷 2023-06-02 오후 12 17 54" src="https://github.com/terria1020/api-server/assets/38485612/875636b7-7d41-4be5-b3fb-8e475d874d80">
<img width="738" alt="스크린샷 2023-06-02 오후 12 18 02" src="https://github.com/terria1020/api-server/assets/38485612/e4a12a16-1a61-4ab5-95e8-dd6921aa7a90">
<img width="685" alt="스크린샷 2023-06-02 오후 12 19 41" src="https://github.com/terria1020/api-server/assets/38485612/7f524945-d6f0-4d01-9ce0-d31f5e1b46b6">


### 기술 스택

---

### Programming Language · Framework

객체 지향 언어인 java와 Spring Boot 프레임워크를 사용한 RESTful API 웹 서버 개발

### Infra

GoormIDE Container 사용한 우분투 리눅스 서버 사용

Shell Script, cron 서비스 활용

MySQL 8.0.2 Database 사용

Github Actions + SSH + Shell Script 활용한 CD

### Distributed Version Control

git/github 사용

### 설계 과정

---

### 1차 설계

chatGPT 질문, 답변을 사용한 설계

개발 플랫폼, 언어 선정

infrastructure 세팅

### 2차 설계

chatGPT 기반 설계를 다듬고 클라이언트가 필요한 API End Point 설계

Spring Boot 기본 프로젝트 세팅

### 본 개발

---

엔드포인트/세부 기능 별 개발 우선순위 부여

개발 범위를 좁히고, 이슈 생성 후 이슈 별 개발

Git-Flow 전략을 사용한 머지 정책

Pull Request를 사용한 코드 리뷰, 피드백 및 머지

상세 설계 이후의 개발 내용 정리는 다음을 참조: 

[SOPAR_최종결과보고서_백엔드파트.pdf](https://s3-us-west-2.amazonaws.com/secure.notion-static.com/94e868c9-570d-4aef-b3f6-180202e51796/SOPAR_%E1%84%8E%E1%85%AC%E1%84%8C%E1%85%A9%E1%86%BC%E1%84%80%E1%85%A7%E1%86%AF%E1%84%80%E1%85%AA%E1%84%87%E1%85%A9%E1%84%80%E1%85%A9%E1%84%89%E1%85%A5_%E1%84%87%E1%85%A2%E1%86%A8%E1%84%8B%E1%85%A6%E1%86%AB%E1%84%83%E1%85%B3%E1%84%91%E1%85%A1%E1%84%90%E1%85%B3.pdf)

### 개인별 프로젝트 개발 내용

---

### 설계 파트

1. 기초설계/상세설계
개발언어/플랫폼 선정, 백엔드 파트 개발에 필요한 설계를 chatGPT 질문 응답 과정을 통하여 설계하게 되었습니다. chatGPT 질문 응답을 통해 설계 한 서비스/데이터베이스 스키마를 기초 설계로 삼아 상세 설계시까지 일부 데이터베이스의 변경을 제외하고 그대로 설계하였습니다. 상세설계 이후 데이터베이스 설계는 개발과 동시에 설계가 이루어졌습니다.
2. 인프라 및 CI/CD 환경 구축 설계
goormIDE 인프라는 지원비를 사용한 student plan으로 사용할 수 있는 항상 켜두기 서버를 사용하여 MySQL 설치 및 환경변수 설정, java11 설치, 폴더 구조 설계 등을 맡았고 지속적 배포 구축을 위해 인프라가 구축된 상태로 Github Actions를 사용한 ssh 접속 명령어, 쉘스크립트 등을 설계하고 만들게 되었습니다.

### 개발 파트

1. Database:
기초 설계 및 상세 설계에서 MySQL 서버에 실제로 스키마를 올리는 과정, 개발 과정에서 설계 된 내용을 sql로 옮겨 서버에 재 반영시키는 업무를 수행하였습니다.
2. Spring Boot:
RESTful API 서버를 구축하기 위해 클라이언트의 카카오 SDK 연동 후 처리, 이미지 업로드 서비스, 유저 포인트 추가, 회원탈퇴 후처리, 3회에 거친 주차장 데이터베이스 및 요청/응답 데이터 개선 QnA 서비스 개선, 공지사항 & FaQ API 개선, 예약 API 개선,History 재설계 및 개선, 버그 패치, 코드 리팩토링 등의 임무를 수행하였습니다.
OpenAPI 3.0 스펙에 준수하는 API 명세 작성, 전역적인 예외 처리, 예외 트래킹 외부 서비스 ‘sentry’ 연동의 작업을 수행하였습니다.
3. 인프라:
cron 스케줄링을 통한 MySQL Hot Backup을 위한 쉘 스크립트 작성, PR을 통한 코드 병합 시 자동화된 배포를 위한 수동 환경변수 설정 등을 개발 시 맡았습니다.

### 문서화/발표 파트

회의록 작성, 각 보고서 백엔드 파트 작성을 맡았습니다.

### 회고

---

본 프로젝트 개발을 통해 크게 3가지 정도를 느낄 수 있었습니다.

1. 데이터베이스는 가능한 처음에 최대한 변경 없는 설계를 할 것
상세 설계 시 까지만 해도 외래 키를 사용하여 의도적인 Relational을 표현하였습니다.
그러나 상세 설계 시점 이후에 chatGPT를 이용한 데이터베이스 스키마 설계에서 미흡한 점을 많이 파악하게 되어 개발과 동시에 설계가 이루어 지게 되었는데, 이 때 정말 많이 데이터베이스 스키마 를 변경했습니다. 데이터베이스 설계 전문가가 따로 존재하지 않는 팀 프로젝트 수준에서는 데이터 베이스를 설계하고 구축하는 임무가 모두 학생에게 다 부여되어 있기 때문에, 데이터베이스의 잦은 변경에 있어서 무결성 등에 걸리는 불편함 없이 개발하기 위해 외래 키를 제외하고 개발하기로 하 였고, 이 점은 결과적으로 많은 데이터베이스 스키마 변경에 있어서 그나마 자유롭게 변경 가능하게 한 판단이었다고 생각합니다. RDBMS를 설계하는 데 있어 관계라는 목적 자체가 사라졌을 때 생각 해야 하는 단점은 당연히 존재할 것이기 때문에, 외래 키 없는 데이터베이스 설계는 장점만 있는 것 이 아니라는 것을 생각해봐야 할 것 같고, RDBMS를 사용하는 데 있어 관계라는 목적을 두고 사용 할 것이라면, 외래 키를 설계할 생각이라면 처음부터 데이터베이스를 설계할 때 정말 잘 해야 한다 고 생각하게 되었습니다.
2. 프론트엔드 개발자와의 더욱 빠르고 많은 커뮤니케이션을 할 것
개발 팀장으로 상세 설계 이후의 개발 시작 시점에서 프론트와 백엔드 개발 관리에 있어서 프론트 개발에 참여하지 않고 비교적 초반에 관여를 많이 하지 않기로 했습니다. 팀원 간 불화는 없었지만 개발 후반부에 프론트엔드 개발자와의 커뮤니케이션에서 API 요청 스펙이 2번 정도 바뀌면서 곤란 한 상황이 발생 해 개발에 차질을 빚게 되었던 적이 있습니다. 커뮤니케이션을 더 빨리 해서 API 스펙을 미리 결정해 두었더라면 좀 더 빠른 백엔드 개발을 이루지 않았을까 싶은 생각이 있습니다.
3. 테스트 코드를 작성할 것
사실 유닛 테스트 기반으로 코드를 작성하고 싶었습니다. 하지만 개발하는 데 생각보다 많은 시간을 할애하여 유닛 테스트 코드를 작성해 볼 수가 없었습니다. 테스트 주도 개발(TDD)나 유닛 테스트 기반 개발은 자신이 개발한 코드 또는 서비스를 보증하는 일종의 보증서 역할을 한다고 이해하고 있는데, 본격적으로 큰 개발 프로젝트 환경에서 테스트 코드를 작성하고자 하는 목적을 이루지 못해 아쉽다고 생각합니다. 유닛 테스트 코드를 많이 작성했다면 개발 영역에 있어서 이슈 별 개발 완료 된 서비스를 테스트 코드기반으로 테스트하여 PR 시 서비스를 보증할 수 있었을 것이라고 생각합 니다.
