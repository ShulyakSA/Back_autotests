package steps;

import config.TestConfigFactory;
import helpers.Comments;
import helpers.DbUtils;
import helpers.Posts;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;
import queries.Sql;

import java.sql.*;

@Slf4j
public class DbSteps {
    protected static TestConfigFactory conf = TestConfigFactory.getInstance();

    @Step("Выполнить запрос в БД '{query}'")
    public Posts getPost(String query) {
        Posts posts = new Posts();
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                posts.setId(result.getInt("ID"));
                posts.setContent(result.getString("post_content"));
                posts.setTitle(result.getString("post_title"));
                posts.setExcerpt(result.getString("post_excerpt"));
                posts.setStatus(result.getString("post_status"));
            }
            result.close();
        } catch (SQLException e) {
            log.error("SQL ERROR. QUERY WAS NOT COMPLETED -> " + query);
            e.printStackTrace();
        }
        return posts;
    }

    @Step("Выполнить запрос в БД '{query}'")
    public Comments getComment(String query) {
        Comments comments = new Comments();
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            ResultSet result = preparedStatement.executeQuery();
            while (result.next()) {
                comments.setCommentId(result.getInt("comment_ID"));
                comments.setCommentPostId(result.getInt("comment_post_ID"));
                comments.setCommentAuthor(result.getString("comment_author"));
                comments.setCommentContent(result.getString("comment_content"));
                comments.setCommentApproved(result.getString("comment_approved"));
            }
            result.close();
        } catch (SQLException e) {
            log.error("SQL ERROR. QUERY WAS NOT COMPLETED -> " + query);
            e.printStackTrace();
        }
        return comments;
    }

    @Step("Выполнить запрос в БД '{query}'. Получен {id поста}")
    public Posts createPost(String query) {
        Posts posts = new Posts().builder()
                .content(conf.getTestConfigPosts().getContentCreate())
                .title(conf.getTestConfigPosts().getTitleCreate())
                .excerpt(conf.getTestConfigPosts().getExcerptCreate())
                .status(conf.getTestConfigPosts().getStatusCreate())
                .build();
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, posts.getContent());
            preparedStatement.setString(2, posts.getTitle());
            preparedStatement.setString(3, posts.getExcerpt());
            preparedStatement.setString(4, posts.getStatus());
            preparedStatement.executeUpdate();
            ResultSet result = preparedStatement.getGeneratedKeys();
            while (result.next()) {
                posts.setId(result.getInt(1));
            }
            result.close();
        } catch (SQLException e) {
            log.error("SQL ERROR. QUERY WAS NOT COMPLETED -> " + Sql.insertPost());
            e.printStackTrace();
        }
        return posts;
    }

    @Step("Выполнить запрос в БД '{query}'. Получен {id поста}")
    public Comments createComment(String query, int postId) {
        Comments comments = new Comments().builder()
                .commentPostId(postId)
                .commentAuthor(conf.getTestConfigComments().getAuthorNameCreate())
                .commentContent(conf.getTestConfigComments().getContentCreate())
                .commentApproved(conf.getTestConfigComments().getApprovedCreate())
                .build();
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setInt(1, comments.getCommentPostId());
            preparedStatement.setString(2, comments.getCommentAuthor());
            preparedStatement.setString(3, comments.getCommentContent());
            preparedStatement.setString(4, comments.getCommentApproved());
            preparedStatement.executeUpdate();
            ResultSet result = preparedStatement.getGeneratedKeys();
            while (result.next()) {
                comments.setCommentId(result.getInt(1));
            }
            result.close();
        } catch (SQLException e) {
            log.error("SQL ERROR. QUERY WAS NOT COMPLETED -> " + Sql.insertPost());
            e.printStackTrace();
        }
        return comments;
    }

    @Step("Выполнить запрос в БД '{query}'")
    public void delete(String query) {
        try (Connection connection = DbUtils.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            log.error("SQL ERROR. QUERY WAS NOT COMPLETED -> " + query);
            e.printStackTrace();
        }
    }
}
