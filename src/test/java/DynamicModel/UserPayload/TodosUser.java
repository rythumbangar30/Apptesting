package DynamicModel.UserPayload;

import lombok.Data;

@Data
public class TodosUser {
    private int id;
    private String user_id;
    private String title;
    private String due_on;
    private String status;

}
