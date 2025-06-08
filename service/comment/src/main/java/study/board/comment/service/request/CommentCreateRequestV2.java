package study.board.comment.service.request;

import lombok.Getter;

@Getter
public class CommentCreateRequestV2 {
    //TODO: articleId 검증, 의존성 단방향 신경쓰면서 작업
    private Long articleId;
    private String content;
    private String parentPath;
    private Long writerId;
}
