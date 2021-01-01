package com.evertonogura.demomssql.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.evertonogura.demomssql.model.Cidade;

@Repository
public interface CidadeRepository extends JpaRepository<Cidade, Short> {
	
	/*
	@Query(value = "SELECT Id, Id_Estado, Descricao "
			+ "FROM Cidade "
			+ "WHERE Descricao = CAST(:descricao AS VARCHAR(250))", 
		   nativeQuery = true)
	 */
	List<Cidade> findByDescricao(@Param("descricao") String descricao);
	
}
