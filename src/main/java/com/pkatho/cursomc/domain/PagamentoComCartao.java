package com.pkatho.cursomc.domain;

import java.io.Serializable;

import javax.persistence.Entity;

import com.pkatho.cursomc.domain.enuns.EstadoPagamento;

@Entity
public class PagamentoComCartao extends Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private Integer numeroDeParcelas;
	
	public PagamentoComCartao() {
		
	}

	public PagamentoComCartao(Integer id, EstadoPagamento pagamento, Pedido pedido, Integer numeroDeParcelas) {
		super(id, pagamento, pedido);
		
		this.setNumeroDeParcelas(numeroDeParcelas);
	}

	public Integer getNumeroDeParcelas() {
		return numeroDeParcelas;
	}

	public void setNumeroDeParcelas(Integer numeroDeParcelas) {
		this.numeroDeParcelas = numeroDeParcelas;
	}
		
}
