package com.example.eventos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventos.models.Evento;
import com.example.eventos.repositories.EventoRepo;

@Service
public class EventoService {
	@Autowired
	private EventoRepo eventoRepo;
	
	public Evento crearEvento(Evento evento) {
		return eventoRepo.save(evento);
	}
	

}
