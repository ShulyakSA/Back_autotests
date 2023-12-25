package models;
/**
 * Модель запроса схемы posts
 */

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ResponsePostsModel {
    private int id;
    private String status;
    private ObjectModel title;
    private ObjectModel content;
    private ObjectModel excerpt;
}
