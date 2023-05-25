package com.example.eventos.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.eventos.models.Evento;
import com.example.eventos.models.User;
import com.example.eventos.repositories.EventoRepo;

@Service
public class EventoService {
	@Autowired
	private EventoRepo eventoRepo;
	
	public Evento crearEvento(Evento evento) {
		return eventoRepo.save(evento);
	}
	
	public List<Evento> todosLosEventosConEstado(String estado){
		return eventoRepo.findByEstado(estado);
		
	}
	
	public List<Evento> todosLosEventosSinEstado(String estado){
		return eventoRepo.findByEstadoIsNot(estado);
		
	}
	
	public Evento findById(Long id) {
		return eventoRepo.findById(id).orElse(null);
	}
	
	//ACTUALIZAR EVENTO
	public Evento actualiarEvento(Evento evento) {
		return eventoRepo.save(evento);
	}
	
	//BORRAR EVENTO
	public void borrarEvento(Long id) {
		eventoRepo.deleteById(id);
	}
	
	//ADMINISTRAR EVENTO
	public void adminEventos(Evento evento, 
			User usuario, boolean unirse) {
//		System.out.println(evento.getNombreEvento() + usuario.getNombre() + " aquiiiii ");
		if(unirse) {
			evento.getAsistentes().add(usuario);
		}else {
			evento.getAsistentes().remove(usuario);
		}
		eventoRepo.save(evento);
	}
	
		

}
