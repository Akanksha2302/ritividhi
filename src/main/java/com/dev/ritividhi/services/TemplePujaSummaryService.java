package com.dev.ritividhi.services;

import com.dev.ritividhi.models.TemplePuja;
import com.dev.ritividhi.models.TemplePujaDetails;
import com.dev.ritividhi.models.TemplePujaSummary;
import com.dev.ritividhi.models.TemplePujaSummaryDetails;
import com.dev.ritividhi.repository.TemplePujaRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TemplePujaSummaryService {

	@Autowired
	private TemplePujaRepository templePujaRepository;

	public TemplePujaSummary getSummaryById(String id) {
		TemplePuja templePuja = templePujaRepository.findById(id).orElse(null);
		if (templePuja == null) {
			return null; // Handle not found case
		}

		TemplePujaSummary summary = new TemplePujaSummary();
		summary.setTempleId(templePuja.getTempleId());
		summary.setTempleName(templePuja.getTempleName());
		summary.setTempleLocation(templePuja.getTempleLocation());
		summary.setDescription(templePuja.getDescription());
		summary.setPrice(templePuja.getPrice());
		summary.setTempleImage(templePuja.getTempleImage());

		// Create a list to hold puja details
		List<TemplePujaSummaryDetails> pujaDetails = new ArrayList<>();

		// Populate puja details from the associated TemplePujaDetails
		for (TemplePujaDetails pujaDetail : templePuja.getPujas()) {
			TemplePujaSummaryDetails detail = new TemplePujaSummaryDetails();
			detail.setPujaId(pujaDetail.getPujaId());
			detail.setName(pujaDetail.getName());
			detail.setSignificance(pujaDetail.getSignificance());
			detail.setPujaDescription(pujaDetail.getDescription());
			detail.setPujaPrice(pujaDetail.getPrice());

			pujaDetails.add(detail);
		}

		summary.setPujaDetails(pujaDetails); // Set the puja details in the summary
		return summary;
	}
	
	public List<TemplePujaSummary> getAllTemplePujaSummaries() {
	    List<TemplePujaSummary> summaries = new ArrayList<>();
	    
	    List<TemplePuja> templePujas = templePujaRepository.findAll(); // Fetch all TemplePuja records

	    for (TemplePuja templePuja : templePujas) {
	        TemplePujaSummary summary = new TemplePujaSummary();
	        summary.setTempleId(templePuja.getTempleId());
	        summary.setTempleName(templePuja.getTempleName());
	        summary.setTempleLocation(templePuja.getTempleLocation());
	        summary.setDescription(templePuja.getDescription());
	        summary.setPrice(templePuja.getPrice());
	        summary.setTempleImage(templePuja.getTempleImage());

	        // Create a list to hold puja details
	        List<TemplePujaSummaryDetails> pujaDetails = new ArrayList<>();

	        // Populate puja details from the associated TemplePujaDetails
	        for (TemplePujaDetails pujaDetail : templePuja.getPujas()) {
	            TemplePujaSummaryDetails detail = new TemplePujaSummaryDetails(); // Correct type
	            detail.setPujaId(pujaDetail.getPujaId());
	            detail.setName(pujaDetail.getName());
	            detail.setSignificance(pujaDetail.getSignificance());
	            detail.setPujaDescription(pujaDetail.getDescription()); // Ensure this method exists in TemplePujaSummaryDetails
	            detail.setPujaPrice(pujaDetail.getPrice()); // Ensure this method exists in TemplePujaSummaryDetails
	            
	            pujaDetails.add(detail);
	        }

	        summary.setPujaDetails(pujaDetails); // Set the puja details in the summary
	        summaries.add(summary); // Add the summary to the list
	    }
	    
	    return summaries; // Return the list of summaries
	}

}
