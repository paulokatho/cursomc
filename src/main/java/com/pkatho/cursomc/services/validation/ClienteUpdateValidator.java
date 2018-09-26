package com.pkatho.cursomc.services.validation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerMapping;

import com.pkatho.cursomc.domain.Cliente;
import com.pkatho.cursomc.dto.ClienteDTO;
import com.pkatho.cursomc.repository.ClienteRepository;
import com.pkatho.cursomc.resources.exception.FieldMessage;

public class ClienteUpdateValidator implements ConstraintValidator<ClienteUpdate, ClienteDTO> { //aqui vai o nome da anotação e a classe do DTO

	//Esse cara serve para pegar um parametro que vem da url. Nesse caso é o Id quem vem na URI e não esta no corpo do json.
	@Autowired
	private HttpServletRequest request;
	
	@Autowired
	private ClienteRepository repo;
	
	@Override
	public void initialize(ClienteUpdate ann) {
	}

	@Override
	public boolean isValid(ClienteDTO objDto, ConstraintValidatorContext context) {
		
		//Esse cara pega o Map(lista) de variaveis que estao na requisição. Que vem na url.
		@SuppressWarnings({ "unused", "unchecked" })
		Map<String, String> map = (Map<String, String>) request.getAttribute(HandlerMapping.URI_TEMPLATE_VARIABLES_ATTRIBUTE);
		Integer uriId = Integer.parseInt(map.get("id"));//Passando o valor do id que veio na url para uma variavel Inteira.
		
		List<FieldMessage> list = new ArrayList<>();
		
		Cliente aux = repo.findByEmail(objDto.getEmail());
		if (aux != null && !aux.getId().equals(uriId)) {//verifica se o id do cliente aux é diferente do id do cara da url que vai fz o update
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
