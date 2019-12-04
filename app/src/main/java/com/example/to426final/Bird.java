package com.example.to426final;

public class Bird {
    public String birdname;
    public int zipcode;
    public String username;
    public int importance;


    public Bird(){

    }


    public Bird(String createBirdName, int createZipCode, String createPersonName) {
        this.birdname = createBirdName;
        this.importance = 0;
        this.username = createPersonName;
        this.zipcode = createZipCode;

    }
    public void incrementImportance(){
        importance++;
    }
}

