package com.dev.ritividhi.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.ritividhi.models.Addon;
import com.dev.ritividhi.repository.AddonRepository;

@Service
public class AddonService {


    @Autowired
    AddonRepository addonRepository;

    public List<Addon> getAllAddons() {
        return addonRepository.findAll();
    }

    public Addon saveAddon(Addon addon) {
        return addonRepository.save(addon);
    }
    
    public void deleteAddon(String addonId) {
        addonRepository.deleteById(addonId);
    }
}
