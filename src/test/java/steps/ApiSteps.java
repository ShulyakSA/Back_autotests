package steps;

import config.TestConfigFactory;
import models.RequestCommentsModel;
import models.RequestPostsModel;
import models.ResponseCommentsModel;
import models.ResponsePostsModel;
import io.qameta.allure.Step;
import io.restassured.specification.RequestSpecification;

public class ApiSteps {
    protected static TestConfigFactory conf = TestConfigFactory.getInstance();

    @Step("Выполнить POST запрос '/posts'.  Получен ответ c кодом ответа 201 с телом в формате JSON")
    public ResponsePostsModel createPost(RequestSpecification requestSpecification) {
        RequestPostsModel requestModel = RequestPostsModel.builder().build();
        return requestSpecification
                .body(requestModel)
                .when()
                .post(conf.getWebConfig().getPostsPath())
                .then()
                .statusCode(201)
                .extract()
                .body().as(ResponsePostsModel.class);
    }

    @Step("Выполнить POST запрос '/posts/{id}'.  Получен ответ c кодом ответа 200 с телом в формате JSON")
    public ResponsePostsModel updatePost(RequestSpecification requestSpecification, int id) {
        RequestPostsModel requestModel = RequestPostsModel.builder()
                .status(conf.getTestConfigPosts().getStatusUpdate())
                .title(conf.getTestConfigPosts().getTitleUpdate())
                .content(conf.getTestConfigPosts().getContentUpdate())
                .excerpt(conf.getTestConfigPosts().getExcerptUpdate())
                .build();
        return requestSpecification
                .body(requestModel)
                .when()
                .post(conf.getWebConfig().getPostsPath() + "/" + id)
                .then()
                .statusCode(200)
                .extract()
                .body().as(ResponsePostsModel.class);
    }

    @Step("Выполнить DELETE запрос '/posts/{id}'.  Получен ответ c кодом ответа 200 с телом в формате JSON")
    public ResponsePostsModel deletePost(RequestSpecification requestSpecification, int id) {
        return requestSpecification
                .when()
                .delete(conf.getWebConfig().getPostsPath() + "/" + id)
                .then()
                .statusCode(200)
                .extract()
                .body().as(ResponsePostsModel.class);
    }

    @Step("Выполнить POST запрос '/comments'.  Получен ответ c кодом ответа 201 с телом в формате JSON")
    public ResponseCommentsModel createComment(RequestSpecification requestSpecification, int postId) {
        RequestCommentsModel requestModel = RequestCommentsModel.builder().post(postId).build();
        return requestSpecification
                .body(requestModel)
                .when()
                .post(conf.getWebConfig().getCommentPath())
                .then()
                .statusCode(201)
                .extract()
                .body().as(ResponseCommentsModel.class);
    }

    @Step("Выполнить POST запрос '/comments'.  Получен ответ c кодом ответа 200 с телом в формате JSON")
    public ResponseCommentsModel updateComment(RequestSpecification requestSpecification, int commentId) {
        RequestCommentsModel requestModel = RequestCommentsModel.builder()
                .authorName(conf.getTestConfigComments().getAuthorNameUpdate())
                .content(conf.getTestConfigComments().getContentUpdate())
                .status(conf.getTestConfigComments().getStatusUpdate())
                .build();
        return requestSpecification
                .body(requestModel)
                .when()
                .post(conf.getWebConfig().getCommentPath() + "/" + commentId)
                .then()
                .statusCode(200)
                .extract()
                .body().as(ResponseCommentsModel.class);
    }

    @Step("Выполнить DELETE запрос '/comments/{commentId}'.  Получен ответ c кодом ответа 200 с телом в формате JSON")
    public ResponseCommentsModel deleteComment(RequestSpecification requestSpecification, int commentId) {
        return requestSpecification
                .when()
                .delete(conf.getWebConfig().getCommentPath() + "/" + commentId)
                .then()
                .statusCode(200)
                .extract()
                .body().as(ResponseCommentsModel.class);
    }
}
