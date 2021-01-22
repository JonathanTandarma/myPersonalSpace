package com.gp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "aksesoris_detail")
public class AksesorisDetail {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name="ukuran")
	private String ukuran;
	
	@Column(name="warna_aksesoris_id")
	private int warnaAksesorisId;
		
	@Column(name = "connector_category_id")
	private int connectorCategoryId;
	
	@Column(name = "status")
	private String status;

		
	public int getWarnaAksesorisId() {
		return warnaAksesorisId;
	}

	public void setWarnaAksesorisId(int warnaAksesorisId) {
		this.warnaAksesorisId = warnaAksesorisId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getUkuran() {
		return ukuran;
	}

	public void setUkuran(String ukuran) {
		this.ukuran = ukuran;
	}

	public int getConnectorCategoryId() {
		return connectorCategoryId;
	}

	public void setConnectorCategoryId(int connectorCategoryId) {
		this.connectorCategoryId = connectorCategoryId;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	
	
}
