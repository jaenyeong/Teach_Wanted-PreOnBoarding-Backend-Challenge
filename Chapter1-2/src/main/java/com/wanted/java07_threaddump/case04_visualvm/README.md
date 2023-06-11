# visualVM으로 스레드덤프 분석

## visualVM 설치

### 패키지 매니저로 설치
* `brew install visualVM` 명령으로 설치

### VisualVM 다운로드 설치
* VisualVM을 [사이트](https://visualvm.github.io/)에서 다운로드해서 설치

## IDEA에 VisualVM 플러그인 추가 (필수 아님)
[사이트 참조](https://plugins.jetbrains.com/plugin/7115-visualvm-launcher/)

* IDEA에 `VisualVM Launcher` 플러그인으로 연동하여 IDEA에서 사용할 수 있음
* 아래 설명이 이해되지 않는다면 사이트 참조 

### `VisualVM Launcher` Plugin 설치
* IDEA Settings (⌘ + ,) > Plugins에서 `VisualVM Launcher` 검색 후 설치

### 설치한 VisualVM의 경로를 IDEA에 설정에 추가
* 설치 경로를 확인 (OS 또는 설치 방법에 따라 경로와 경로 확인 방법 등이 다르니 주의!)
  * 여기서는 `brew info visualVM` 명령으로 설치된 경로 확인
    >>>> /opt/homebrew/Caskroom/visualvm/2.1.6/
* IDEA 설정 [ IDEA Settings (⌘ + ,) > Other Settings > VisualVM Launcher > VisualVM executable ] 안에 VisualVM 경로 추가
  * 여기서는 `/opt/homebrew/Caskroom/visualvm/2.1.6/` 안에 `VisualVM.app`를 추가
    * 다시 확인 시 `/Applications/VisualVM.app/Contents/MacOS/visualvm` 경로로 설정되어 있는 것을 확인

## VisualVM 실행
* 프로세스를 실행 후에 VisualVM에서 프로세스에 접속
  * 플러그인을 설치/설정한 경우 IDEA에서 VisualVM으로 실행
