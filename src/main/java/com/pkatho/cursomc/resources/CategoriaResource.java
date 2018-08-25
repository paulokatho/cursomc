package com.pkatho.cursomc.resources;


import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.pkatho.cursomc.domain.Categoria;
import com.pkatho.cursomc.services.CategoriaService;

@RestController
@RequestMapping(value="/categorias")
public class CategoriaResource {
		
	@Autowired
	private CategoriaService service;

	@RequestMapping(value="/{id}", method=RequestMethod.GET)
	public ResponseEntity<?> find(@PathVariable Integer id) {

		Categoria obj = service.find(id);

		return ResponseEntity.ok().body(obj);		
	}
		
	@RequestMapping(method=RequestMethod.POST)
	public ResponseEntity<Void> insert(Categoria obj) {
		
		obj = service.insert(obj);
		//esse cara abaixo retorno a uri do recurso novo que foi criado - seção 3, aula 31 curso ionic 
		URI uri = ServletUriComponentsBuilder.fromCurrentRequest()
					.path("/{id}").buildAndExpand(obj.getId()).toUri();
		
		return ResponseEntity.created(uri).build();
		
	}
}
