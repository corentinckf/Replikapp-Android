package com.ckfcsteam.spaceinvaders.gamelib;

/**
 * Classe qui represente un meilleur score
 */
public class HighScore {
    private String mode;
    private int highScore;

    public HighScore(String mode, int highScore){
        this.highScore = highScore;
        this.mode = mode;
    }

    /* Getter sur les attributs */
    public String getMode() {
        return mode;
    }

    public int getHighScore() {
        return highScore;
    }
}
