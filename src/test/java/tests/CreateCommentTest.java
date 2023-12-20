package tests;

import helpers.Comments;
import io.qameta.allure.*;
import models.ResponseCommentsModel;
import models.ResponsePostsModel;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import queries.Sql;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic(value = "Задача D1. Тестирование API: работа с БД")
@Feature(value = "Метод comments")
public class CreateCommentTest extends BaseTest {
    private int postId;

    @BeforeTest
    public void precondition() {
        ResponsePostsModel response = apiSteps.createPost(requestSpecification);
        postId = response.getId();
        apiSteps.updatePost(requestSpecification, postId);
    }

    @Story(value = "Добавление комментария с проверкой в БД")
    @Severity(CRITICAL)
    @Test(description = "Добавление комментария, с проверкой тэгов 'status', 'author_name', 'content'")
    public void createCommentTest() {
        ResponseCommentsModel response = apiSteps.createComment(requestSpecification, postId);
        int commentId = response.getId();
        SoftAssert softAssertApi = new SoftAssert();
        softAssertApi.assertEquals(response.getAuthorName(), conf.getTestConfigComments().getAuthorNameCreate());
        softAssertApi.assertEquals(response.getContent().getRaw(), conf.getTestConfigComments().getContentCreate());
        softAssertApi.assertEquals(response.getStatus(), conf.getTestConfigComments().getStatusCreate());
        softAssertApi.assertAll();
        Comments comment = dbSteps.getComment(Sql.selectCommentById(commentId));
        SoftAssert softAssertDb = new SoftAssert();
        softAssertDb.assertEquals(comment.getCommentAuthor(), conf.getTestConfigComments().getAuthorNameCreate());
        softAssertDb.assertEquals(comment.getCommentContent(), conf.getTestConfigComments().getContentCreate());
        softAssertDb.assertEquals(comment.getCommentApproved(), conf.getTestConfigComments().getApprovedCreate());
        softAssertDb.assertAll();
    }
}
