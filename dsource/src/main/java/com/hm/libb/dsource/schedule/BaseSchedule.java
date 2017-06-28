package com.hm.libb.dsource.schedule;

/**
 * Created by huangming on 2017/6/18.
 */
public abstract class BaseSchedule extends Thread {

    private boolean flag = true;
    private int SLEEP_TIME;

    public BaseSchedule() {
        this(2000);
    }

    public BaseSchedule(int SLEEP_TIME) {
        this.SLEEP_TIME = SLEEP_TIME;
    }

    @Override
    public void run() {
        while (flag) {
            wear();
            try {
                sleep(SLEEP_TIME);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void startWear() {
        flag = true;
        this.start();
    }

    public void stopWear() {
        flag = false;
    }

    protected abstract void wear();

}
