package models;
/**
 * Модель ответа схемы posts
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
public class RequestPostsModel {
    protected static TestConfigFactory conf = TestConfigFactory.getInstance();

    @Builder.Default
    private String status = conf.getTestConfigPosts().getStatusCreate();
    @Builder.Default
    private String title = conf.getTestConfigPosts().getTitleCreate();
    @Builder.Default
    private String content = conf.getTestConfigPosts().getContentCreate();
    @Builder.Default
    private String excerpt = conf.getTestConfigPosts().getExcerptCreate();
}
