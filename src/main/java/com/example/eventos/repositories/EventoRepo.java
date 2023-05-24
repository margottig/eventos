package com.example.eventos.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.eventos.models.Evento;

public interface EventoRepo extends CrudRepository<Evento, Long>{

	List<Evento> findByEstado(String estado);
	List<Evento> findByEstadoIsNot(String estado);
}

