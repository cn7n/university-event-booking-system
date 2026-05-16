package org.example.secondscene;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class EventStore {
    private static final List<Event> events = new ArrayList<>();
    private static final ObservableList<String> eventDisplays = FXCollections.observableArrayList();
    private static final ObservableList<String> registrations = FXCollections.observableArrayList();
    private static final Set<String> registeredTitles = new HashSet<>();

    public static void addEvent(Event event) {
        events.add(event);
        eventDisplays.add(toDisplay(event));
    }

    public static void updateEvent(int index, Event event) {
        events.set(index, event);
        eventDisplays.set(index, toDisplay(event));
    }

    public static void removeEvent(int index) {
        Event removed = events.remove(index);
        eventDisplays.remove(index);
        registeredTitles.remove(removed.getTitle());
        registrations.removeIf(r -> r.startsWith(removed.getTitle()));
    }

    public static Event getEvent(int index) { return events.get(index); }
    public static int size() { return events.size(); }

    public static ObservableList<String> getEventDisplays() { return eventDisplays; }
    public static ObservableList<String> getRegistrations() { return registrations; }

    public static boolean register(int index) {
        Event e = events.get(index);
        if (registeredTitles.contains(e.getTitle())) return false;
        registeredTitles.add(e.getTitle());
        registrations.add(toDisplay(e));
        return true;
    }

    public static void unregister(int registrationIndex) {
        if (registrationIndex < 0 || registrationIndex >= registrations.size()) return;
        String display = registrations.remove(registrationIndex);
        for (Event e : events) {
            if (display.startsWith(e.getTitle())) {
                registeredTitles.remove(e.getTitle());
                break;
            }
        }
    }

    public static boolean isRegistered(int eventIndex) {
        if (eventIndex < 0 || eventIndex >= events.size()) return false;
        return registeredTitles.contains(events.get(eventIndex).getTitle());
    }

    private static String toDisplay(Event e) {
        return e.getTitle() + "  ·  " + e.getCategory() + "\n"
                + e.getDescription() + "\n"
                + e.getDate() + "  |  " + e.getTime() + "  |  " + e.getLocation()
                + "  |  Capacity: " + e.getCapacity();
    }
}
