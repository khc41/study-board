package study.board.view.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import study.board.common.event.EventType;
import study.board.common.event.payload.ArticleViewedEventPayload;
import study.board.common.outboxmessagerelay.OutboxEventPublisher;
import study.board.view.entity.ArticleViewCount;
import study.board.view.repository.ArticleViewCountBackUpRepository;

@Component
@RequiredArgsConstructor
public class ArticleViewCountBackUpProcessor {
    private final OutboxEventPublisher outboxEventPublisher;
    private final ArticleViewCountBackUpRepository articleViewCountBackUpRepository;

    @Transactional
    public void backUp(Long articleId, Long viewCount) {
        int result = articleViewCountBackUpRepository.updateViewCount(articleId, viewCount);
        if (result == 0) {
            articleViewCountBackUpRepository.findById(articleId)
                    .ifPresentOrElse(ignored -> {
                            },
                            () -> articleViewCountBackUpRepository.save(ArticleViewCount.init(articleId, viewCount))
                    );
        }

        outboxEventPublisher.publish(
                EventType.ARTICLE_VIEWED,
                ArticleViewedEventPayload.builder()
                        .articleId(articleId)
                        .articleViewCount(viewCount)
                        .build(),
                articleId
        );
    }
}
