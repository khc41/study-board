package study.board.articleread.cache;

import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

class OptimizedCacheTTLTest {

    @Test
    void ofTest() {
        // given
        long ttlSeconds = 10;

        // when
        OptimizedCacheTTL optimizedCacheTTL = OptimizedCacheTTL.of(ttlSeconds);

        // then
        assertThat(optimizedCacheTTL.getLogicalTTL().getSeconds()).isEqualTo(ttlSeconds);
        assertThat(optimizedCacheTTL.getPhysicalTTL()).isEqualTo(
                Duration.ofSeconds(ttlSeconds).plusSeconds(OptimizedCacheTTL.PHYSICAL_TTL_DELAY_SECONDS)
        );
    }

}