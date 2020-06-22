package com.github.jp.erudo.eanticheat.event.events.packetevents;

import com.github.jp.erudo.eanticheat.event.EACEvent;

public class SwingEvent extends EACEvent {

    private final long timeStamp;

    public SwingEvent() {
        timeStamp = (System.nanoTime() / 1000000);
    }

    public long getTimeStamp() {
        return timeStamp;
    }

}
