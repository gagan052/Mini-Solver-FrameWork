package com.gagan.minisolver.manifest;

import java.util.Objects;

import com.gagan.minisolver.event.Command;

public class RegistrationKey {

    private final Command command;
    private final String objectType;

    public RegistrationKey(Command command, String objectType) {
        this.command = command;
        this.objectType = objectType;
    }

    public Command getCommand() {
        return command;
    }

    public String getObjectType() {
        return objectType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof RegistrationKey)) return false;

        RegistrationKey that = (RegistrationKey) o;

        return command == that.command &&
                Objects.equals(objectType, that.objectType);
    }

    @Override
    public int hashCode() {
        return Objects.hash(command, objectType);
    }
}