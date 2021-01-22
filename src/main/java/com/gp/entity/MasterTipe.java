package com.gp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "master_tipe")
public class MasterTipe {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name="nama_tipe")
	private String namaTipe;
	
	@Column(name="kode_tipe")
	private String kodeTipe;
	
	@Column(name="category_id")
	private int category_id;
	
	public int getCategory_id() {
		return category_id;
	}

	public void setCategory_id(int category_id) {
		this.category_id = category_id;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNamaTipe() {
		return namaTipe;
	}

	public void setNamaTipe(String namaTipe) {
		this.namaTipe = namaTipe;
	}

	public String getKodeTipe() {
		return kodeTipe;
	}

	public void setKodeTipe(String kodeTipe) {
		this.kodeTipe = kodeTipe;
	}

	
}
