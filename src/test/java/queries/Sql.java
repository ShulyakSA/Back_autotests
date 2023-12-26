package queries;

public class Sql {
    public static String selectPostById(int id) {
        return "SELECT * FROM wp_posts WHERE id=" + id;
    }

    public static String deletePostById(int id) {
        return "DELETE FROM wp_posts WHERE id=" + id;
    }

    public static String selectCommentById(int id) {
        return "SELECT * FROM wp_comments WHERE comment_ID=" + id;
    }

    public static String deleteCommentById(int id) {
        return "DELETE FROM wp_comments WHERE comment_ID=" + id;
    }

    public static String insertPost() {
        return "INSERT INTO wp_posts (post_author, post_date, post_date_gmt, post_content, post_title, " +
                "post_excerpt, post_status, comment_status, ping_status, to_ping, pinged, post_modified, " +
                "post_modified_gmt, post_content_filtered, post_type, comment_count) " +
                "VALUES (1, SYSDATE(), SYSDATE(), ?, ?, ?, ?, 'open', 'open', " +
                "'',  '', SYSDATE(), SYSDATE(), '', 'post', 1 );";
    }

    public static String insertComment() {
        return "INSERT INTO wp_comments (comment_post_ID, comment_author, comment_date, comment_date_gmt, " +
                "comment_content, comment_approved, comment_type, user_id)" +
                "VALUES (?, ?, SYSDATE(), SYSDATE(), ?, ?, 'comment', 1);";
    }
}
