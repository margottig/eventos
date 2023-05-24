package com.example.eventos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.eventos.models.Estados;
import com.example.eventos.models.Evento;
import com.example.eventos.models.User;
import com.example.eventos.services.EventoService;
import com.example.eventos.services.UserService;

import jakarta.validation.Valid;

@Controller
public class EventController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private EventoService eventoService;
	
	
	@PostMapping("/new/event")
	public String crearEvento(@Valid @ModelAttribute("event") Evento evento,
			BindingResult resultado, Model viewModel) {
		if(resultado.hasErrors()) {
			User usuario = userService.findUserById(evento.getOrganizador().getId());
			viewModel.addAttribute("usuario", usuario);
			viewModel.addAttribute("estados", Estados.estados);
			return "/event/index.jsp";
		}
		eventoService.crearEvento(evento);
		return "redirect:/events";
	}
	

}
