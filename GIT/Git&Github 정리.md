1. Git : 분산버전관리 시스템
	- 레포지토리(저장소) 단위로 관리
	- 사용자 로컬 PC <-> 로컬 레포지토리(git) <-> 원격 서버 레포지토리(github)
	- git init : git 로컬 저장소 생성. 현재 작업중인 디렉토리를 git 레포지토리로 지정. ls -al을 통해 .git을 확인 가능
	- 사용자 로컬 PC : Working Directory / Staging Area / Local Repository의 세 단계로 구성
	- Working Directory -> (git add) Staging Area : git add 명령어를 통해 디렉토리의 변경사항을 Staging Area로 이동
	- Staging Area -> (git commit) Local Repository : git commit 명령어를 통해 변경사항을 Local Repository로 이동
2. Git branch : 코드의 흐름을 분산/가지치기 하는 기능
	- git branch <branch_name> : 브랜치 생성
	- git checkout <branch_name> : 현재 브랜치에서 해당 브랜치로 전환
	- git merge <branch_name> : 현재 작업중인 브랜치를 다른 브랜치로 병합. 
	- git branch -d <branch_name> : 해당 브랜치 삭제
3. Github
	- Code 탭
		- branch : 각 브랜치의 로그/흐름 확인
		- tag : 소프트웨어의 릴리즈(버전)에 대해 작성 가능
	- Issue 탭 : pull request, 버그 등을 제보
	- Pull Request 탭 : 브랜치 병합 시 사용
		- compare -> main 으로 병합을 의미
	- Actions 탭 : 자동 CI/CD 환경 구축을 위한 워크 플로우 제공
	- git clone <github Repository 주소> : github 원격 저장소의 코드를 나의 working Directory로 가져옴