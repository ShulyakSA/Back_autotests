package models;
/**
 * Модель запроса схемы comments
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import config.TestConfigFactory;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RequestCommentsModel {
    protected static TestConfigFactory conf = TestConfigFactory.getInstance();

    private int post;
    @Builder.Default
    private int author = 1;
    @Builder.Default
    @JsonProperty("author_name")
    private String authorName = conf.getTestConfigComments().getAuthorNameCreate();
    @Builder.Default
    private String content = conf.getTestConfigComments().getContentCreate();
    @Builder.Default
    private String status = conf.getTestConfigComments().getStatusCreate();
}