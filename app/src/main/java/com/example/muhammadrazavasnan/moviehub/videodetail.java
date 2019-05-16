package com.example.muhammadrazavasnan.moviehub;

import java.io.Serializable;

/**
 * Created by Muhammad Raza Vasnan on 3/16/2019.
 */

public class videodetail implements Serializable{
    String name;
    String upvote;
    String downvote;
    String thumbnail;
    String desc;
    String link;
    String id;
    public videodetail(String name, String upvote, String downvote, String thumbnail, String desc, String link,String id) {
        this.name = name;
        this.upvote = upvote;
        this.downvote = downvote;
        this.thumbnail = thumbnail;
        this.desc = desc;
        this.link = link;
        this.id=id;
    }


    public videodetail() {

    }
}
