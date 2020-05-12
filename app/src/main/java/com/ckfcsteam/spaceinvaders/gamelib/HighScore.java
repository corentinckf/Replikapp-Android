package com.ckfcsteam.spaceinvaders.gamelib;

public class HighScore {
    private String mode;
    private int highScore;

    public HighScore(String mode, int highScore){
        this.highScore = highScore;
        this.mode = mode;
    }

    public String getMode() {
        return mode;
    }

    public int getHighScore() {
        return highScore;
    }
}
