package study.board.comment.api;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.junit.jupiter.api.Test;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.web.client.RestClient;
import study.board.comment.service.response.CommentPageResponse;
import study.board.comment.service.response.CommentResponse;

import java.util.List;

public class CommentApiV2Test {
    RestClient restClient = RestClient.create("http://localhost:9001");

    @Test
    void create() {
        CommentResponse response1 = create(new CommentCreateRequestV2(1L, "my comment1", null, 1L));
        CommentResponse response2 = create(new CommentCreateRequestV2(1L, "my comment2", response1.getPath(), 1L));
        CommentResponse response3 = create(new CommentCreateRequestV2(1L, "my comment3", response2.getPath(), 1L));

        System.out.println("response1.getPath() = " + response1.getPath());
        System.out.println("response1.getCommentId() = " + response1.getCommentId());
        System.out.println("\tresponse2.getPath() = " + response2.getPath());
        System.out.println("\tresponse2.getCommentId() = " + response2.getCommentId());
        System.out.println("\t\tresponse3.getPath() = " + response3.getPath());
        System.out.println("\t\tresponse3.getCommentId() = " + response3.getCommentId());

        /**
         * response1.getPath() = 00002
         * response1.getCommentId() = 175845906343886848
         * 	response2.getPath() = 0000200000
         * 	response2.getCommentId() = 175845906587156480
         * 		response3.getPath() = 000020000000000
         * 		response3.getCommentId() = 175845906633293824
         */
    }

    CommentResponse create(CommentCreateRequestV2 request) {
        return restClient.post()
                .uri("/v2/comments")
                .body(request)
                .retrieve()
                .body(CommentResponse.class);
    }

    @Test
    void read(){
        CommentResponse response = restClient.get()
                .uri("/v2/comments/{commendId}", 175845906343886848L)
                .retrieve()
                .body(CommentResponse.class);
        System.out.println("response = " + response);
    }

    @Test
    void delete(){
        restClient.delete()
                .uri("/v2/comments/{commendId}", 175845906343886848L)
                .retrieve();
    }

    @Test
    void readAll(){
        CommentPageResponse response = restClient.get()
                .uri("v2/comments?articleId=1&pageSize=10&page=50000")
                .retrieve()
                .body(CommentPageResponse.class);

        System.out.println("response.getCommentCount() = " + response.getCommentCount());
        for (CommentResponse comment : response.getComments()) {
            System.out.println("comment.getCommentId() = " + comment.getCommentId());
        }
        /**
         * comment.getCommentId() = 175845353110994944
         * comment.getCommentId() = 175845353584951296
         * comment.getCommentId() = 175845353635282944
         * comment.getCommentId() = 175845552403349504
         * comment.getCommentId() = 175845552596287488
         * comment.getCommentId() = 175845552650813440
         * comment.getCommentId() = 175845906343886848
         * comment.getCommentId() = 175845906587156480
         * comment.getCommentId() = 175845906633293824
         * comment.getCommentId() = 175847627189587973
         */
    }

    @Test
    void readAllInfiniteScroll(){
        List<CommentResponse> response1 = restClient.get()
                .uri("v2/comments/infinite-scroll?articleId=1&pageSize=5")
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("firstPage");
        for (CommentResponse response : response1) {
            System.out.println("response.getCommentId() = " + response.getCommentId());
        }

        String lastPath = response1.getLast().getPath();

        List<CommentResponse> response2 = restClient.get()
                .uri("v2/comments/infinite-scroll?articleId=1&pageSize=5&lastPath=%s".formatted(lastPath))
                .retrieve()
                .body(new ParameterizedTypeReference<List<CommentResponse>>() {
                });

        System.out.println("secondPage");
        for (CommentResponse response : response2) {
            System.out.println("response.getCommentId() = " + response.getCommentId());
        }
    }

    @Test
    void countTest(){
        CommentResponse commentResponse = create(new CommentCreateRequestV2(2L, "my comment1", null, 1L));

        Long count1 = restClient.get()
                .uri("/v2/comments/articles/{articleId}/count", 2L)
                .retrieve()
                .body(Long.class);
        System.out.println("count1 = " + count1);

        restClient.delete()
                .uri("/v2/comments/{commendId}", commentResponse.getCommentId())
                .retrieve();

        Long count2 = restClient.get()
                .uri("/v2/comments/articles/{articleId}/count", 2L)
                .retrieve()
                .body(Long.class);
        System.out.println("count2 = " + count2);
    }

    @Getter
    @AllArgsConstructor
    static class CommentCreateRequestV2 {
        private Long articleId;
        private String content;
        private String parentPath;
        private Long writerId;
    }

}
