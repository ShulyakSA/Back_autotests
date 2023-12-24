package tests;

import helpers.Comments;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import models.ResponseCommentsModel;
import models.ResponsePostsModel;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import queries.Sql;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic(value = "Задача D1. Тестирование API: работа с БД")
@Feature(value = "Метод comments")
public class UpdateCommentTest extends BaseTest {
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

    @Story(value = "Обновление комментария с проверкой в БД")
    @Severity(CRITICAL)
    @Test(description = "Обновление комментария, с проверкой тэгов 'status', 'author_name', 'content'")
    public void updateCommentTest() {
        ResponseCommentsModel response = apiSteps.updateComment(getRequestSpecification(), commentId);
        SoftAssert softAssertApi = new SoftAssert();
        softAssertApi.assertEquals(response.getAuthorName(), conf.getTestConfigComments().getAuthorNameUpdate());
        softAssertApi.assertEquals(response.getContent().getRaw(), conf.getTestConfigComments().getContentUpdate());
        softAssertApi.assertEquals(response.getStatus(), conf.getTestConfigComments().getStatusUpdate());
        softAssertApi.assertAll();
        Comments comment = dbSteps.getComment(Sql.selectCommentById(commentId));
        SoftAssert softAssertDb = new SoftAssert();
        softAssertDb.assertEquals(comment.getCommentAuthor(), conf.getTestConfigComments().getAuthorNameUpdate());
        softAssertDb.assertEquals(comment.getCommentContent(), conf.getTestConfigComments().getContentUpdate());
        softAssertDb.assertEquals(comment.getCommentApproved(), conf.getTestConfigComments().getStatusUpdate());
        softAssertDb.assertAll();
    }
}
