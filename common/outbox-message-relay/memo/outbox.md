## Transactional Messaging
### 문제 상황
- Producer가 Kafka로 메시지를 발행하고 Consumer가 이를 처리하는 과정에서 네트워크는 언제든 순단될 수 있고 각 시스템에서 장애가 발생할 수 있다.
- Kafka에 데이터가 제대로 전달된다면 장애가 발생하더라도 복구가 가능하다.
- 하지만 Producer에서 Kafka로 메시지를 전달하는 과정에서 장애가 전파되면 안되므로 작업이 정상적으로 수행되도 메시지가 제대로 전달되지 못하는 경우가 발생할 수 있다.

### 해결 방안
- Two Phase Commit
  - 별도의 Coordinator를 두고 Producer와 Consumer가 모두 준비 상태가 되면 Commit을 수행하는 방식
  - 모든 참여자를 기다려야 하기 때문에 지연이 길어질 수 있고, 장애가 발생하면 복구 처리가 복잡해질 수 있다. 
- Transactional Outbox
  - 데이터베이스에 Outbox 테이블을 생성하고 서비스 로직 수행과 Outbox 테이블 이벤트 기록을 단일 트랜잭션에서 실행하는 방식
  - 별도의 Message Relay를 두고, Message Relay가 Outbox 테이블을 모니터링하여 이벤트를 Kafka로 발행한다.
- Transaction Log Tailing
  - 데이터베이스의 로그를 읽어 이벤트를 발행하는 방식
  - Outbox Table을 사용하지 않아도 되는 반면, CDC 기술의 추가적인 학습 및 운영 비용이 생긴다.
  - Data Table을 사용하게 되면 메시지 정보가 데이터 베이스 변경 사항에 종속된 구조로 한정되고, 이벤트 발행도 명시적이지 않아 추적하기 힘들다.
  - Outbox Table을 사용하게 되면 메시지 정보가 데이터베이스 변경 사항과 독립적인 구조로 설계할 수 있다.

### 설계
#### Message Relay
- Message Relay에서 Outbox Table을 10초 간격으로 polling하여 미전송 이벤트를 조회한다. 
- 10초간 지연될 수 있으므로, 각 서비스에서 로직 수행 시 Message Relay로 이벤트를 전달한다.
- 하지만, Message Relay로 들어오는 이벤트가 중복될 수 있으므로 Outbox Table에서 조회하는 데이터는 생성된 지 10초 이상의 이벤트들만 가져온다.
- 전송 완료된 이벤트는 추적이나 비즈니스 관점에서 유의미할 수도 있지만, 여기서는 삭제한다.
#### 샤딩된 데이터베이스 Polling
- 미전송 이벤트를 polling할 때 특정 shard key가 없으므로 모든 샤드에서 직접 polling을 수행해야 한다.
- 모든 애플리케이션이 동시에 polling 하게 되면, 이벤트를 중복으로 수행할 수 있고 지연이 발생할 수 있다.
- 이를 해결하기 위해, Message Relay 내에 별도의 Coordinator를 두고, Coordinator가 각 샤드에 polling을 분배한다.
  - Coordinator는 중앙 저장소에 일정 시간 간격으로 ping을 보내고, 중앙 저장소는 실행중인 애플리케이션 목록을 파악해 샤드를 적절히 분배한다.
  - ping을 받은지 일정 시간이 지나면 애플리케이션이 종료되었다고 판단하고 목록에서 제거한다.