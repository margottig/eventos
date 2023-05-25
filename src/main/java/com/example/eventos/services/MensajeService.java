package com.example.eventos.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventos.models.Evento;
import com.example.eventos.models.Mensaje;
import com.example.eventos.models.User;
import com.example.eventos.repositories.MensajeRepo;

@Service
public class MensajeService {
	
	@Autowired
	private MensajeRepo mensajeRepo;
	
	
	public void comentario(User usuario, Evento evento, String comentario) {
		Mensaje men = new Mensaje(usuario, evento, comentario);
		mensajeRepo.save(men);
	}

}
