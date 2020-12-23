package itmo.healthtracking.HealthTrackingBackEnd.model;


import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table(name = "users")
public class Users extends AuditModel{

    @Id @GeneratedValue
    private Long usersId;

    private String usersName;
    private String password;

    public Long getUsersId() {
        return usersId;
    }

    public void setUsersId(Long usersId) {
        this.usersId = usersId;
    }

    public String getUsersName() {
        return usersName;
    }

    public void setUsersName(String usersName) {
        this.usersName = usersName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
