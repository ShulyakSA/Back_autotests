package steps;

import helpers.Comments;
import helpers.DbUtils;
import helpers.Posts;
import io.qameta.allure.Step;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

@Slf4j
public class DbSteps {
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
            connection.close();
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
                comments.setCommentAuthor(result.getString("comment_author"));
                comments.setCommentContent(result.getString("comment_content"));
                comments.setCommentApproved(result.getString("comment_approved"));
            }
            connection.close();
            result.close();
        } catch (SQLException e) {
            log.error("SQL ERROR. QUERY WAS NOT COMPLETED -> " + query);
            e.printStackTrace();
        }
        return comments;
    }
}
