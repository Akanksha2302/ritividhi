package com.dev.ritividhi.services;

import com.dev.ritividhi.models.OnlineEvents;
import com.dev.ritividhi.repository.OnlineEventsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OnlineEventsService {

	@Autowired
	private OnlineEventsRepository onlineEventsRepository;

	public OnlineEvents save(OnlineEvents event) {
		return onlineEventsRepository.save(event);
	}

	public Optional<OnlineEvents> findById(String id) {
		return onlineEventsRepository.findById(id);
	}

	public List<OnlineEvents> findAll() {
		return onlineEventsRepository.findAll();
	}

	public void delete(String id) {
		onlineEventsRepository.deleteById(id);
	}
}
