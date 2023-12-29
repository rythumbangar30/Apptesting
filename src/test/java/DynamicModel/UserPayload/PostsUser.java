package DynamicModel.UserPayload;

import lombok.Data;

@Data
public class PostsUser {
    private int id;
    private String user_id;
    private String title;
    private String body;

}
