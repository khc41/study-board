# 스프링부트로 직접 만들면서 배우는 대규모 시스템 설계 - 게시판
대규모 시스템 설계를 배우며 배운점 중 중요한 내용이나 기억해야할 사항들을 정리해 놓은 공간
## 메모 목록
- [인덱스 관련 메모](service/article/memo/index.md)<br>
  → 성능 병목이 발생한 쿼리에 대해 인덱스 추가 및 EXPLAIN 분석 내용
- [무한 depth 댓글 설계](service/comment/memo/comment-infinite-depth.md)<br>
  → Path Enumeration 방식으로 무한 계층 댓글을 효율적으로 구현한 설계
- [좋아요 수 테이블 설계](service/like/memo/like.md)<br>
  → MSA 환경에서 분산 트랜잭션을 피하기 위한 좋아요 수 테이블 분리, 샤딩 전략 및 동시성 이슈 고려
- [Kafka Cluster 개념 정리](service/hot-article/memo/kafka.md)<br>
  → Kafka의 pub/sub 구조, partition 분산 처리, 복제 설정 및 Consumer Group에 따른 offset 처리 방식 등 대규모 시스템에서 Kafka를 안정적으로 운영하기 위한 핵심 개념 정리
- [인기글 설계](service/hot-article/memo/hot-article.md)
  → Kafka 기반 실시간 이벤트 스트림 + Redis Sorted Set을 활용한 고속 인기글 선정 시스템 설계 (대규모 트래픽 대응 최적화)