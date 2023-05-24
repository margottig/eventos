package com.example.eventos.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import com.example.eventos.models.Estados;
import com.example.eventos.models.Evento;
import com.example.eventos.models.LoginUser;
import com.example.eventos.models.User;
import com.example.eventos.services.UserService;

import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class UserController {
	
//	String[] estados = {"CA", "OR", "WA", "TX", "NV"};
	@Autowired
	private UserService userService;
	
	@GetMapping("/")
	public String index(Model viewModel) {
		viewModel.addAttribute("user", new User());
		viewModel.addAttribute("login", new LoginUser());
		viewModel.addAttribute("estados", Estados.estados);
		return "loginreg.jsp";
	}
	
	@PostMapping("/registration")
	public String register(@Valid @ModelAttribute("user") User usuario, 
			BindingResult resultado, Model viewModel) {
		if(resultado.hasErrors()) {
			viewModel.addAttribute("login", new LoginUser());
			viewModel.addAttribute("estados", Estados.estados);
			return "loginreg.jsp";
		}
		
		User usuarioRegistrado = userService.registerUser(usuario, resultado);
		viewModel.addAttribute("login", new LoginUser());
		if(usuarioRegistrado != null) {
			viewModel.addAttribute("succesRegister", "Gracias por registrarte, por favor login"); 	
		}
		return "loginreg.jsp";
	}
	
	@PostMapping("/login")
	public String login(@Valid @ModelAttribute("login") LoginUser loginuser, 
			BindingResult resultado, Model viewModel, HttpSession sesion) {
		if(resultado.hasErrors()) {
			viewModel.addAttribute("user", new User());
			return "loginreg.jsp";
		}
		if(userService.authenticateUser(loginuser.getEmail(), 
				loginuser.getPassword(), resultado)) {
			User usuarioLog = userService.findByEmail(loginuser.getEmail());
			sesion.setAttribute("userID",  usuarioLog.getId());
//			System.out.println(sesion.getAttribute("userID") + "atributo ");
			return "redirect:events";
		}else {
			viewModel.addAttribute("user", new User());
			viewModel.addAttribute("estados", Estados.estados);
			return "loginreg.jsp";
		}
	}
	
	@GetMapping("/events")
	public String welcome(HttpSession sesion, Model viewModel, @ModelAttribute("event") Evento evento) {
		Long userId = (Long) sesion.getAttribute("userID");
		if(userId == null) {
			return "redirect:/"; 
		}
		User usuario = userService.findUserById(userId);
		viewModel.addAttribute("usuario", usuario);
		viewModel.addAttribute("estados", Estados.estados);
		return "/event/index.jsp";
	}
	
	@GetMapping("/logout")
	 public String logout(HttpSession session) {
		 session.setAttribute("userID", null);
		 return "redirect:/";
	 }

}
