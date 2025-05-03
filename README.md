# 스프링부트로 직접 만들면서 배우는 대규모 시스템 설계 - 게시판
대규모 시스템 설계를 배우며 배운점 중 중요한 내용이나 기억해야할 사항들을 정리해 놓은 공간
## 메모 목록
- [인덱스 관련 메모](service/article/memo/index.md)<br>
  → 성능 병목이 발생한 쿼리에 대해 인덱스 추가 및 EXPLAIN 분석 내용
- [무한 depth 댓글 설계](service/comment/memo/comment-infinite-depth.md)<br>
  → Path Enumeration 방식으로 무한 계층 댓글을 효율적으로 구현한 설계
- [좋아요 수 테이블 설계](service/like/memo/like.md)<br>
  → MSA 환경에서 분산 트랜잭션을 피하기 위한 좋아요 수 테이블 분리 및 샤딩 전략