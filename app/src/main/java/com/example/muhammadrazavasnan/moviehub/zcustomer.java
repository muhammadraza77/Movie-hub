package com.example.muhammadrazavasnan.moviehub;

import com.google.firebase.database.IgnoreExtraProperties;

import java.io.Serializable;

/**
 * Created by Mujtaba on 11/24/2018.
 */
@IgnoreExtraProperties
public class zcustomer implements Serializable {
    public String userid, name , email,country , phone,type, genre;

    public zcustomer(String userid,String name, String email, String country, String phone,String type, String genre) {
        this.userid  = userid;
        this.name = name;
        this.email = email;
        this.country = country;
        this.phone = phone;
        this.type= type;
        this.genre = genre;
    }

    public zcustomer() {
    }
}
