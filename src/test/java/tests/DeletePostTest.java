package tests;

import helpers.Posts;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Severity;
import io.qameta.allure.Story;
import models.ResponsePostsModel;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import queries.Sql;

import static io.qameta.allure.SeverityLevel.CRITICAL;

@Epic(value = "Задача D1. Тестирование API: работа с БД")
@Feature(value = "Метод posts")
public class DeletePostTest extends BaseTest {
    private int postId;

    @BeforeTest
    public void precondition() {
        ResponsePostsModel response = apiSteps.createPost(requestSpecification);
        postId = response.getId();
    }

    @Story(value = "Удаление поста с проверкой в БД")
    @Severity(CRITICAL)
    @Test(description = "Удаление поста, с проверкой тэга 'status'")
    public void createPosTest() {
        ResponsePostsModel response = apiSteps.deletePost(requestSpecification, postId);
        Assert.assertEquals(response.getStatus(), conf.getTestConfigPosts().getStatusDelete());
        Posts posts = dbSteps.getPost(Sql.selectPostById(postId));
        Assert.assertEquals(posts.getStatus(), conf.getTestConfigPosts().getStatusDelete());
    }
}
