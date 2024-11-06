package com.dev.ritividhi.services;

import com.dev.ritividhi.models.Event;
import com.dev.ritividhi.repository.EventRepository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    @Autowired
    private EventRepository eventRepository;

    // Method to save a new event
    public Event createEvent(Event event) {
        // Any additional business logic or checks can be performed here
        return eventRepository.save(event); // Save the event to the repository
    }
    
    public boolean deleteEventById(String eventId) {
        Optional<Event> event = eventRepository.findById(eventId);
        if (event.isPresent()) {
            eventRepository.delete(event.get());
            return true; // Event was found and deleted
        }
        return false; // Event not found
    }
}
