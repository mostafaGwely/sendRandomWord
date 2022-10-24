package com.example.demo;

import com.google.gson.JsonObject;

public class Word {
    private long id;

    private String word;
    private String description;
    private String cat;
    private String p;

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCat() {
        return cat;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public String getP() {
        return p;
    }

    public void setP(String p) {
        this.p = p;
    }

    public String createJson(){
        JsonObject xx = new  JsonObject();

        xx.addProperty("word", this.word);
        xx.addProperty("description", this.description);
        xx.addProperty("cat", this.cat);
        xx.addProperty("p", this.p);

        String xString = xx.toString();

        return xString;

    }
}

