package study.board.hotarticle.service.eventhandler;

import study.board.common.event.Event;
import study.board.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);

    boolean supports(Event<T> event);

    Long findArticleId(Event<T> event);
}
