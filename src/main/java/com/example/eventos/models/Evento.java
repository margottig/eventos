package com.example.eventos.models;

import java.util.Date;

import org.springframework.format.annotation.DateTimeFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;

@Entity
@Table(name="eventos")
public class Evento {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Future(message="Por ingresa una fecha posterior")
	@DateTimeFormat(pattern="yyyy-MM-dd")
	private Date fechaEvento;
	
	@NotBlank(message="Por favor ingresa el nombre del evento")
	private String nombreEvento;
	@NotBlank(message="Por favor ingresa la locacion del evento")
	private String ciudad;
	@NotBlank(message="Por favor selecciona un estado")
	private String estado;
	
	@Column(updatable = false)
	private Date createdAt;
	private Date updatedAt;
	
	// Relacion n:1 a Usuarios
	@ManyToOne(fetch=FetchType.LAZY)
	@JoinColumn(name="user_id")
	private User organizador;
	
	//CONSTRUCTOR
	public Evento() {
		
	}
	
	
	
	
	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Date getFechaEvento() {
		return fechaEvento;
	}


	public void setFechaEvento(Date fechaEvento) {
		this.fechaEvento = fechaEvento;
	}


	public String getNombreEvento() {
		return nombreEvento;
	}


	public void setNombreEvento(String nombreEvento) {
		this.nombreEvento = nombreEvento;
	}

	public String getCiudad() {
		return ciudad;
	}

	public void setCiudad(String ciudad) {
		this.ciudad = ciudad;
	}


	public String getEstado() {
		return estado;
	}

	public void setEstado(String estado) {
		this.estado = estado;
	}


	public Date getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}




	public Date getUpdatedAt() {
		return updatedAt;
	}




	public void setUpdatedAt(Date updatedAt) {
		this.updatedAt = updatedAt;
	}




	public User getOrganizador() {
		return organizador;
	}




	public void setOrganizador(User organizador) {
		this.organizador = organizador;
	}




	@PrePersist
	protected void onCreate() {
		this.createdAt = new Date();
	}

	@PreUpdate
	protected void onUpdate() {
		this.updatedAt = new Date();
	}
	
	

}
