package com.beardsmcgee.whodat;

/**
 * Person object contains a name and a url reference to an image of that person.
 */
public class Person {
    private String name;
    private String imageUrl;

    public Person(String name, String imageUrl){
        this.name = name;
        this.imageUrl = imageUrl;
    }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

    public void setImageUrl(String imageUrl){
        this.imageUrl = imageUrl;
    }

    public String getImageUrl(){
        return imageUrl;
    }
}
