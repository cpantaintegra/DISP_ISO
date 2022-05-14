package com.integrasystemsonline.Utilidades;

import java.util.concurrent.Callable;

class MyTask implements Callable<Void> {

    private String name;

    private long sleepTime;

    public MyTask(String name, long sleepTime) {
        this.name = name;
        this.sleepTime = sleepTime;
    }

    @Override
    public Void call() throws Exception {
        Thread.sleep(this.sleepTime);
        return null;
    }
}
