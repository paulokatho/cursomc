package com.pkatho.cursomc.dto;

import java.io.Serializable;

import com.pkatho.cursomc.domain.Categoria;

public class CategoriaDTO implements Serializable {

	private static final long serialVersionUID = 1L;
	private Integer id;
	private String nome;
	
	public CategoriaDTO() {
		
	}
	
	//convertendo em um lista de DTO
	//responsavel por criar uma CategoriaDTO a partir da classe Categoria
	public CategoriaDTO(Categoria obj) {
		//agora instanciar o DTO através de categoria
		id = obj.getId();
		nome = obj.getNome();
	}
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}
	
}
