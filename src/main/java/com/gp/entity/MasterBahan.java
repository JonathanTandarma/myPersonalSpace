package com.gp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "master_bahan")
public class MasterBahan {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name="nama_bahan")
	private String namaBahan;
	
	@Column(name="kode_bahan")
	private String kodeBahan;

	@Column(name="category_id")
	private int categoryId;
	
	public int getCategoryId() {
		return categoryId;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNamaBahan() {
		return namaBahan;
	}

	public void setNamaBahan(String namaBahan) {
		this.namaBahan = namaBahan;
	}

	public String getKodeBahan() {
		return kodeBahan;
	}

	public void setKodeBahan(String kodeBahan) {
		this.kodeBahan = kodeBahan;
	}

	
	
	
}
