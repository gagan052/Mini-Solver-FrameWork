package com.gagan.minisolver.manifest;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.gagan.minisolver.event.Event;

public class SolverManifest {

    private final Map<RegistrationKey, List<SolverRegistration>> registrations
            = new HashMap<>();

    public void register(SolverRegistration registration) {

        RegistrationKey key = new RegistrationKey(
                registration.getCommand(),
                registration.getObjectType()
        );

        registrations
                .computeIfAbsent(key, k -> new ArrayList<>())
                .add(registration);
    }

    public List<SolverRegistration> findMatching(Event event) {

        RegistrationKey key = new RegistrationKey(
                event.getCommand(),
                event.getObjectType()
        );

        List<SolverRegistration> matching
                = registrations.getOrDefault(
                        key,
                        Collections.emptyList()
                );

        return matching.stream()
                .sorted(
                        java.util.Comparator.comparingInt(
                                SolverRegistration::getPriority
                        )
                )
                .toList();
    }
}
