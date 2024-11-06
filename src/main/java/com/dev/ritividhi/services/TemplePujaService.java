package com.dev.ritividhi.services;

import com.dev.ritividhi.models.TemplePuja;
import com.dev.ritividhi.models.TemplePujaDetails;
import com.dev.ritividhi.models.Addon;
import com.dev.ritividhi.models.BhetOptions;
import com.dev.ritividhi.models.Package;
import com.dev.ritividhi.models.ParticipationOption;
import com.dev.ritividhi.repository.AddonRepository;
import com.dev.ritividhi.repository.BhetOptionRepository;
import com.dev.ritividhi.repository.PackageRepository;
import com.dev.ritividhi.repository.ParticipationOptionRepository;
import com.dev.ritividhi.repository.TemplePujaRepository;
import com.dev.ritividhi.repository.TempleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import com.dev.ritividhi.models.Puja;
import com.dev.ritividhi.models.Temple;
import com.dev.ritividhi.models.TemplePuja;
import com.dev.ritividhi.models.TemplePujaDetails;
import com.dev.ritividhi.repository.PujaRepository;
import com.dev.ritividhi.repository.TemplePujaDetailsRepository;
import com.dev.ritividhi.repository.TemplePujaRepository;
import com.dev.ritividhi.repository.TempleRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TemplePujaService {

	@Autowired
	private TemplePujaRepository templePujaRepository;

	@Autowired
	private TempleRepository templeRepository;

	@Autowired
	private PackageRepository packageRepository;

	@Autowired
	private TemplePujaDetailsRepository templePujaDetailsRepository;

	@Autowired
	private ParticipationOptionRepository participationOptionRepository;

	@Autowired
	private AddonRepository addonRepository;

	@Autowired
	private PujaRepository pujaRepository;

	public List<TemplePuja> getAllTemplePujas() {
		return templePujaRepository.findAll();
	}

	public TemplePuja createTemplePuja(TemplePuja templePuja) {
		return templePujaRepository.save(templePuja);
	}

	public void deleteTemplePuja(String templeId) {
		templePujaRepository.deleteById(templeId);
	}

	public TemplePuja saveTemple(TemplePuja templePuja) {
		// Step 1: Save the Temple data separately
		Temple temple = new Temple();
		temple.setTempleId(templePuja.getTempleId());
		temple.setTempleName(templePuja.getTempleName());
		temple.setTempleLocation(templePuja.getTempleLocation());
		temple.setDescription(templePuja.getDescription());
		temple.setPrice(templePuja.getPrice());

		// Save the temple details
		Temple savedTemple = templeRepository.save(temple);

		// Step 2: Update TemplePuja with the saved Temple ID
		templePuja.setTempleId(savedTemple.getTempleId());

		// Step 3: Save the TemplePuja details
		TemplePuja savedTemplePuja = templePujaRepository.save(templePuja);

		// Step 4: Handle Pujas separately
		if (templePuja.getPujas() != null) {
			List<TemplePujaDetails> savedPujas = new ArrayList<>();

			for (TemplePujaDetails templePujaDetails : templePuja.getPujas()) {
				if (templePujaDetails != null) {
					// Step 4.1: Save the Puja data separately
					Puja puja = new Puja();
					puja.setPujaId(templePujaDetails.getPujaId());
					puja.setName(templePujaDetails.getName());
					puja.setSignificance(templePujaDetails.getSignificance());
					// Set any other Puja fields as necessary
					Puja savedPuja = pujaRepository.save(puja); // Save the Puja

					// Set the saved Puja back into TemplePujaDetails
					templePujaDetails.setPujaId(savedPuja.getPujaId());

					// Step 4.2: Handle Packages
					if (templePujaDetails.getPackages() != null) {
						List<Package> savedPackages = new ArrayList<>();

						for (Package pkg : templePujaDetails.getPackages()) {
							// Step 4.2.1: Save Addons if they exist
							List<Addon> savedAddons = new ArrayList<>();
							if (pkg.getAddOns() != null) {
								for (Addon addon : pkg.getAddOns()) {
									Addon savedAddon = addonRepository.save(addon);
									savedAddons.add(savedAddon);
								}
								pkg.setAddOns(savedAddons); // Update package with saved addons
							}

							// Save the Package
							Package savedPackage = packageRepository.save(pkg);
							savedPackages.add(savedPackage);
						}
						templePujaDetails.setPackages(savedPackages); // Update TemplePujaDetails with saved packages
					}

					// Step 4.3: Handle Participation Options
					if (templePujaDetails.getParticipationOptions() != null) {
						List<ParticipationOption> savedOptions = new ArrayList<>();

						for (ParticipationOption option : templePujaDetails.getParticipationOptions()) {
							ParticipationOption savedOption = participationOptionRepository.save(option);
							savedOptions.add(savedOption);
						}
						templePujaDetails.setParticipationOptions(savedOptions);
					}

					// Save the TemplePujaDetails
					TemplePujaDetails savedPujaDetails = templePujaDetailsRepository.save(templePujaDetails);
					savedPujas.add(savedPujaDetails);
				}
			}
			savedTemplePuja.setPujas(savedPujas); // Set saved pujas
		}

		return savedTemplePuja; // Return the saved TemplePuja
	}

	public Optional<TemplePuja> getTemplePujaById(String templeId) {
		return templePujaRepository.findByTempleId(templeId);
	}

	public void deletePujaById(String templePujaId) {
		templePujaRepository.deleteById(templePujaId);
	}
}
