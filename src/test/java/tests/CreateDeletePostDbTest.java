package tests;

import helpers.Posts;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import models.ResponseErrorModel;
import models.ResponsePostsModel;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import queries.Sql;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic(value = "Задача D2. Подготовка тестовых данных в БД")
@Feature(value = "Метод posts")
public class CreateDeletePostDbTest extends BaseTest {
    int postId;
    Posts posts;

    @Story(value = "Создание и удаление записей с БД")
    @Severity(CRITICAL)
    @Test(description = "Создание и удаление поста в БД")
    public void createPosTest() {
        posts = dbSteps.createPost(Sql.insertPost());
        postId = posts.getId();
        posts = dbSteps.getPost(Sql.selectPostById(postId));
        SoftAssert dbInsert = new SoftAssert();
        dbInsert.assertEquals(posts.getStatus(), conf.getTestConfigPosts().getStatusCreate());
        dbInsert.assertEquals(posts.getTitle(), conf.getTestConfigPosts().getTitleCreate());
        dbInsert.assertEquals(posts.getContent(), conf.getTestConfigPosts().getContentCreate());
        dbInsert.assertEquals(posts.getExcerpt(), conf.getTestConfigPosts().getExcerptCreate());
        dbInsert.assertAll();
        ResponsePostsModel response = apiSteps.getPost(getRequestSpecification(), postId);
        SoftAssert apiGet = new SoftAssert();
        apiGet.assertEquals(response.getStatus(), conf.getTestConfigPosts().getStatusCreate());
        apiGet.assertEquals(response.getTitle().getRendered(), conf.getTestConfigPosts().getTitleCreate());
        apiGet.assertEquals(response.getContent().getRendered(), conf.getTestConfigPosts().getContentCreate());
        apiGet.assertEquals(response.getExcerpt().getRendered(), conf.getTestConfigPosts().getExcerptCreate());
        apiGet.assertAll();
        dbSteps.delete(Sql.deletePostById(postId));
        posts = dbSteps.getPost(Sql.selectPostById(postId));
        SoftAssert dbDelete = new SoftAssert();
        dbDelete.assertNull(posts.getStatus());
        dbDelete.assertNull(posts.getTitle());
        dbDelete.assertNull(posts.getContent());
        dbDelete.assertNull(posts.getExcerpt());
        dbDelete.assertAll();
        ResponseErrorModel responseError = apiSteps.getPostError(getRequestSpecification(), postId);
        SoftAssert apiError = new SoftAssert();
        apiError.assertEquals(responseError.getCode(), conf.getTestConfigErrors().getPostsErrorCode());
        apiError.assertEquals(responseError.getMessage(), conf.getTestConfigErrors().getPostsErrorMessage());
        apiError.assertAll();
    }
}
