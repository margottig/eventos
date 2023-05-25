package com.example.eventos.repositories;

import org.springframework.data.repository.CrudRepository;

import com.example.eventos.models.Mensaje;

public interface MensajeRepo extends CrudRepository<Mensaje, Long>{

}
