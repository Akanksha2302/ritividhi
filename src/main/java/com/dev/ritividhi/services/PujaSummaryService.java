package com.dev.ritividhi.services;


import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.dev.ritividhi.models.PujaSummary;
import com.dev.ritividhi.repository.PujaSummaryRepository;

@Service
public class PujaSummaryService {

    @Autowired
    private PujaSummaryRepository repository;

    public List<PujaSummary> getAllPujas() {
        return repository.findAll();
    }
  
    public List<PujaSummary> findByPujaId(String pujaId) {
        return repository.findByPujaId(pujaId);
    }

    public PujaSummary savePuja(PujaSummary pujaSummary) {
        return repository.save(pujaSummary);
    }

    public boolean deletePuja(String pujaid) {
        if (repository.existsById(pujaid)) {
            repository.deleteById(pujaid);
            return true;
        }
        return false;
    }

    
}