package com.gp.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gp.MasterGeneralConstant;
import com.gp.entity.MasterBahan;
import com.gp.entity.MasterKategori;
import com.gp.entity.MasterMerk;
import com.gp.entity.MasterTipe;
import com.gp.entity.MasterWarna;
import com.gp.repository.MasterBahanRepo;
import com.gp.repository.MasterKategoriRepo;
import com.gp.repository.MasterMerkRepo;
import com.gp.repository.MasterTipeRepo;
import com.gp.repository.MasterWarnaRepo;

@RestController
@RequestMapping({MasterGeneralConstant.baseRestURL})
@CrossOrigin(origins = "*", allowedHeaders = "*")
public class ListController {
	
	private final MasterMerkRepo merkRepo;
	private final MasterTipeRepo tipeRepo;
	private final MasterWarnaRepo warnaRepo;
	private final MasterBahanRepo bahanRepo;
	private final MasterKategoriRepo kategoriRepo;
	
	public ListController(MasterMerkRepo merkRepo, MasterTipeRepo tipeRepo, MasterWarnaRepo warnaRepo,
			MasterBahanRepo bahanRepo, MasterKategoriRepo kategoriRepo) {
		this.merkRepo = merkRepo;
		this.tipeRepo = tipeRepo;
		this.warnaRepo = warnaRepo;
		this.bahanRepo = bahanRepo;
		this.kategoriRepo = kategoriRepo;
	}
	
	
	@RequestMapping(value = "/inquiryListCode", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public JSONObject inquiryAllProduct(
			@Valid @RequestBody JSONObject serviceInput){
		
		JSONObject serviceReturn = new JSONObject();
		List<MasterBahan> kodeBahan = new ArrayList<>();
		List<MasterTipe> kodeTipe = new ArrayList<>();
		List<MasterWarna> kodeWarna = new ArrayList<>();
		List<MasterMerk> kodeMerk = new ArrayList<>();
		
		String kodeBarang = String.valueOf(serviceInput.get("kode"));
		Integer categoryId = 0;
		try {
			switch(kodeBarang) {
			case MasterGeneralConstant.kodeBahan : 
				categoryId = Integer.valueOf(String.valueOf(serviceInput.get("categoryId")));
				kodeBahan = bahanRepo.findAllByCategory(categoryId);
				serviceReturn.put("status","S");
				serviceReturn.put("listCode",kodeBahan);
				break;
			case MasterGeneralConstant.kodeMerk :
				kodeMerk = merkRepo.findAll();
				serviceReturn.put("status","S");
				serviceReturn.put("listCode",kodeMerk);
				break;
			case MasterGeneralConstant.kodeTipe : 
				categoryId = Integer.valueOf(String.valueOf(serviceInput.get("categoryId")));
				kodeTipe = tipeRepo.findAllByCategory(categoryId);
				serviceReturn.put("status","S");
				serviceReturn.put("listCode",kodeTipe);
				break;
			case MasterGeneralConstant.kodeWarna :
				kodeWarna = warnaRepo.findAll();
				serviceReturn.put("status","S");
				serviceReturn.put("listCode",kodeWarna);
				break;
			}
			
			
		}catch(Exception e) {
			serviceReturn.put("status","F");
			serviceReturn.put("errMes",e.getMessage());
		}
		return serviceReturn;
	}
	
	@RequestMapping(value="/insertNewCode",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject insertNewCode(
			@Valid @RequestBody JSONObject serviceInput) {
		
		JSONObject serviceReturn = new JSONObject();
		
		String nama = String.valueOf(serviceInput.get("nama"));
		String kode = String.valueOf(serviceInput.get("kode"));
		String namaCode = String.valueOf(serviceInput.get("tipeKode"));
		Integer categoryId = 0 ;
		try {
			switch(namaCode) {
			case MasterGeneralConstant.kodeBahan : 
				categoryId = Integer.valueOf(String.valueOf(serviceInput.get("categoryId")));
				
				MasterBahan newBahan = new MasterBahan();
				newBahan.setCategoryId(categoryId);
				newBahan.setKodeBahan(kode);
				newBahan.setNamaBahan(nama);
				bahanRepo.save(newBahan);
				break;
			
			case MasterGeneralConstant.kodeTipe :
				categoryId = Integer.valueOf(String.valueOf(serviceInput.get("categoryId")));
				MasterTipe newTipe = new MasterTipe();
				newTipe.setCategory_id(categoryId);
				newTipe.setKodeTipe(kode);
				newTipe.setNamaTipe(nama);
				
				tipeRepo.save(newTipe);
				break;
				
			case MasterGeneralConstant.kodeMerk :
				MasterMerk newMerk = new MasterMerk();
				newMerk.setKodeMerk(kode);
				newMerk.setNamaMerk(nama);
				
				merkRepo.save(newMerk);
				break;
				
			case MasterGeneralConstant.kodeWarna :
				MasterWarna newWarna = new MasterWarna();
				newWarna.setKodeWarna(kode);
				newWarna.setNamaWarna(nama);
				
				warnaRepo.save(newWarna);
				break;
				
			case MasterGeneralConstant.kodeKategori :
				MasterKategori newKategori = new MasterKategori();
				newKategori.setKodeKategori(kode);
				newKategori.setNamaKategori(nama);
				
				kategoriRepo.save(newKategori);
				break;
			}
		}catch(Exception e) {
			serviceReturn.put("status", MasterGeneralConstant.failed);
			serviceReturn.put("errorMessage",e.getMessage());
		}
		
		serviceReturn.put("status", MasterGeneralConstant.success);
		
		return serviceReturn;
	}

	
	@RequestMapping(value = "/updateCode", 
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public JSONObject updateCode(
			@Valid @RequestBody JSONObject serviceInput) {
		
		return null;
	}
	
	public JSONObject deleteCode() {
		
		return null;
	}
	
}
