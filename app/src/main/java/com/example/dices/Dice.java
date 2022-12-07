package com.example.dices;

import android.widget.ImageView;

public class Dice {
    private int resourceId;
    private int diceValue;
    private ImageView imageView;
    private boolean isLocked = false;
    private boolean isLockedForever = false;

    public Dice( ImageView imageView, int diceValue) {
        this.imageView = imageView;
        setDiceValue(diceValue);
    }

    public void setDiceValue(int value) {
        diceValue = value;
        switch (value){
            case 0:
                resourceId = R.drawable.dice_empty;
                break;
            case 1:
                resourceId = R.drawable.dice_1;
                break;
            case 2:
                resourceId = R.drawable.dice_2;
                break;
            case 3:
                resourceId = R.drawable.dice_3;
                break;
            case 4:
                resourceId = R.drawable.dice_4;
                break;
            case 5:
                resourceId = R.drawable.dice_5;
                break;
            case 6:
                resourceId = R.drawable.dice_6;
                break;
        }
    }

    public void setLocked(boolean locked) {
        isLocked = locked;
    }

    public void setLockedForever(boolean lockedForever) {
        isLockedForever = lockedForever;
    }

    public void setResourceForImageView() {
        imageView.setImageResource(resourceId);
    }


    public int getResourceId() {
        return resourceId;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getDiceValue() {
        return diceValue;
    }

    public boolean isLocked() {
        return isLocked;
    }

    public boolean isLockedForever() {
        return isLockedForever;
    }
}
