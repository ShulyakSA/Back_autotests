package tests;

import helpers.Posts;
import models.ResponsePostsModel;
import queries.Sql;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic(value = "Задача D1. Тестирование API: работа с БД")
@Feature(value = "Метод posts")
public class UpdatePostTest extends BaseTest {
    private int postId;

    @BeforeTest
    public void precondition() {
        ResponsePostsModel response = apiSteps.createPost(getRequestSpecification());
        postId = response.getId();
    }

    @Story(value = "Обновление поста с проверкой в БД")
    @Severity(CRITICAL)
    @Test(description = "Обновление поста, с проверкой тэгов 'status', 'title', 'content', 'excerpt'")
    public void createPosTest() {
        ResponsePostsModel response = apiSteps.updatePost(getRequestSpecification(), postId);
        SoftAssert softAssertApi = new SoftAssert();
        softAssertApi.assertEquals(response.getStatus(), conf.getTestConfigPosts().getStatusUpdate());
        softAssertApi.assertEquals(response.getTitle().getRaw(), conf.getTestConfigPosts().getTitleUpdate());
        softAssertApi.assertEquals(response.getContent().getRaw(), conf.getTestConfigPosts().getContentUpdate());
        softAssertApi.assertEquals(response.getExcerpt().getRaw(), conf.getTestConfigPosts().getExcerptUpdate());
        softAssertApi.assertAll();
        Posts posts = dbSteps.getPost(Sql.selectPostById(postId));
        SoftAssert softAssertDb = new SoftAssert();
        softAssertDb.assertEquals(posts.getId(), postId);
        softAssertDb.assertEquals(posts.getStatus(), conf.getTestConfigPosts().getStatusUpdate());
        softAssertDb.assertEquals(posts.getTitle(), conf.getTestConfigPosts().getTitleUpdate());
        softAssertDb.assertEquals(posts.getContent(), conf.getTestConfigPosts().getContentUpdate());
        softAssertDb.assertEquals(posts.getExcerpt(), conf.getTestConfigPosts().getExcerptUpdate());
        softAssertDb.assertAll();
    }
}
