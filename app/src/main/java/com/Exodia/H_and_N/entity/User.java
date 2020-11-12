package com.Exodia.H_and_N.entity;



import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.Date;

@Entity(tableName = "user_table")
public class User {
    @PrimaryKey(autoGenerate = false)
    private int id;
    @Ignore
    private  String u_id;
    @Ignore
    private  String name;
    @ColumnInfo(name = "email")
    private  String email;
    @Ignore
    private String image;
    @Ignore
    private String encrypted_password;
    @Ignore
    private  String salt;
    @Ignore
    private  String telephone;
    @Ignore
    private  String addresse;
    @Ignore
    private Date created_at;
    @Ignore
    private  Date updated_at;
    @Ignore
    private  String msgerror ; ;

    @Ignore
    public User() {
    }
    public User(String image) {
        this.image = image;
    }
    public User(String error , String name) {
        this.msgerror = error;
        this.name = name;
    }

    public  User(int id , String email){
        this.id = id;
        this.email =email;

    }
    @Ignore
    public User(int id,String uid, String name, String email, @NonNull String password) {
        this.id=id;
        this.u_id = uid;
        this.name = name;
        this.email = email;
        this.encrypted_password = password;
    }

    @Ignore
    public User(int id, String uid, String name, String email,String image ,String password, String salt, Date created_at, Date updated_at) {
        this.id = id;
        this.u_id = uid;
        this.name = name;
        this.email = email;
        this.image = image;
        this.encrypted_password = password;
        this.salt = salt;
        this.created_at = created_at;
        this.updated_at = updated_at;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUid() {
        return u_id;
    }

    public void setUid(String uid) {
        this.u_id = uid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return encrypted_password;
    }

    public void setPassword(String password) {
        this.encrypted_password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    public Date getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Date updated_at) {
        this.updated_at = updated_at;
    }


    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getAddresse() {
        return addresse;
    }

    public void setAddresse(String addresse) {
        this.addresse = addresse;
    }

    public String getError() {
        return msgerror ;
    }

    public void setError(String error) {
        msgerror = error;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", u_id='" + u_id + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", image='" + image + '\'' +
                ", encrypted_password='" + encrypted_password + '\'' +
                ", salt='" + salt + '\'' +
                ", telephone='" + telephone + '\'' +
                ", addresse='" + addresse + '\'' +
                ", created_at=" + created_at +
                ", updated_at=" + updated_at +
                ", msgerror='" + msgerror + '\'' +
                '}';
    }
}
