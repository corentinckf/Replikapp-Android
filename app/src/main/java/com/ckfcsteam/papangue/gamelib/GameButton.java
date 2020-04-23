package com.ckfcsteam.papangue.gamelib;

import android.content.Context;

public class GameButton extends GameObject {
    public GameButton(int renderRessource, Context context) {
        super(renderRessource, context);
    }

    public Boolean checkIfClicked(float touchx, float touchy){
        return(touchx < this.getCordx() + this.getWidth() &&
                touchx > this.getCordx() &&
                touchy < this.getCordy() + this.getHeight() &&
                touchy > this.getCordy());
    }
}
