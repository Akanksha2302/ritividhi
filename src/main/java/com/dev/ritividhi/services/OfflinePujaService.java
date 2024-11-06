package com.dev.ritividhi.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.ritividhi.models.Addon;
import com.dev.ritividhi.models.BhetOptions;
import com.dev.ritividhi.models.OfflinePuja;
import com.dev.ritividhi.models.Package;
import com.dev.ritividhi.models.Puja;
import com.dev.ritividhi.repository.PackageRepository;
import com.dev.ritividhi.repository.AddonRepository;
import com.dev.ritividhi.repository.BhetOptionRepository;
import com.dev.ritividhi.repository.OfflinePujaRepository;
import com.dev.ritividhi.repository.PujaRepository;

@Service
public class OfflinePujaService {

    @Autowired
    OfflinePujaRepository offlinePujaRepository;

    @Autowired
    PujaRepository pujaRepo;

    @Autowired
    PackageRepository pkgRepo;

    @Autowired
    AddonRepository addonRepo;
    
    @Autowired
    BhetOptionRepository bhetOptionsRepo;

    public Optional<OfflinePuja> findById(String pujaId) {
        return offlinePujaRepository.findById(pujaId);
    }

    
    public List<OfflinePuja> getAllOfflinePujas() {
        return offlinePujaRepository.findAll();
    }


    public OfflinePuja save(OfflinePuja offlinePujaRequest) {
        Puja puja = new Puja();
        puja.setName(offlinePujaRequest.getName());
        puja.setPujaId(offlinePujaRequest.getPujaId());
        puja.setSignificance(offlinePujaRequest.getSignificance());

        // Save Puja to puja collection
        Puja savedPuja = pujaRepo.save(puja);

        // Now create the OfflinePuja, referencing the saved Puja
        OfflinePuja offlinePuja = new OfflinePuja();
        
        // Set inherited fields
        offlinePuja.setPujaId(savedPuja.getPujaId());
        offlinePuja.setName(savedPuja.getName());
        offlinePuja.setSignificance(savedPuja.getSignificance());
        offlinePuja.setDescription(offlinePujaRequest.getDescription());
        offlinePuja.setSamagri(offlinePujaRequest.getSamagri());
        offlinePuja.setHomePuja(offlinePujaRequest.getHomePuja());
        offlinePuja.setAddons(offlinePujaRequest.getAddons());
        offlinePuja.setGod(offlinePujaRequest.getGod());
        offlinePuja.setMode(offlinePujaRequest.getMode());

        // Save related Packages
        if (offlinePuja.getHomePuja() != null && offlinePuja.getHomePuja().getPackages() != null) {
            List<Package> savedPackages = new ArrayList<>();
            for (Package pkg : offlinePuja.getHomePuja().getPackages()) {
                List<Addon> savedAddons = new ArrayList<>();
                List<BhetOptions> savedBhetOptions = new ArrayList<>();

                // Handle Addons
                if (pkg.getAddOns() != null) {
                    for (Addon addon : pkg.getAddOns()) {
                        Addon savedAddon = addonRepo.save(addon);
                        savedAddons.add(savedAddon);
                    }
                }
                pkg.setAddOns(savedAddons.isEmpty() ? new ArrayList<>() : savedAddons); // Set to empty list if null or empty

//                // Handle BhetOptions
//                if (pkg.getBhetOptions() != null) {
//                    for (BhetOptions bhetOption : pkg.getBhetOptions()) {
//                        BhetOptions savedBhetOption = bhetOptionsRepo.save(bhetOption);
//                        savedBhetOptions.add(savedBhetOption);
//                    }
//                }
//                pkg.setBhetOptions(savedBhetOptions.isEmpty() ? new ArrayList<>() : savedBhetOptions); // Set to empty list if null or empty

                // Save the package
                Package savedPackage = pkgRepo.save(pkg);
                savedPackages.add(savedPackage);
            }
            offlinePuja.getHomePuja().setPackages(savedPackages);
        }

        // Save related Addons
        if (offlinePuja.getAddons() != null) {
            List<Addon> savedAddons = new ArrayList<>();
            for (Addon addon : offlinePuja.getAddons()) {
                Addon savedAddon = addonRepo.save(addon);
                savedAddons.add(savedAddon);
            }
            offlinePuja.setAddons(savedAddons);
        }

        return offlinePujaRepository.save(offlinePuja);
    }

    public void deletePuja(String pujaId) {
        Optional<OfflinePuja> pujaOptional = offlinePujaRepository.findById(pujaId);
        if (pujaOptional.isPresent()) {
            offlinePujaRepository.deleteById(pujaId);
        } else {
            throw new RuntimeException("Puja not found with ID: " + pujaId);
        }
    }
}

