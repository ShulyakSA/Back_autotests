package helpers;

import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Posts {
    private int id;
    private String status;
    private String title;
    private String content;
    private String excerpt;
}
