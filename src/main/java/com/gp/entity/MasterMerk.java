package com.gp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "master_merk")
public class MasterMerk {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name="nama_merk")
	private String namaMerk;
	
	@Column(name="kode")
	private String kodeMerk;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNamaMerk() {
		return namaMerk;
	}

	public void setNamaMerk(String namaMerk) {
		this.namaMerk = namaMerk;
	}

	public String getKodeMerk() {
		return kodeMerk;
	}

	public void setKodeMerk(String kodeMerk) {
		this.kodeMerk = kodeMerk;
	}

	
}
