package study.board.comment.entity;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class CommentPathTest {
    @Test
    void createChildCommentTest() {
        // 00000 <- 생성
        createChildCommentTest(CommentPath.create(""), null, "00000");

        // 00000
        //      00000 <- 생성
        createChildCommentTest(CommentPath.create("00000"), null, "0000000000");

        // 00000
        // 00001 <- 생성
        createChildCommentTest(CommentPath.create(""), "00000", "00001");

        // 0000z
        //      abcdz
        //           zzzzz
        //                zzzzz
        //      abce0 <- 생성
        createChildCommentTest(CommentPath.create("0000z"), "0000zabcdzzzzzzzzzzz", "0000zabce0");

        // 0000z
        //      abcdz
        //           abcde
        //                zzzzz
        //           abcdf <- 생성
        //      abce0
        //           zzzzz
        //                zzzzz
        //      abce1
        createChildCommentTest(CommentPath.create("0000zabcdz"), "0000zabcdzabcdezzzzz", "0000zabcdzabcdf");
    }

    void createChildCommentTest(CommentPath commentPath, String descendentsTopPath, String expectedChildPath) {
        CommentPath childCommentPath = commentPath.createChildCommentPath(descendentsTopPath);
        assertThat(childCommentPath.getPath()).isEqualTo(expectedChildPath);
    }

    @Test
    void createChildCommentPathIfMaxDepthTest() {
        assertThatThrownBy(() ->
                CommentPath.create("zzzzz".repeat(5)).createChildCommentPath(null)
        ).isInstanceOf(IllegalStateException.class);
    }

    @Test
    void createChildCommentPathIfChunkOverflowTest() {
        // given
        CommentPath commentPath = CommentPath.create("");

        // when, them
        assertThatThrownBy(()-> commentPath.createChildCommentPath("zzzzz00000"))
                .isInstanceOf(IllegalStateException.class);
    }

}