package com.example.pocketcards.classes;

import com.google.firebase.database.Exclude;

import java.io.Serializable;

public class FlashCard implements Serializable {
    @Exclude
    public String key;
    public String header;
    public String content;
    public String answer;
    public String difficulty;
    public int color;

    public String getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(String difficulty) {
        this.difficulty = difficulty;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public FlashCard() {}

    public FlashCard(String Header, String Content, String Answer, String Key, String Difficulty, int Color) {
        header = Header;
        content = Content;
        answer = Answer;
        key = Key;
        difficulty = Difficulty;
        color = Color;
    }

    public String getHeader() {
        return header;
    }

    public void setHeader(String header) {
        this.header = header;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public int getColor() {
        return color;
    }

    public void setColor(int color) {
        this.color = color;
    }

    @Override
    public String toString() {
        return this.header;
    }
}
