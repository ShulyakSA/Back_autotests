package tests;

import helpers.Posts;
import models.ResponsePostsModel;
import queries.Sql;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic(value = "Задача D1. Тестирование API: работа с БД")
@Feature(value = "Метод posts")
public class CreatePostTest extends BaseTest {
    @Story(value = "Создание поста с проверкой в БД")
    @Severity(CRITICAL)
    @Test(description = "Создания поста, с проверкой тэгов 'status', 'title', 'content', 'excerpt'")
    public void createPosTest() {
        ResponsePostsModel response = apiSteps.createPost(getRequestSpecification());
        SoftAssert softAssertApi = new SoftAssert();
        int postId = response.getId();
        softAssertApi.assertEquals(response.getStatus(), conf.getTestConfigPosts().getStatusCreate());
        softAssertApi.assertEquals(response.getTitle().getRaw(), conf.getTestConfigPosts().getTitleCreate());
        softAssertApi.assertEquals(response.getContent().getRaw(), conf.getTestConfigPosts().getContentCreate());
        softAssertApi.assertEquals(response.getExcerpt().getRaw(), conf.getTestConfigPosts().getExcerptCreate());
        softAssertApi.assertAll();
        Posts posts = dbSteps.getPost(Sql.selectPostById(postId));
        SoftAssert softAssertDb = new SoftAssert();
        softAssertDb.assertEquals(posts.getId(), postId);
        softAssertDb.assertEquals(posts.getStatus(), conf.getTestConfigPosts().getStatusCreate());
        softAssertDb.assertEquals(posts.getTitle(), conf.getTestConfigPosts().getTitleCreate());
        softAssertDb.assertEquals(posts.getContent(), conf.getTestConfigPosts().getContentCreate());
        softAssertDb.assertEquals(posts.getExcerpt(), conf.getTestConfigPosts().getExcerptCreate());
        softAssertDb.assertAll();
    }
}
