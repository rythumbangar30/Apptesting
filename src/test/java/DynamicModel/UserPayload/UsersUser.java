package DynamicModel.UserPayload;

import lombok.Data;

@Data
public class UsersUser {
    private int id;
    private String name;
    private String email;
    private String gender;
    private String status;
    public String getUsersName(){
        return name;
    }
    public void setUsersName(String name){
          this.name=name;
    }
    public String getUsersEmail(){
        return email;
    }
    public void setUsersEmail(String email){
        this.email=email;
    }
    public String getUsersGender(){
        return gender;
    }
    public void setUsersGender(String gender){
        this.gender=gender;
    }
    public String getUsersStatus(){
        return status;
    }
    public void setUsersStatus(String status){
        this.status=status;
    }

}
