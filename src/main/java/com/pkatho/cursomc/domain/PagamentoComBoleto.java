package com.pkatho.cursomc.domain;

import java.io.Serializable;
import java.time.LocalDateTime;

import javax.persistence.Entity;

import com.pkatho.cursomc.domain.enuns.EstadoPagamento;

@Entity
public class PagamentoComBoleto extends Pagamento implements Serializable {

	private static final long serialVersionUID = 1L;

	private LocalDateTime dataVencimento;
	private LocalDateTime dataPagamento;
	
	public PagamentoComBoleto() {
		
	}

	public PagamentoComBoleto(Integer id, EstadoPagamento pagamento, Pedido pedido, LocalDateTime dataVencimento, LocalDateTime dataPagamento) {
		super(id, pagamento, pedido);
	
		this.dataVencimento = dataVencimento;
		this.dataPagamento = dataPagamento;
	}

	public LocalDateTime getDataVencimento() {
		return dataVencimento;
	}

	public void setDataVencimento(LocalDateTime dataVencimento) {
		this.dataVencimento = dataVencimento;
	}

	public LocalDateTime getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(LocalDateTime dataPagamento) {
		this.dataPagamento = dataPagamento;
	}
	
	
}
