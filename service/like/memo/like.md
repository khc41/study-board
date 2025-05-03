## 좋아요 수 설계
### 문제 상황
대규모 트래픽 상황 데이터에선 count 쿼리의 성능 이슈가 있다.<br>
`-> 실시간으로 전체 개수를 조회하는 대신 좋아요 수를 미리 갱신해둔다.`

### 데이터 특성 분석
- 쓰기 트래픽 낮음
- 데이터 일관성 중요

`-> 데이터 베이스의 트랜잭션 활용한 일관성 유지가 가능하다.`

### 제약사항 분석
좋아요 수를 게시물 테이블에 넣으면 다음과 같은 문제가 발생한다.
- Record Lock 충돌: 서로 다른 유저 요청이 하나의 row에 락을 걸어 병목 발생
- 분산 트랜잭션: 좋아요와 게시글이 서로 다른 DB에 잇을 경우 트랜잭션 보장이 어려움

### 해결방안
- 좋아요 수를 별도 테이블로 분리
- 좋아요 테이블과 같은 DB 샤드에 위치시켜 분산 트랜잭션 문제 방지

### 동시성 이슈
- 비관적 락: record lock 사용
- 낙관적 락: version 컬럼 사용
- 비동기 순차 처리: 오히려 더 큰 비용이 듦 (좋아요 쓰기 트래픽이 많지 않다고 가정)
> 비관적 락으로 해결 가능하지만, 학습 목적 상 비관적 락, 낙관적 락 모두 사용해볼 예정
> 낙관적 락은 테스트 결과 사용 불가 (충돌로 인해 데이터 보장 X)

```text
pessimistic-lock-1 start
lockType = pessimistic-lock-1, time = 1936ms
pessimistic-lock-1 end
count = 3001

pessimistic-lock-2 start
lockType = pessimistic-lock-2, time = 2604ms
pessimistic-lock-2 end
count = 3001

optimistic-lock start
lockType = optimistic-lock, time = 2816ms
optimistic-lock end
count = 335
```

### 참고
- 댓글 수, 게시글 수 등 다른 카운팅에도 동일 방식 적용 가능
  - 페이징 성능 개선 목적의 count 쿼리 대체로도 활용 가능