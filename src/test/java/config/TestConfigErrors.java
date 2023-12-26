package config;

import lombok.Data;

@Data
public class TestConfigErrors {
    private String postsErrorCode;
    private String postsErrorMessage;
    private String commentsErrorCode;
    private String commentsErrorMessage;
}
