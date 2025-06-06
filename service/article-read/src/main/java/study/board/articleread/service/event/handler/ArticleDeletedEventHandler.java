package study.board.articleread.service.event.handler;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import study.board.articleread.repository.ArticleQueryModelRepository;
import study.board.common.event.Event;
import study.board.common.event.EventType;
import study.board.common.event.payload.ArticleDeletedEventPayload;

@Component
@RequiredArgsConstructor
public class ArticleDeletedEventHandler implements EventHandler<ArticleDeletedEventPayload> {
    private final ArticleQueryModelRepository articleQueryModelRepository;

    @Override
    public void handle(Event<ArticleDeletedEventPayload> event) {
        ArticleDeletedEventPayload payload = event.getPayload();
        articleQueryModelRepository.delete(payload.getArticleId());
    }

    @Override
    public boolean support(Event<ArticleDeletedEventPayload> event) {
        return EventType.ARTICLE_DELETED == event.getType();
    }
}
