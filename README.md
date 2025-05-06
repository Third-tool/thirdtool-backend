# thirdtool-backend
깃 관련 간단한 가이드 

우리는 각 기능 단위(Feature)로 브랜치를 생성하고, 작업 후 `main` 브랜치에 Pull Request(PR) 방식으로 병합합니다.  
아래는 개발 중 따라야 할 Git 규칙입니다.

---

## 1. 브랜치 전략

- `main`: 배포용 브랜치 (절대 직접 푸시 금지)
- `feature/기능이름`: 기능 단위 작업 브랜치  
  예: `feature/login`, `feature/user-profile`, `feature/#12-refactor-tag`

> ✅ 하나의 기능이 끝나면 해당 브랜치는 병합 후 삭제

---

## 2. 브랜치 생성 및 이동

```bash
git checkout -b feature/기능이름
```

예:
```bash
git checkout -b feature/login-api
```

---

## 3. 커밋 메시지 규칙

형식:
```
[feature-기능이름] 작업 내용
```

예:
- `[feature-login] 로그인 API 구현`
- `[feature-tag] 해시태그 서비스 로직 분리`

---

## 4. 작업 저장 및 푸시

```bash
git add .
git commit -m "[feature-기능이름] 작업 내용"
git push origin feature/기능이름
```

---

## 5. Pull Request (PR)

1. GitHub에서 `main ← feature/기능이름` 기준 PR 생성
2. 제목: `[feature-기능이름] 작업 내용`
3. 리뷰어에게 코드 리뷰 요청- 반드시 리뷰가 확인이 되었을 때만 main 브랜치에 남길 것 
4. 승인되면 `main` 브랜치에 병합

---

## 6. 충돌 해결

```bash
git pull origin main --rebase
# 충돌 파일 수정
git add .
git rebase --continue
git push -f origin feature/기능이름
```

---

## 7. 기타 규칙

- 공통 설정 파일(`QuerydslConfig`, `SecurityConfig`)은 `global/config` 폴더에 위치
- 모든 기능은 자신의 feature 브랜치에서 구현 후 병합
- 테스트 및 설정 리팩토링은 가능하면 독립된 브랜치로 작업

---

