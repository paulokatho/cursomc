package com.pkatho.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;

import com.pkatho.cursomc.domain.Cliente;
import com.pkatho.cursomc.domain.enuns.TipoCliente;
import com.pkatho.cursomc.dto.ClienteNewDTO;
import com.pkatho.cursomc.repository.ClienteRepository;
import com.pkatho.cursomc.resources.exception.FieldMessage;
import com.pkatho.cursomc.services.validation.utils.BR;

public class ClienteInsertValidator implements ConstraintValidator<ClienteInsert, ClienteNewDTO> { //aqui vai o nome da anotação e a classe do DTO

	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteInsert ann) {
	}
	
	/**
	 * Entendendo a validação
	 * Esse metodo isValid() tem que retornar true se o objeto for verdadeiro e false se o objeto for falso.
	 * Ele esta ligado com o @Valid lá no ClienteResource, ou seja, nos @Valid vai bater nesse metodo aqui.
	 * 
	 * Dentro dessa classe também teremos o metodo initialize() que podemos colocar alguma programação de inicialização e validação.
	 * Nesse caso agora não vai precisar.
	 * 
	 */

	@Override
	public boolean isValid(ClienteNewDTO objDto, ConstraintValidatorContext context) {
		
		List<FieldMessage> list = new ArrayList<>();
		
		//validando cpf ou cnpj e email
		if (objDto.getTipo().equals(TipoCliente.PESSOAFISICA.getCod()) && !BR.isValidCPF(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CPF inválido"));
		}

		if (objDto.getTipo().equals(TipoCliente.PESSOAJURIDICA.getCod()) && !BR.isValidCNPJ(objDto.getCpfOuCnpj())) {
			list.add(new FieldMessage("cpfOuCnpj", "CNPJ inválido"));
		}

		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null) {
			list.add(new FieldMessage("email", "Email já existente"));
		}
		
		//aqui permite colocar nossa lista de erros para dentro da lista de erros do framework
		//jogando a lista de erro dentro do frameword
		for (FieldMessage e : list) {
			context.disableDefaultConstraintViolation();
			context.buildConstraintViolationWithTemplate(e.getMessage()).addPropertyNode(e.getFieldName())
					.addConstraintViolation();
		}
		return list.isEmpty();
	}
}
