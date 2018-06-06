package models;

import java.util.*;
import javax.persistence.*;

import play.db.jpa.*;

@Entity(name="user_info")
public class User extends Model {

    //继承自 model    id有 jpa 维护

    //编译后为   private String  filed     public void set(...)   get()[....]

    public String email;
    public String password;
    public String fullname;
    public boolean isAdmin;

    public User(String email, String password, String fullname) {
        this.email = email;
        this.password = password;
        this.fullname = fullname;
    }

    public static User connect(String email, String password) {
        //jpa 方法   查询返回第一条数据      email = ''  and   password = ''
        return find("byEmailAndPassword", email, password).first();
    }


}