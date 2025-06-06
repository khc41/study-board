package study.board.articleread.api;

import org.junit.jupiter.api.Test;
import org.springframework.web.client.RestClient;
import study.board.articleread.service.response.ArticleReadResponse;

public class ArticleReadApiTest {
    RestClient restClient = RestClient.create("http://localhost:9005");

    @Test
    void readTest(){
        ArticleReadResponse response = restClient.get()
                .uri("/v1/articles/{articleId}", 174842446859825152L)
                .retrieve()
                .body(ArticleReadResponse.class);

        System.out.println("response = " + response);
    }
}
