# jconsole로 스레드덤프 분석
jconsole은 jmx 사양을 준수하는 모니터링 도구

## 앱 실행 후 PID 확인
* `Teach_Wanted-PreOnBoarding-Challenge/Chapter1-2/src/main/java/com/wanted/java07_threaddump` 경로에서 `java Example07.java` 명령 실행
* `jps` 명령으로 PID 확인 (위 명령으로 실행 시 `Main` 프로세스로 표시됨)

## jconsole 명령 설명
`jconsole` 명령으로 바로 실행 가능 (`jconsole [-interval=n] [-notile] [-plugin path] [-version] [connection ... ] [-Jinput_arguments]`)

* `jconsole <pid>` : 해당 프로세스 실행
* `jconsole <pid> -interval` : 업데이트 간격을 n초로 설정 (default 4 sec)
* `jconsole <pid> -notile` : 둘 이상의 커넥션에 대해 윈도우를 바둑판식으로 배열하지 않음
* `jconsole <pid> -pluginpath` : 플러그인 조회 경로 지정
* `jconsole <pid> -version` : 프로그램 버전 출력
* `jconsole <pid> connection=<pid | host:port | jmxURL>` : 접속 정보 입력
* `jconsole <pid> -Jinput_arguments` : jconsole 명령이 실행중인 VM에 인자 전달
* `jconsole <pid> -help` : 명령에 관련된 도움말 출력
