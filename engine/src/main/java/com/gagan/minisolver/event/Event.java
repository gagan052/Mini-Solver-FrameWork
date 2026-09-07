package com.gagan.minisolver.event;

import com.gagan.minisolver.model.Criteria;

public class Event {

    private Command command;
    private String objectType;
    private String objectId;
    private Criteria criteria;

    public Event() {
        // Required by Jackson
    }

    public Event(
            Command command,
            String objectType,
            String objectId,
            Criteria criteria) {

        this.command = command;
        this.objectType = objectType;
        this.objectId = objectId;
        this.criteria = criteria;
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

    public Criteria getCriteria() {
        return criteria;
    }

    public void setCommand(Command command) {
        this.command = command;
    }

    public void setObjectType(String objectType) {
        this.objectType = objectType;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public void setCriteria(Criteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public String toString() {
        return "Event{" +
                "command=" + command +
                ", objectType='" + objectType + '\'' +
                ", objectId='" + objectId + '\'' +
                ", criteria=" + criteria +
                '}';
    }
}