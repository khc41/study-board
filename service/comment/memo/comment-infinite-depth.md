## 댓글 무한 depth 설계
### 문제점
기존 방식을 활용하면 무한 depth는 불가능하다. 단순히 depth에 맞춰 컬럼을 생성해 댓글 ID를 관리해줄 수 있지만, 테이블 구조가 비효율적으로 복잡해진다.

### 해결방안
문자열을 순서대로 결합해 Path Enumeration(경로 열거) 방식으로 해결 가능하다.

> XXXXX | XXXXX | XXXXX | ..... <br>
> 1 depth | 2depth | 3depth | 4depth

각 자리수는 0~9 < A~Z < a~z 까지 총 62개의 문자를 사용해 각 경로별로 약 9억개의 자식 댓글을 표현 가능하다.

> 문자열 비교 시 대소문자의 순서를 비교하기 위해 mysql 의 collation은 반드시 `utf8mb4_bin`으로 바꿔줘야 한다.

### 인덱스 참고사항
mysql 인덱스는 양방향 포인터를 활용하기에 정렬한 방향이 반대여도 Backward index scan으로 역순 스캔이 가능하다.
