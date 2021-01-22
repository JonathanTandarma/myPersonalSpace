package com.gp.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "master_warna")
public class MasterWarna {
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	private int id;

	@Column(name="nama_warna")
	private String namaWarna;
	
	@Column(name="kode_warna")
	private String kodeWarna;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNamaWarna() {
		return namaWarna;
	}

	public void setNamaWarna(String namaWarna) {
		this.namaWarna = namaWarna;
	}

	public String getKodeWarna() {
		return kodeWarna;
	}

	public void setKodeWarna(String kodeWarna) {
		this.kodeWarna = kodeWarna;
	}

	
	
}
