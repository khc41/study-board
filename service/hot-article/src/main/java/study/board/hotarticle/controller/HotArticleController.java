package study.board.hotarticle.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import study.board.hotarticle.service.HotArticleService;
import study.board.hotarticle.service.response.HotArticleResponse;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class HotArticleController {
    private final HotArticleService hotArticleService;

    @GetMapping("/v1/hot-article/articles/date/{dateStr}")
    public List<HotArticleResponse> readAll(
            @PathVariable("dateStr") String dateStr
    ){
        return hotArticleService.readAll(dateStr);
    }


}
