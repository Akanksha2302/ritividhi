package com.dev.ritividhi.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import com.dev.ritividhi.models.Puja;
import com.dev.ritividhi.repository.PackageRepository;
import com.dev.ritividhi.repository.PujaRepository;

@Service
public class PujaService {

	@Autowired
	PujaRepository pujaRepository;

	@Autowired
	PackageRepository pkgRepo;

	public List<Puja> findAll() {
		return pujaRepository.findAll();
	}

	public Puja savePuja(Puja puja) {
		return pujaRepository.save(puja);
	}

	public Optional<Puja> findById(String pujaId) {
		return pujaRepository.findById(pujaId);
	}

	public void deletePujaById(String pujaId) {
		pujaRepository.deleteById(pujaId);
	}
}
