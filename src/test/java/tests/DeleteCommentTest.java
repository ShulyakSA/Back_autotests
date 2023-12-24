package tests;

import helpers.Comments;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import models.ResponseCommentsModel;
import models.ResponsePostsModel;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import queries.Sql;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic(value = "Задача D1. Тестирование API: работа с БД")
@Feature(value = "Метод comments")
public class DeleteCommentTest extends BaseTest {
    private int postId;
    private int commentId;

    @BeforeTest
    public void precondition() {
        ResponsePostsModel postResponse = apiSteps.createPost(getRequestSpecification());
        postId = postResponse.getId();
        apiSteps.updatePost(getRequestSpecification(), postId);
        ResponseCommentsModel commentResponse = apiSteps.createComment(getRequestSpecification(), postId);
        commentId = commentResponse.getId();
    }

    @Story(value = "Удаление комментария с проверкой в БД")
    @Severity(CRITICAL)
    @Test(description = "Удаление комментария, с проверкой тэгов 'status', 'author_name', 'content'")
    public void createCommentTest() {
        ResponseCommentsModel response = apiSteps.deleteComment(getRequestSpecification(), commentId);
        Assert.assertEquals(response.getStatus(), conf.getTestConfigComments().getStatusDelete());
        Comments comment = dbSteps.getComment(Sql.selectCommentById(commentId));
        Assert.assertEquals(comment.getCommentApproved(), conf.getTestConfigComments().getStatusDelete());
    }
}
