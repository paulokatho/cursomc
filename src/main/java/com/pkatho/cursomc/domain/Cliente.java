package com.pkatho.cursomc.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CollectionTable;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.pkatho.cursomc.domain.enuns.TipoCliente;

@Entity
public class Cliente implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/** Serialização Cíclica
	 * 
	 * Aqui tem um problema de serialização cíclica, onde a jvm tenta serializar o cliente e o endereço e entra em um tipo 'loop' eterno que gera erro.
	 * Como no relacionamento entre essas duas tabelas é de n/n o cliente conhece seu endereço e o endereço acessa o seu cliente, então isso é o que gera
	 *  essa serialização cíclica. Entre cliente e telefones não existe, pois segundo a modelagem do diagrama esse é um relacionamento simples.
	 *  Usamos o @JsonManagedReference  em cliente para nos proteger quanto a serialização cíclica e aí no endereço usamos no atributo 'cliente' o 
	 *   @JsonBackReference e dessa maneira nos protegemos quanto a esse problema.
	 *   
	 * Exemplo de erro no Postman: Quando se faz uma requisição get passando o id de cliente ele retorna: Expected ',' instead of 't'
	 * 
	 * Durante a aula após essa pesquisa acima deu erro de serialização cíclica, pois segundo o diagrama <endereco tem relacionamento entre cidade-estado> e 
	 *  isso gerou o erro. Então temos que proteger cidade e estado.
	 *  Então vai ficar assim: cliente serializa o endereco, mas o endereco não serializa o cliente. Desta forma entre cidade e estado vamos deixar a cidade
	 *   serializar o estado dela, mas não vamos deixar o estado serializar o estado.
	 *  
	 * 
	 */

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Integer id;
	private String nome;
	private String email;
	private String cpfOuCnpj;
	private Integer tipoCliente;
	
	@JsonManagedReference
	@OneToMany(mappedBy="cliente")
	private List<Endereco> enderecos = new ArrayList<>();
	
	//Essa é uma classe simples demais e ela pode ser implementada dessa maneira, como um set<>
	@ElementCollection
	@CollectionTable(name="TELEFONE")
	private Set<String> telefones = new HashSet<>(); 
	
	@JsonBackReference
	@OneToMany(mappedBy="cliente")
	private List<Pedido> pedidos = new ArrayList<>();
	
	public Cliente() {
		
	}

	public Cliente(Integer id, String nome, String email, String cpfOuCnpj, TipoCliente tipoCliente) {
		super();
		this.id = id;
		this.nome = nome;
		this.email = email;
		this.cpfOuCnpj = cpfOuCnpj;
		this.tipoCliente = tipoCliente.getCod();
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

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getCpfOuCnpj() {
		return cpfOuCnpj;
	}

	public void setCpfOuCnpj(String cpfOuCnpj) {
		this.cpfOuCnpj = cpfOuCnpj;
	}

	public TipoCliente getTipoCliente() {
		return TipoCliente.toEnum(tipoCliente);
	}

	public void setTipoCliente(TipoCliente tipoCliente) {
		this.tipoCliente = tipoCliente.getCod();
	}

	public List<Endereco> getEnderecos() {
		return enderecos;
	}

	public void setEnderecos(List<Endereco> enderecos) {
		this.enderecos = enderecos;
	}

	public Set<String> getTelefones() {
		return telefones;
	}

	public void setTelefones(Set<String> telefones) {
		this.telefones = telefones;
	}
	
	public List<Pedido> getPedidos() {
		return pedidos;
	}

	public void setPedidos(List<Pedido> pedidos) {
		this.pedidos = pedidos;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
