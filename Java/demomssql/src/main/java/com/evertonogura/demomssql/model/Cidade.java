package com.evertonogura.demomssql.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "Cidade")
public class Cidade {
	
	private Short id;
	private Short id_Estado;
	private String descricao;
	
	public Cidade() {
		
	}
	
	public Cidade(Short id, Short id_Estado, String descricao) {
		this.id = id;
		this.id_Estado = id_Estado;
		this.descricao = descricao;
	}
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	public Short getId() {
		return id;
	}
	
	public void setId(Short id) {
		this.id = id;
	}
	
	@Column(name = "Id_Estado", nullable = false)
	public Short getIdEstado() {
		return id_Estado;
	}
	
	public void setIdEstado(Short id_Estado) {
		this.id_Estado = id_Estado;
	}
	
	@Column(name = "Descricao", nullable = false, columnDefinition = "VARCHAR(250) NOT NULL")
	public String getDescricao() {
		return descricao;
	}
	
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
	
	@Override
	public String toString() {
		return "Cidade [id=" + id + ", id_Estado=" + id_Estado + ", descricao=" + descricao + "]";
	}
	
}
