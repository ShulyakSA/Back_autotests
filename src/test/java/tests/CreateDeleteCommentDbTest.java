package tests;

import helpers.Comments;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import models.ResponseCommentsModel;
import models.ResponseErrorModel;
import models.ResponsePostsModel;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import queries.Sql;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic(value = "Задача D2. Подготовка тестовых данных в БД")
@Feature(value = "Метод comments")
public class CreateDeleteCommentDbTest extends BaseTest {
    private int postId;
    private int commentId;
    Comments comments;

    @BeforeTest
    public void precondition() {
        ResponsePostsModel response = apiSteps.createPost(getRequestSpecification());
        postId = response.getId();
        apiSteps.updatePost(getRequestSpecification(), postId);
    }

    @Story(value = "Создание и удаление записей с БД")
    @Severity(CRITICAL)
    @Test(description = "Создание и удаление коммента в БД")
    public void createPosTest() {
        comments = dbSteps.createComment(Sql.insertComment(), postId);
        commentId = comments.getCommentId();
        comments = dbSteps.getComment(Sql.selectCommentById(commentId));
        SoftAssert dbInsert = new SoftAssert();
        dbInsert.assertEquals(comments.getCommentPostId(), postId);
        dbInsert.assertEquals(comments.getCommentAuthor(), conf.getTestConfigComments().getAuthorNameCreate());
        dbInsert.assertEquals(comments.getCommentContent(), conf.getTestConfigComments().getContentCreate());
        dbInsert.assertEquals(comments.getCommentApproved(), conf.getTestConfigComments().getApprovedCreate());
        dbInsert.assertAll();
        ResponseCommentsModel response = apiSteps.getComment(getRequestSpecification(), commentId);
        SoftAssert apiGet = new SoftAssert();
        apiGet.assertEquals(response.getPost(), postId);
        apiGet.assertEquals(response.getAuthorName(), conf.getTestConfigComments().getAuthorNameCreate());
        apiGet.assertEquals(response.getContent().getRendered(), conf.getTestConfigComments().getContentRendered());
        apiGet.assertEquals(response.getStatus(), conf.getTestConfigComments().getStatusCreate());
        apiGet.assertAll();
        dbSteps.delete(Sql.deleteCommentById(commentId));
        comments = dbSteps.getComment(Sql.selectCommentById(commentId));
        SoftAssert dbDelete = new SoftAssert();
        dbDelete.assertNull(comments.getCommentApproved());
        dbDelete.assertNull(comments.getCommentAuthor());
        dbDelete.assertNull(comments.getCommentContent());
        dbDelete.assertAll();
        ResponseErrorModel responseError = apiSteps.getCommentError(getRequestSpecification(), commentId);
        SoftAssert apiError = new SoftAssert();
        apiError.assertEquals(responseError.getCode(), conf.getTestConfigErrors().getCommentsErrorCode());
        apiError.assertEquals(responseError.getMessage(), conf.getTestConfigErrors().getCommentsErrorMessage());
        apiError.assertAll();
    }
}
