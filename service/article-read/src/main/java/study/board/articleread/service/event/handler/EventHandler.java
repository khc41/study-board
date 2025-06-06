package study.board.articleread.service.event.handler;

import study.board.common.event.Event;
import study.board.common.event.EventPayload;

public interface EventHandler<T extends EventPayload> {
    void handle(Event<T> event);
    boolean support(Event<T> event);
}
