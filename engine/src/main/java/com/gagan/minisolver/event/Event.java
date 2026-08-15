package com.gagan.minisolver.event;

public class Event {

    private Command command;
    private String objectType;
    private String objectId;

    public Event(Command command, String objectType, String objectId) {
        this.command = command;
        this.objectType = objectType;
        this.objectId = objectId;
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

    @Override
    public String toString() {
        return "Event{" +
                "command=" + command +
                ", objectType='" + objectType + '\'' +
                ", objectId='" + objectId + '\'' +
                '}';
    }
}