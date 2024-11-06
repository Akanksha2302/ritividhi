package com.dev.ritividhi.models;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

@Data
@Document(collection = "events")
public class Event {
    @Id    
    private String eventId;

    @NotBlank(message = "Event name is required")
    private String eventName;

    @NotBlank(message = "Puja associated is required")
    private String pujaAssociated;

    @NotBlank(message = "Puja ID is required")
    private String pujaId;

    @NotBlank(message = "Event date is required")
    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Event date must be in the format YYYY-MM-DD")
    private String eventDate;

    @NotBlank(message = "Event place is required")
    private String eventPlace;

    @NotBlank(message = "Event mode is required")
    private String eventMode;

    @NotBlank(message = "Event details are required")
    private String eventDetails;

    @NotNull(message = "Event cost is required")
    @Min(value = 0, message = "Event cost must be a positive number")
    private Double eventCost;

    @NotBlank(message = "Event start time is required")
    @Pattern(regexp = "^([01]\\d|2[0-3]):([0-5]\\d)$", message = "Event start time must be in HH:MM format")
    private String eventStartTime;

    @NotBlank(message = "Event image is required")
    private String eventImage;
}
