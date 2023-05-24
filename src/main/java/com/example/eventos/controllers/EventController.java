package com.example.eventos.controllers;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import com.example.eventos.models.Estados;
import com.example.eventos.models.Evento;
import com.example.eventos.models.User;
import com.example.eventos.services.EventoService;
import com.example.eventos.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class EventController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private EventoService eventoService;
	
	@GetMapping("/events")
	public String welcome(HttpSession sesion, Model viewModel, @ModelAttribute("event") Evento evento) {
		Long userId = (Long) sesion.getAttribute("userID");
		if(userId == null) {
			return "redirect:/"; 
		}
		User usuarioLog = userService.findUserById(userId);
		viewModel.addAttribute("estadoUsuario", eventoService.todosLosEventosConEstado(usuarioLog.getEstado()));
		viewModel.addAttribute("usuario", usuarioLog);
		viewModel.addAttribute("estados", Estados.estados);
		
		return "/event/index.jsp";
	}
	
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
	
	//EDITAR EVENTO
	@GetMapping("/events/{idEvento}/edit")
	public String editarEvento(@PathVariable("idEvento") Long idEvento,
			@ModelAttribute("event") Evento evento, HttpSession sesion,
			Model viewModel) {
		Long userId = (Long) sesion.getAttribute("userID");
		if(userId == null) {
			return "redirect:/"; 
		}
		Evento unEvento = eventoService.findById(idEvento);
		if(unEvento == null ) {
			return "redirect:/events";
		}
		String[] listaEstados = Arrays.stream(Estados.estados)
		        .filter(estado -> !estado.contains(unEvento.getEstado()))
		        .toArray(String[]::new);
//		System.out.println(eventoService.todosLosEventosSinEstado(unEvento.getEstado()) + "que es esto");
		viewModel.addAttribute("usuario", userService.findUserById(userId));
		viewModel.addAttribute("evento", unEvento);
		viewModel.addAttribute("estados", listaEstados);
		return "/event/editar.jsp";
	}
	
	@PutMapping("/{id}")
	public String actualizarEvento(@Valid @ModelAttribute("event") Evento evento, BindingResult resultado,
			@PathVariable("id") Long id,
			HttpSession sesion,
			Model viewModel) {
		Long userId = (Long) sesion.getAttribute("userID");
		if(userId == null) {
			return "redirect:/"; 
		}
		
		if(resultado.hasErrors()) {
			Evento unEvento = eventoService.findById(id);
			if(unEvento == null ) {
				return "redirect:/events";
			}
			String[] listaEstados = Arrays.stream(Estados.estados)
			        .filter(estado -> !estado.contains(unEvento.getEstado()))
			        .toArray(String[]::new);
			
			viewModel.addAttribute("usuario", userService.findUserById(userId));
			viewModel.addAttribute("evento", unEvento);
			viewModel.addAttribute("estados", listaEstados);
//			viewModel.addAttribute("estados", Estados.estados);
			return "/event/editar.jsp";
		}
		System.out.println(" SI INGRESAMOS POR ACAS");
		eventoService.actualiarEvento(evento);
		return "redirect:/events";
		
	}
	
	@DeleteMapping("/events/{id}")
	public String borrarEvento(@PathVariable("id") Long id) {
		eventoService.borrarEvento(id);
		return "redirect:/events";
	}
	
	
	
	
	
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.setAttribute("userID", null);
		return "redirect:/";
	}
	

}
