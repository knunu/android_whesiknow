package com.knunu.android.whesiknow;

import java.util.Date;

/**
 * Created by Knunu on 2016. 7. 23..
 */
public class User {
    public static final String TAG = LoginActivity.class.getSimpleName();

    private int id;
    private String email;
    private String login_group;
    private String password;
    private String name;
    private String date_joined;
    private String last_login;
    private int is_active;
    private String profile_image;
    private String thumbnail_image;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLogin_group() {
        return login_group;
    }

    public void setLogin_group(String login_group) {
        this.login_group = login_group;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDate_joined() {
        return date_joined;
    }

    public void setDate_joined(String date_joined) {
        this.date_joined = date_joined;
    }

    public String getLast_login() {
        return last_login;
    }

    public void setLast_login(String last_login) {
        this.last_login = last_login;
    }

    public int isIs_active() {
        return is_active;
    }

    public void setIs_active(int is_active) {
        this.is_active = is_active;
    }

    public String getProfile_image() {
        return profile_image;
    }

    public void setProfile_image(String profile_image) {
        this.profile_image = profile_image;
    }

    public String getThumbnail_image() {
        return thumbnail_image;
    }

    public void setThumbnail_image(String thumbnail_image) {
        this.thumbnail_image = thumbnail_image;
    }
}
