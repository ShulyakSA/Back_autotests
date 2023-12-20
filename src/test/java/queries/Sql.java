package queries;

public class Sql {
    public static String selectPostById(int id) {
        return "SELECT * FROM wp_posts WHERE id=" + id;
    }
    public static String selectCommentById(int id) {
        return "SELECT * FROM wp_comments WHERE comment_ID=" + id;
    }
}
