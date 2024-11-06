package com.dev.ritividhi.services;

import com.dev.ritividhi.models.Addon;
import com.dev.ritividhi.models.LiveEvents;
import com.dev.ritividhi.models.OnlinePuja;
import com.dev.ritividhi.models.OnlineTemple;
import com.dev.ritividhi.models.ParticipationOption;
import com.dev.ritividhi.models.Puja;
import com.dev.ritividhi.models.Temple;
import com.dev.ritividhi.repository.AddonRepository;
import com.dev.ritividhi.repository.OnlinePujaRepository;
import com.dev.ritividhi.repository.ParticipationOptionRepository;
import com.dev.ritividhi.repository.PujaRepository;
import com.dev.ritividhi.repository.TempleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class OnlinePujaService {

    @Autowired
    private OnlinePujaRepository onlinePujaRepository;
    
    @Autowired
    private PujaRepository pujaRepository;
    
    @Autowired
    private ParticipationOptionRepository participationOptionRepository;
    
    @Autowired
    private AddonRepository addonRepository;
    
    @Autowired
    private TempleRepository templeRepository;

    public List<OnlinePuja> getAllPujas() {
        return onlinePujaRepository.findAll();
    }

    public OnlinePuja getPujaById(String pujaId) {
        return onlinePujaRepository.findById(pujaId).orElse(null);
    }
//
//    public OnlinePuja createPuja(OnlinePuja onlinePuja) {
//        return onlinePujaRepository.save(onlinePuja);
//    }

    public OnlinePuja updatePuja(String pujaId, OnlinePuja onlinePuja) {
        onlinePuja.setPujaId(pujaId);
        return onlinePujaRepository.save(onlinePuja);
    }

    public void deletePuja(String pujaId) {
        onlinePujaRepository.deleteById(pujaId);
    }
    
    public OnlinePuja saveOnlinePuja(OnlinePuja onlinePuja) {
        // Step 1: Save the Puja details separately
        Puja puja = new Puja();
        puja.setPujaId(onlinePuja.getPujaId());
        puja.setName(onlinePuja.getName());
        puja.setSignificance(onlinePuja.getSignificance());

        // Save the Puja details
        Puja savedPuja = pujaRepository.save(puja);

        // Step 2: Update OnlinePuja with the saved Puja ID
        onlinePuja.setPujaId(savedPuja.getPujaId());

        // Step 3: Handle LiveEvents and OnlineTemples separately
        if (onlinePuja.getLiveEvents() != null && onlinePuja.getLiveEvents().getAvailableTemples() != null) {
            List<OnlineTemple> savedTemples = new ArrayList<>(); // To store saved OnlineTemples

            for (OnlineTemple onlineTemple : onlinePuja.getLiveEvents().getAvailableTemples()) {
                // Step 3.1: Save the Temple details in the temple collection
                Temple temple = new Temple();
                temple.setTempleId(onlineTemple.getTempleId());
                temple.setTempleName(onlineTemple.getTempleName());
                temple.setTempleLocation(onlineTemple.getTempleLocation());
                temple.setPrice(onlineTemple.getPrice());
                temple.setDescription(onlineTemple.getDescription());

                // Save the Temple details
                Temple savedTemple = templeRepository.save(temple);

                // Step 3.2: Save Participation Options if they exist
                if (onlineTemple.getParticipationOptions() != null) {
                    List<ParticipationOption> savedOptions = new ArrayList<>();

                    for (ParticipationOption option : onlineTemple.getParticipationOptions()) {
                        ParticipationOption newOption = new ParticipationOption();
                        newOption.setOptionId(option.getOptionId());
                        newOption.setName(option.getName());
                        newOption.setPrice(option.getPrice());
                        newOption.setIncludes(option.getIncludes());
                        newOption.setDescription(option.getDescription());

                        // Save the Participation Option
                        ParticipationOption savedOption = participationOptionRepository.save(newOption);
                        savedOptions.add(savedOption);
                    }
                    onlineTemple.setParticipationOptions(savedOptions); // Update onlineTemple with saved options
                }

                // Step 3.3: Save Addons if they exist
                if (onlineTemple.getAddons() != null) {
                    List<Addon> savedAddons = new ArrayList<>();

                    for (Addon addon : onlineTemple.getAddons()) {
                        Addon newAddon = new Addon();
                        newAddon.setAddonId(addon.getAddonId());
                        newAddon.setName(addon.getName());
                        newAddon.setPrice(addon.getPrice());
                        newAddon.setDescription(addon.getDescription());

                        // Save the Addon
                        Addon savedAddon = addonRepository.save(newAddon);
                        savedAddons.add(savedAddon);
                    }
                    onlineTemple.setAddons(savedAddons); // Update onlineTemple with saved addons
                }

                // Add the saved temple to the OnlineTemple list
                onlineTemple.setTempleId(savedTemple.getTempleId());
                savedTemples.add(onlineTemple); // Store the OnlineTemple
            }

            // Set the saved temples back to the LiveEvents
            LiveEvents liveEvents = new LiveEvents();
            liveEvents.setAvailableTemples(savedTemples);
            onlinePuja.setLiveEvents(liveEvents);
        }

        // Step 4: Save the OnlinePuja details
        OnlinePuja savedOnlinePuja = onlinePujaRepository.save(onlinePuja);

        return savedOnlinePuja; // Return the saved OnlinePuja
    }



}
