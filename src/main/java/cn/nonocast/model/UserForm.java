package cn.nonocast.model;

import javax.validation.constraints.Size;

public class UserForm {
    @Size(min=5, max=30, message="邮箱地址不正确")
    private String email;
    @Size(min=6, max=30, message="密码不少于6位")
    private String password;
    @Size(min=4, max=30, message="用户名不少于4个字符")
    private String name;
    private Boolean admin;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Boolean getAdmin() {
        return admin;
    }

    public void setAdmin(Boolean admin) {
        this.admin = admin;
    }

    public UserForm() {
        this.admin = false;
    }
}
