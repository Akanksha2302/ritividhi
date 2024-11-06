package com.dev.ritividhi.services;

import com.dev.ritividhi.models.Package;
import com.dev.ritividhi.repository.PackageRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PackageService {

	@Autowired
	PackageRepository packageRepository;

	public List<Package> getAllPackages() {
		return packageRepository.findAll();
	}

	public Optional<Package> getPackageById(String packageId) {
		return packageRepository.findById(packageId);
	}

	public Package savePackage(Package pack) {
		return packageRepository.save(pack);
	}

	public void deletePackage(String id) {
		packageRepository.deleteById(id);
	}

}