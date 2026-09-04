package com.gagan.minisolver.event;

import java.util.Map;

public class Event {

    private Command command;
    private String objectType;
    private String objectId;
    private Map<String, Object> data;

    public Event(
            Command command,
            String objectType,
            String objectId,
            Map<String, Object> data) {

        this.command = command;
        this.objectType = objectType;
        this.objectId = objectId;
        this.data = data;
    }

    public Command getCommand() {
        return command;
    }

    public String getObjectType() {
        return objectType;
    }

    public String getObjectId() {
        return objectId;
    }

    public Map<String, Object> getData() {
        return data;
    }

    @Override
    public String toString() {
        return "Event{" +
                "command=" + command +
                ", objectType='" + objectType + '\'' +
                ", objectId='" + objectId + '\'' +
                ", data=" + data +
                '}';
    }
}