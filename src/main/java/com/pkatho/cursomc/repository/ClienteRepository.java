package com.pkatho.cursomc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.pkatho.cursomc.domain.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

}
