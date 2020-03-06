package com.gdx.pickdem.util;

import com.badlogic.gdx.utils.TimeUtils;

public class Timer {
    private long startTimer;
    private int countdown;

    public Timer() {
    }

    public void startTimer() {
        startTimer = TimeUtils.nanoTime();
    }

    public void updateCountDownText() {
        countdown = (int) Utils.secondsSince(startTimer);
    }

    public int getCountdown(){
        return Math.max((Constants.TIME - countdown), 0);
    }
}
