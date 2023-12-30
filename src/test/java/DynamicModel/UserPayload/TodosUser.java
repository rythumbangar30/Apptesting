package DynamicModel.UserPayload;

import lombok.Data;

import java.util.Date;

@Data
public class TodosUser {
    private int id;
    private String user_id;
    private String title;
    private Date due_on;
    private String status;
    public String getTodosUser_id(){
        return user_id;
    }
    public void setTodoUser_id(String user_id){
        this.user_id=user_id;
    }
    public String getTodosTitle(){
        return title;
    }
    public void setTodoTitle(String title){
        this.title=title;
    }    public Date getTodosDue_on(){
        return due_on;
    }
    public void setTodoDue_on(Date due_on){
        this.due_on=due_on;
    }    public String getTodosStatus(){
        return status;
    }
    public void setTodoStatus(String status){
        this.status=status;
    }
}
