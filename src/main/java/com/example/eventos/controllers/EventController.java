package com.example.eventos.controllers;

import java.text.SimpleDateFormat;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.example.eventos.models.Estados;
import com.example.eventos.models.Evento;
import com.example.eventos.models.User;
import com.example.eventos.services.EventoService;
import com.example.eventos.services.MensajeService;
import com.example.eventos.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class EventController {
	
	@Autowired
	private UserService userService;
	@Autowired
	private EventoService eventoService;
	@Autowired 
	private MensajeService mensajeService;
	
	// metodo interno para formatear fecha
	public String fecha(Long idEvento) {
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
		return dateformat.format(eventoService.findById(idEvento).getFechaEvento());
	}
	
	
	@GetMapping("/events")
	public String welcome(HttpSession sesion, Model viewModel, @ModelAttribute("event") Evento evento) {
		Long userId = (Long) sesion.getAttribute("userID");
		if(userId == null) {
			return "redirect:/"; 
		}
		User usuarioLog = userService.findUserById(userId);
		viewModel.addAttribute("estadoUsuario", eventoService.todosLosEventosConEstado(usuarioLog.getEstado()));
		viewModel.addAttribute("usuario", usuarioLog);
		viewModel.addAttribute("estadoNoUsuario", eventoService.todosLosEventosSinEstado(usuarioLog.getEstado()));
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
		// opcion para formatera fecha
		viewModel.addAttribute("fecha", this.fecha(unEvento.getId()));
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
	
	// Controlador para unirse o cancelar de un evento
	@GetMapping("/event/{idEvento}/{idUsuario}/{opcion}")
	public String adminEventos(@PathVariable("idEvento")Long idEvento, 
			@PathVariable("opcion")String opcion, 
			@PathVariable("idUsuario")Long idUsuario,
			HttpSession sesion) {
		Long userId = (Long) sesion.getAttribute("userID");
		if(userId == null) {
			return "redirect:/"; 
		}
		Evento unEvento = eventoService.findById(idEvento);
		boolean unirse = (opcion.equals("unirse"));
		User usuario = userService.findUserById(idUsuario);
		
		eventoService.adminEventos(unEvento, usuario, unirse);
				
		return "redirect:/events";
	}
	
	//MOSTRAR EL EVENTO
	@GetMapping("/events/{idEvento}")
	public String mostrarEvento(Model viewModel, 
			@PathVariable("idEvento") Long idEvento, 
			HttpSession sesion) {
		Long userId = (Long) sesion.getAttribute("userID");
		if(userId == null) {
			return "redirect:/"; 
		}
		viewModel.addAttribute("event", eventoService.findById(idEvento));
//		viewModel.addAttribute("event", eventoService.findById(idEvento));
		return "/event/mostrar.jsp";
	}
		
	@PostMapping("/events/{idEvento}/comentario")
	public String comentario(@PathVariable("idEvento") Long idEvento,
			@RequestParam("comment") String comentario ,
			HttpSession sesion, RedirectAttributes redirecAttr) {
		
		Long userId = (Long) sesion.getAttribute("userID");
		if(userId == null) {
			return "redirect:/"; 
		}
		if(comentario.equals("")) {
			redirecAttr.addFlashAttribute("error", "Por favor escribe un comentario");
			return "redirect:/events/"+idEvento;
		}
		Evento evento = eventoService.findById(idEvento);
		User usuario = userService.findUserById(userId);
		mensajeService.comentario(usuario, evento, comentario);
		
		return "redirect:/events/"+idEvento;
	}
	
	
	
	
	@GetMapping("/logout")
	public String logout(HttpSession session) {
		session.setAttribute("userID", null);
		return "redirect:/";
	}
	

}
