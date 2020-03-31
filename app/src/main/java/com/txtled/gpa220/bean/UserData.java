package com.txtled.gpa220.bean;

import org.greenrobot.greendao.annotation.Convert;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Mr.Quan on 2020/3/30.
 */
@Entity
public class UserData implements Serializable {
    public static final long serialVersionUID = 1L;

    @Id
    private Long id;
    private String userName;
    private String post;
    private String postCode;
    private String birth;
    private int sex;//man:0  woman:1 unknown:2
    @Convert(columnType = String.class, converter = FloatConverter.class)
    private List<Float> data;

    public UserData() {
    }

    @Generated(hash = 1736951955)
    public UserData(Long id, String userName, String post, String postCode, String birth,
            int sex, List<Float> data) {
        this.id = id;
        this.userName = userName;
        this.post = post;
        this.postCode = postCode;
        this.birth = birth;
        this.sex = sex;
        this.data = data;
    }

    public UserData(String userName, String post, String postCode, String birth, int sex) {
        this.userName = userName;
        this.post = post;
        this.postCode = postCode;
        this.birth = birth;
        this.sex = sex;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getBirth() {
        return birth;
    }

    public void setBirth(String birth) {
        this.birth = birth;
    }

    public List<Float> getData() {
        return data;
    }

    public void setData(List<Float> data) {
        this.data = data;
    }

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getSex() {
        return this.sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }
}
