package com.example.muhammadrazavasnan.moviehub;

import java.io.Serializable;

/**
 * Created by Muhammad Raza Vasnan on 3/16/2019.
 */

public class comment implements Serializable {
    String name,text;

    public comment() {
    }

    public comment(String name, String text) {

        this.name = name;
        this.text = text;
    }
}
