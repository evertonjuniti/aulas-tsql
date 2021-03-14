package com.evertonogura.demomssql.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.evertonogura.demomssql.exception.BadRequestException;
import com.evertonogura.demomssql.exception.ResourceNotFoundException;
import com.evertonogura.demomssql.model.Cidade;
import com.evertonogura.demomssql.repository.CidadeRepository;

@RestController
@RequestMapping("/api/v1")
public class CidadeController {
	
	@Autowired
	private CidadeRepository repository;
	
	@GetMapping("/cidades")
	public List<Cidade> getAllCidades(@RequestParam(name = "descricao") Optional<String> descricao) 
			throws ResourceNotFoundException {
		if (descricao.isPresent())
			return repository.findByDescricao(descricao.get());
		else
			return repository.findAll();
	}
	
	@GetMapping("/cidades/{id}")
	public ResponseEntity<Cidade> getCidadeById(@PathVariable(value = "id") Short cidadeId) 
		throws ResourceNotFoundException {
		Cidade cidade = repository.findById(cidadeId)
				.orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada para este id :: " + cidadeId));
		return ResponseEntity.ok().body(cidade);
	}
	
    @PutMapping("/cidades/{id}")
    public ResponseEntity<Cidade> updateCidade(@PathVariable(value = "id") Short cidadeId,
         @RequestBody Cidade cidadeDetails) throws ResourceNotFoundException, BadRequestException {
    	if (cidadeId != cidadeDetails.getId())
    		throw new BadRequestException("Id da Cidade não corresponde com o payload da requisição.");
    	
    	if (cidadeDetails.getIdEstado() <= 0)
    		throw new BadRequestException("Id do Estado não pode ser menor ou igual a zero.");
    	
    	if (cidadeDetails.getDescricao() == null)
    		throw new BadRequestException("Descrição da Cidade não pode ser nulo.");
    	
    	Cidade cidade = repository.findById(cidadeId)
    			.orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada para este id :: " + cidadeId));
    	
    	cidade.setIdEstado(cidadeDetails.getIdEstado());
    	cidade.setDescricao(cidadeDetails.getDescricao());
        final Cidade updatedCidade = repository.save(cidade);
        return ResponseEntity.ok(updatedCidade);
    }
    
    @PatchMapping("/cidades/{id}")
    public ResponseEntity<Cidade> updateCidadePatch(@PathVariable(value = "id") Short cidadeId,
         @RequestBody Cidade cidadeDetails) throws ResourceNotFoundException, BadRequestException {
    	if (cidadeDetails.getDescricao() == null)
    		throw new BadRequestException("Descrição da Cidade não pode ser nulo.");
    	
    	Cidade cidade = repository.findById(cidadeId)
    			.orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada para este id :: " + cidadeId));
    	
    	cidade.setDescricao(cidadeDetails.getDescricao());
        final Cidade updatedCidade = repository.save(cidade);
        return ResponseEntity.ok(updatedCidade);
    }
    
	@PostMapping("/cidades")
    public Cidade createCidade(@RequestBody Cidade cidade) {
        return repository.save(cidade);
    }
	
    @DeleteMapping("/cidades/{id}")
    public Map<String, Boolean> deleteEmployee(@PathVariable(value = "id") Short cidadeId)
         throws ResourceNotFoundException {
    	Cidade cidade = repository.findById(cidadeId)
    			.orElseThrow(() -> new ResourceNotFoundException("Cidade não encontrada para este id :: " + cidadeId));

    	repository.delete(cidade);
        Map<String, Boolean> response = new HashMap<>();
        response.put("deletado", Boolean.TRUE);
        return response;
    }
	
}
