package study.board.common.event;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import study.board.common.event.payload.*;

@Slf4j
@Getter
@RequiredArgsConstructor
public enum EventType {
    ARTICLE_CREATED(ArticleCreatedEventPayload.class, Topic.STUDY_BOARD_ARTICLE),
    ARTICLE_UPDATED(ArticleUpdatedEventPayload.class, Topic.STUDY_BOARD_ARTICLE),
    ARTICLE_DELETED(ArticleDeletedEventPayload.class, Topic.STUDY_BOARD_ARTICLE),
    COMMENT_CREATED(CommentCreatedEventPayload.class, Topic.STUDY_BOARD_COMMENT),
    COMMENT_DELETED(CommentDeletedEventPayload.class, Topic.STUDY_BOARD_COMMENT),
    ARTICLE_LIKED(ArticleLikedEventPayload.class, Topic.STUDY_BOARD_LIKE),
    ARTICLE_UNLIKED(ArticleUnlikedEventPayload.class, Topic.STUDY_BOARD_LIKE),
    ARTICLE_VIEWED(ArticleViewedEventPayload.class, Topic.STUDY_BOARD_VIEW);

    private final Class<? extends  EventPayload> payloadClass;
    private final String topic;

    public static EventType from(String type){
        try{
            return valueOf(type);
        } catch (Exception e){
            log.error("[EventType.from] type={}", type, e);
            return null;
        }
    }

    public static class Topic{
        public static final String STUDY_BOARD_ARTICLE = "study-board-article";
        public static final String STUDY_BOARD_COMMENT = "study-board-comment";
        public static final String STUDY_BOARD_LIKE = "study-board-like";
        public static final String STUDY_BOARD_VIEW = "study-board-view";
    }
}
