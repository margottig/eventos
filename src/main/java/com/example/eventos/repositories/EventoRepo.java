package com.example.eventos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.eventos.models.Evento;

public interface EventoRepo extends CrudRepository<Evento, Long>{

}
