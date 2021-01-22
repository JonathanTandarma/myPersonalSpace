package com.gp.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.validation.Valid;

import org.json.simple.JSONObject;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.gp.MasterGeneralConstant;
import com.gp.DTO.CheckProductDTO;
import com.gp.DTO.InquiryAksesorisDetailDTO;
import com.gp.DTO.InquiryJoranDetailDTO;
import com.gp.DTO.InquiryKailDetailDTO;
import com.gp.DTO.InquiryLineDetailDTO;
import com.gp.DTO.InquiryProductBrandAndCategoryDTO;
import com.gp.DTO.InquiryReelDetailDTO;
import com.gp.DTO.InsertNewProductDTO;
import com.gp.DTO.UpdateProductDTO;
import com.gp.entity.AksesorisDetail;
import com.gp.entity.JoranDetail;
import com.gp.entity.KailDetail;
import com.gp.entity.LineDetail;
import com.gp.entity.MasterProduct;
import com.gp.entity.ProductKategoriKonektor;
import com.gp.entity.ReelDetail;
import com.gp.entity.UmpanDetail;
import com.gp.repository.AksesorisDetailRepo;
import com.gp.repository.JoranDetailRepo;
import com.gp.repository.KailDetailRepo;
import com.gp.repository.LineDetailRepo;
import com.gp.repository.MasterProductRepo;
import com.gp.repository.ProductKategoriKonektorRepo;
import com.gp.repository.ProductRepo;
import com.gp.repository.ReelDetailRepo;
import com.gp.repository.UmpanDetailRepo;

@RestController
@RequestMapping({ MasterGeneralConstant.baseRestURL })
@CrossOrigin(origins = "*", allowedHeaders = "*")

public class ProductController {
	private final ProductRepo repository;

	private final MasterProductRepo productRepository;

	private final ProductKategoriKonektorRepo productKonektorRepository;

	private final JoranDetailRepo joranDetailRepository;

	private final ReelDetailRepo reelDetailRepository;

	private final LineDetailRepo lineDetailRepository;

	private final KailDetailRepo kailDetailRepository;
	
	private final AksesorisDetailRepo aksesorisDetailRepository;
	
	private final UmpanDetailRepo umpanDetailRepository;

	public ProductController(ProductRepo repository, MasterProductRepo productRepository,
			ProductKategoriKonektorRepo productKonektorRepository, JoranDetailRepo joranDetailRepository,
			ReelDetailRepo reelDetailRepository, LineDetailRepo lineDetailRepository,
			KailDetailRepo baitDetailRepository, AksesorisDetailRepo aksesorisDetailRepository,
			UmpanDetailRepo umpanDetailRepository ) {
		this.productRepository = productRepository;
		this.repository = repository;
		this.productKonektorRepository = productKonektorRepository;
		this.joranDetailRepository = joranDetailRepository;
		this.reelDetailRepository = reelDetailRepository;
		this.lineDetailRepository = lineDetailRepository;
		this.kailDetailRepository = baitDetailRepository;
		this.aksesorisDetailRepository = aksesorisDetailRepository;
		this.umpanDetailRepository = umpanDetailRepository;
	}

	@RequestMapping(value = "/inquiryCategoryAndBrandByProductName", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject inquiryAllProduct(@Valid @RequestBody InquiryProductBrandAndCategoryDTO serviceInput) {

		JSONObject serviceReturn = new JSONObject();

		try {
			System.out.println("serviceInput : " + serviceInput.getNamaBarang());
			String namaBarang = serviceInput.getNamaBarang();

			System.out.println("Nama Barang : " + namaBarang);

			List<JSONObject> productList = productRepository.findCategoryAndBrandFromProductName(namaBarang);
			serviceReturn.put("status", "S");
			serviceReturn.put("listProduct", productList);
		} catch (Exception e) {
			serviceReturn.put("status", "F");
			serviceReturn.put("errMes", e.getMessage());
		}
		return serviceReturn;
	}

	@RequestMapping(value = "/inquiryProductDetailAllByCategoryAndBrand", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject inquiryDetailProductAll(@Valid @RequestBody InquiryProductBrandAndCategoryDTO serviceInput) {
		JSONObject serviceReturn = new JSONObject();
		JSONObject productDetail = new JSONObject();
		try {
			int categoryId = serviceInput.getKategoriId();
			int productId = serviceInput.getProductId();
			int merkId = serviceInput.getMerkId();

			switch (categoryId) {
			case 1:
				productDetail = inquiryJoranDetailAll(categoryId, productId, merkId);
				break;
			case 2:
				productDetail = inquiryReelDetailAll(categoryId, productId, merkId);
				break;
			case 3:
				productDetail = inquiryLineDetailAll(categoryId, productId, merkId);
				break;
			case 4:
				productDetail = inquiryKailDetailAll(categoryId, productId, merkId);
				break;
			case 5:
				productDetail = inquiryAksesorisDetailAll(categoryId, productId, merkId);
				break;
			case 6:
				productDetail = inquiryUmpanDetailAll(categoryId, productId, merkId);
				break;
			}

			serviceReturn.put("status", "S");
			serviceReturn.put("listProduct", productDetail);
			serviceReturn.put("categoryId", categoryId);

		} catch (Exception e) {
			serviceReturn.put("status", "F");
			serviceReturn.put("errMes", e.getCause());
		}

		return serviceReturn;
	};

	@RequestMapping(value = "/inquiryJoranDetailByFilter", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject inquiryJoranDetailByFilter(@Valid @RequestBody InquiryJoranDetailDTO serviceInput) {
		JSONObject serviceReturn = new JSONObject();
		List<JSONObject> listJoranDetail = new ArrayList<>();

		try {
			String size = serviceInput.getSize();
			// String minLbs = serviceInput.getMinLbs();
			String maxLbs = serviceInput.getMaxLbs();
			String ring = serviceInput.getRing();
			String productionDate = serviceInput.getProductionDate();
			String color = serviceInput.getNamaWarna();
			String material = serviceInput.getNamaBahan();
			String type = serviceInput.getNamaTipe();

			listJoranDetail = productRepository.findJoranDetailByFilter(size, maxLbs, ring, productionDate, color,
					material, type, MasterGeneralConstant.active);

			serviceReturn.put("status", "S");
			serviceReturn.put("listProduct", listJoranDetail);

		} catch (Exception e) {
			serviceReturn.put("status", "F");
			serviceReturn.put("errMes", e.getCause());
		}
		return serviceReturn;
	}
	
	@RequestMapping(value = "/inquiryReelDetailByFilter", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject inquiryReelDetailByFilter(@Valid @RequestBody InquiryReelDetailDTO serviceInput) {
		JSONObject serviceReturn = new JSONObject();
		List<JSONObject> listReelDetail = new ArrayList<>();

		try {
			ReelDetail reelDetailData = serviceInput.getReelDetail();

			listReelDetail = reelDetailRepository.findReelDetailByFilter(reelDetailData.getUkuran(), 
					reelDetailData.getBallBearing(), reelDetailData.getTipePh(), reelDetailData.getTipeWay(), 
					serviceInput.getTipeReel(), serviceInput.getWarna(), MasterGeneralConstant.active);
			
			serviceReturn.put("status", "S");
			serviceReturn.put("listProduct", listReelDetail);

		} catch (Exception e) {
			serviceReturn.put("status", "F");
			serviceReturn.put("errMes", e.getCause());
		}
		return serviceReturn;
	}
	
	@RequestMapping(value = "/inquiryLineDetailByFilter", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject inquiryLineDetailByFilter(@Valid @RequestBody InquiryLineDetailDTO serviceInput) {
		JSONObject serviceReturn = new JSONObject();
		List<JSONObject> listLineDetail = new ArrayList<>();

		try {
			LineDetail lineDetailData = serviceInput.getLineDetail();

			System.out.println(lineDetailData.getTipeNo());
			
			listLineDetail = lineDetailRepository.findLineDetailByFilter(lineDetailData.getUkuranPanjang(), lineDetailData.getSatuanUkur(), 
					lineDetailData.getKekuatan(), lineDetailData.getPe(), lineDetailData.getLbs(), 
					lineDetailData.getDiameter(), lineDetailData.getTipeKonektorSenar(), 
					lineDetailData.getTipeFluorCarbon(), lineDetailData.getTipeNo(), 
					serviceInput.getWarna(), serviceInput.getTipeLine(), MasterGeneralConstant.active);
			
			
			serviceReturn.put("status", "S");
			serviceReturn.put("listProduct", listLineDetail);

		} catch (Exception e) {
			serviceReturn.put("status", "F");
			serviceReturn.put("errMes", e.getCause());
		}
		return serviceReturn;
	}
	
	@RequestMapping(value = "/inquiryKailDetailByFilter", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject inquiryKailDetailByFilter(@Valid @RequestBody InquiryKailDetailDTO serviceInput) {
		JSONObject serviceReturn = new JSONObject();
		List<JSONObject> listKailDetail = new ArrayList<>();

		try {
			KailDetail kailDetailData = serviceInput.getKailDetail();

			listKailDetail = kailDetailRepository.findKailDetailByFilter(kailDetailData.getUkuran(), MasterGeneralConstant.active);
			
			serviceReturn.put("status", "S");
			serviceReturn.put("listProduct", listKailDetail);

		} catch (Exception e) {
			serviceReturn.put("status", "F");
			serviceReturn.put("errMes", e.getCause());
		}
		return serviceReturn;
	}
	
	@RequestMapping(value = "/inquiryAksesorisDetailByFilter", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject inquiryAksesorisDetailByFilter(
			@Valid @RequestBody InquiryAksesorisDetailDTO serviceInput) {
		JSONObject serviceReturn = new JSONObject(); 
		List<JSONObject> listAksesorisDetail = new ArrayList<>();	

		try {
			AksesorisDetail aksesorisDetailData = serviceInput.getAksesorisDetail();

			listAksesorisDetail = aksesorisDetailRepository.findAksesorisDetailByFilter(aksesorisDetailData.getUkuran(),
					serviceInput.getWarna(), MasterGeneralConstant.active);
			
			serviceReturn.put("status", "S");
			serviceReturn.put("listProduct", listAksesorisDetail);

		} catch (Exception e) {
			serviceReturn.put("status", "F");
			serviceReturn.put("errMes", e.getCause());
		}
		return serviceReturn;
	}
	

	@RequestMapping(value = "/checkIfExistProductNameBrandCategory", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject checkIfExistProductNameBrandCategory(
			@Valid @RequestBody CheckProductDTO serviceInput) {
		JSONObject serviceReturn = new JSONObject();

		try {
		String productName = serviceInput.getProductName();
		Integer merkId = serviceInput.getMerkId();
		Integer categoryId = serviceInput.getCategoryId();

		List<Integer> checkProductExist = productRepository.checkIfExistNameBrandAndCategoryMasterProduct(productName,
				merkId, categoryId);

		//List<MasterProduct> productList = new ArrayList<>();
		List<JSONObject> productList = new ArrayList<>();
		System.out.println("Size List : " + checkProductExist.size());

		if (checkProductExist.size() == 0) {
			// New Product
			serviceReturn.put("productList", productList);
		} else {
			// Existing product
			productList = productRepository.findProductByListId(checkProductExist);
			
			serviceReturn.put("productList", productList);
		}
		serviceReturn.put("status", MasterGeneralConstant.success);

		}catch(Exception E) {
			serviceReturn.put("status", MasterGeneralConstant.failed);
			serviceReturn.put("errMessage", E.getMessage());
		}
		return serviceReturn;
	}

	@RequestMapping(value = "/insertNewProduct", 
			method = RequestMethod.POST, 
			consumes = MediaType.APPLICATION_JSON_VALUE, 
			produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject insertNewProduct(@Valid @RequestBody InsertNewProductDTO serviceInput) {

		JSONObject serviceReturn = new JSONObject();
		try {
			int newProductId = 0;
			int newKategoriKonektorId = 0;

			// CheckProductId
			if (serviceInput.getProductId() == 0) {
				// new product
				MasterProduct newProduct = new MasterProduct();
				newProduct.setMerkId(serviceInput.getMerkId());
				newProduct.setNamaBarang(serviceInput.getProductName());

				productRepository.save(newProduct);

				newProductId = productRepository.getLastInsertedId();
			} else {
				newProductId = serviceInput.getProductId();
			}
			// Insert into konektor

			// generate barcode
			String barcode = generateBarcode(newProductId, serviceInput.getKategoriId(), serviceInput.getMerkId());

			ProductKategoriKonektor productConnector = new ProductKategoriKonektor();
			productConnector.setCategoryId(serviceInput.getKategoriId());
			productConnector.setProductId(newProductId);
			productConnector.setBarcode(barcode);

			productKonektorRepository.save(productConnector);

			newKategoriKonektorId = productRepository.getLastInsertedId();

			JSONObject hasilInsert = new JSONObject();

			System.out.println("Kategori konektor ID : " + newKategoriKonektorId);

			switch (serviceInput.getKategoriId()) {
			case 1:
				JoranDetail newJoranDetail = serviceInput.getJoranDetail();
				hasilInsert = insertJoranProduct(newJoranDetail, newKategoriKonektorId) ;
				break;
			case 2:
				ReelDetail newReelDetail = serviceInput.getReelDetail();
				hasilInsert = insertReelProduct(newReelDetail, newKategoriKonektorId);
				break;
			case 3:
				LineDetail newLineDetail = serviceInput.getLineDetail();
				hasilInsert = insertLineProduct(newLineDetail, newKategoriKonektorId);
				break;
			case 4:
				KailDetail newKailDetail = serviceInput.getKailDetail();
				hasilInsert = insertKailProduct(newKailDetail, newKategoriKonektorId);
				break;
			case 5:
				AksesorisDetail newAksesorisDetail = serviceInput.getAksesorisDetail();
				hasilInsert = insertAksesorisProduct(newAksesorisDetail, newKategoriKonektorId);
				break;
			}

			String statusInsert = String.valueOf(hasilInsert.get("status"));

			if (statusInsert.equals(MasterGeneralConstant.success)) {
				serviceReturn.put("status", MasterGeneralConstant.success);

			} else {
				serviceReturn.put("status", MasterGeneralConstant.failed);
				serviceReturn.put("errorMessage", hasilInsert.get("errorMessage"));
			}

		} catch (Exception e) {
			serviceReturn.put("errorMessage", e.getMessage());
			serviceReturn.put("status", MasterGeneralConstant.failed);
		}

		return serviceReturn;
	}

	public JSONObject inquiryJoranDetailAll(int categoryId, int productId, int merkId) {
		JSONObject joranDetailAll = new JSONObject();
		List<JSONObject> listJoranDetailAll = new ArrayList<JSONObject>();

		listJoranDetailAll = productRepository.findJoranDetailAll(categoryId, productId, merkId,
				MasterGeneralConstant.active);

		joranDetailAll.put("listJoranDetail", listJoranDetailAll);

		return joranDetailAll;
	};

	public JSONObject inquiryReelDetailAll(int categoryId, int productId, int merkId) {
		JSONObject reelDetailAll = new JSONObject();
		List<JSONObject> listReelDetailAll = new ArrayList<>();

		listReelDetailAll = reelDetailRepository.findReelDetailAll(categoryId, productId, merkId,
				MasterGeneralConstant.active);

		reelDetailAll.put("listReelDetail", listReelDetailAll);

		return reelDetailAll;
	}
	
	public JSONObject inquiryLineDetailAll(int categoryId, int productId, int merkId) {
		JSONObject lineDetailAll = new JSONObject();
		
		List<JSONObject> listLineDetailAll = new ArrayList<>();

		listLineDetailAll = lineDetailRepository.findLineDetailAll(categoryId, productId, merkId,
				MasterGeneralConstant.active);

		lineDetailAll.put("listLineDetail", listLineDetailAll);

		return lineDetailAll;
	}
	
	public JSONObject inquiryKailDetailAll(int categoryId, int productId, int merkId) {
		JSONObject kailDetailAll = new JSONObject();
		List<JSONObject> listKailDetailAll = new ArrayList<>();

		listKailDetailAll = kailDetailRepository.findKailDetailAll(categoryId, productId, merkId,
				MasterGeneralConstant.active);

		kailDetailAll.put("listKailDetail", listKailDetailAll);

		return kailDetailAll;
	}
	
	public JSONObject inquiryAksesorisDetailAll(int categoryId, int productId, int merkId) {
		JSONObject aksesorisDetailAll = new JSONObject();
		List<JSONObject> listAksesorisDetailAll = new ArrayList<>();

		listAksesorisDetailAll = aksesorisDetailRepository.findAksesorisDetailAll(categoryId, productId, merkId, MasterGeneralConstant.active);

		aksesorisDetailAll.put("listAksesorisDetail", listAksesorisDetailAll);

		return aksesorisDetailAll;
	}
	
	public JSONObject inquiryUmpanDetailAll(int categoryId, int productId, int merkId) {
		JSONObject umpanDetailAll = new JSONObject();
		List<JSONObject> listUmpanDetailAll = new ArrayList<>();

		listUmpanDetailAll = umpanDetailRepository.findUmpanDetailAll(categoryId, productId, merkId, MasterGeneralConstant.active);

		umpanDetailAll.put("listUmpanDetail", listUmpanDetailAll);

		return umpanDetailAll;
	}
	

	@RequestMapping(value = "/updateProduct", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject updateProduct(@Valid @RequestBody UpdateProductDTO serviceInput) {

		JSONObject serviceOutput = new JSONObject();
		String name = "";
		Integer merkId = 0;
		Integer productId = 0;
		try {
			switch (serviceInput.getKategoriId()) {
			// Joran
			case 1:
				JoranDetail joranDetailData = serviceInput.getJoranDetail();

				String size = joranDetailData.getUkuran().equals("") ? null : joranDetailData.getUkuran();
				String minLbs = joranDetailData.getMinLbs().equals("") ? null : joranDetailData.getMinLbs();
				String maxLbs = joranDetailData.getMaxLbs().equals("") ? null : joranDetailData.getMaxLbs();
				String ring = joranDetailData.getRing().equals("") ? null : joranDetailData.getRing();
				String minPe = joranDetailData.getMinPE().equals("") ? null : joranDetailData.getMinPE();
				String maxPe = joranDetailData.getMaxPE().equals("") ? null : joranDetailData.getMaxPE();
				String productionDate = joranDetailData.getTahunProduksi().equals("") ? null
						: joranDetailData.getTahunProduksi();
				Integer warnaId = joranDetailData.getWarnaJoranId() == 0 ? null : joranDetailData.getWarnaJoranId();
				Integer bahanId = joranDetailData.getBahanJoranId() == 0 ? null : joranDetailData.getBahanJoranId();
				Integer tipeId = joranDetailData.getTipeJoranId() == 0 ? null : joranDetailData.getTipeJoranId();
				Integer detailId = joranDetailData.getId();

				name = serviceInput.getProductName().equals("") ? null : serviceInput.getProductName();
				merkId = serviceInput.getMerkId();
				productId = serviceInput.getProductId();

				System.out.println(name + merkId + productId);

				// Update product name and brand
				productRepository.updateProductNameAndBrand(name, merkId, productId);

				// Update product Detail
				productRepository.updateJoranDetail(size, minLbs, maxLbs, ring, minPe, maxPe, productionDate, warnaId,
						bahanId, tipeId, detailId);
				break;
			case 2:
				ReelDetail reelDetailData = serviceInput.getReelDetail();
				
				String ballBearing = reelDetailData.getBallBearing().equals("") ? null : reelDetailData.getBallBearing();
				String tipePh = reelDetailData.getTipePh().equals("") ? null : reelDetailData.getTipePh();
				Integer tipeReelId = reelDetailData.getTipeReelId() == 0 ? null : reelDetailData.getTipeReelId();
				String tipeWay = reelDetailData.getTipeWay().equals("") ? null : reelDetailData.getTipeWay();
				String ukuran = reelDetailData.getUkuran().equals("") ? null : reelDetailData.getUkuran();
				Integer warnaReelId = reelDetailData.getWarnaReelId() == 0 ? null : reelDetailData.getWarnaReelId();
				Integer reelDetailId = reelDetailData.getId();
				
				name = serviceInput.getProductName().equals("") ? null : serviceInput.getProductName();
				merkId = serviceInput.getMerkId();
				productId = serviceInput.getProductId();
				
				// Update product name and brand
				productRepository.updateProductNameAndBrand(name, merkId, productId);
				
				// Update Product Detail
				reelDetailRepository.updateReelDetail(ukuran, ballBearing, tipePh, tipeWay, warnaReelId, tipeReelId, reelDetailId);
				break;
			case 3:
				LineDetail lineDetailData = serviceInput.getLineDetail();
				
				String diameter = lineDetailData.getDiameter().equals("") ? null : lineDetailData.getDiameter();
				String kekuatan = lineDetailData.getKekuatan().equals("") ? null : lineDetailData.getKekuatan();
				String lbs = lineDetailData.getLbs().equals("") ? null : lineDetailData.getLbs();
				String pe = lineDetailData.getPe().equals("") ? null : lineDetailData.getPe();
				String satuanUkur = lineDetailData.getSatuanUkur().equals("") ? null : lineDetailData.getSatuanUkur();
				String tipeFluorCarbon = lineDetailData.getTipeFluorCarbon().equals("") ? null : lineDetailData.getTipeFluorCarbon();
				String tipeKonektorSenar = lineDetailData.getTipeKonektorSenar().equals("") ? null : lineDetailData.getTipeKonektorSenar();
				
				Integer tipeLineId = lineDetailData.getTipeLineId() == 0 ? null : lineDetailData.getTipeLineId();
				String tipeNo = lineDetailData.getTipeNo().equals("") ? null : lineDetailData.getTipeNo();
				String ukuranPanjang = lineDetailData.getUkuranPanjang().equals("") ? null : lineDetailData.getUkuranPanjang();
				Integer warnaLineId = lineDetailData.getWarnaLineId() == 0 ? null : lineDetailData.getWarnaLineId();
				
				Integer lineDetailId = lineDetailData.getId();
				
				name = serviceInput.getProductName().equals("") ? null : serviceInput.getProductName();
				merkId = serviceInput.getMerkId();
				productId = serviceInput.getProductId();
				
				// Update product name and brand
				productRepository.updateProductNameAndBrand(name, merkId, productId);
				
				// Update Product Detail
				lineDetailRepository.updateLineDetail(ukuranPanjang, satuanUkur, kekuatan, pe, lbs, 
						diameter, tipeKonektorSenar, tipeFluorCarbon, tipeNo, warnaLineId, tipeLineId, lineDetailId);
				break;
			case 4:
				KailDetail kailDetailData = serviceInput.getKailDetail();
				String ukuranKail = kailDetailData.getUkuran().equals("") ? null : kailDetailData.getUkuran();
				
				Integer kailDetailId = kailDetailData.getId();
				
				name = serviceInput.getProductName().equals("") ? null : serviceInput.getProductName();
				merkId = serviceInput.getMerkId();
				productId = serviceInput.getProductId();
				
				// update product name and brand
				productRepository.updateProductNameAndBrand(name, merkId, productId);
				
				// Update product Detail
				kailDetailRepository.updateKailDetail(ukuranKail, kailDetailId);
				break;
			case 5: 
				AksesorisDetail aksesorisDetailData = serviceInput.getAksesorisDetail();
				
				String ukuranAksesoris = aksesorisDetailData.getUkuran().equals("") ? null : aksesorisDetailData.getUkuran();
				Integer warnaAksesorisId = aksesorisDetailData.getWarnaAksesorisId() == 0 ? null : aksesorisDetailData.getWarnaAksesorisId(); 
				
				Integer aksesorisDetailId = aksesorisDetailData.getId();
				
				name = serviceInput.getProductName().equals("") ? null : serviceInput.getProductName();
				merkId = serviceInput.getMerkId();
				productId = serviceInput.getProductId();
				
				// update product name and brand
				productRepository.updateProductNameAndBrand(name, merkId, productId);

				// Update product Detail
				aksesorisDetailRepository.updateAksesorisDetail(ukuranAksesoris, warnaAksesorisId, aksesorisDetailId);
				
				break;
			case 6: 
				UmpanDetail umpanDetailData = serviceInput.getUmpanDetail();
				
				String ukuranUmpan = umpanDetailData.getUkuran().equals("") ? null : umpanDetailData.getUkuran();
				String beratUmpan = umpanDetailData.getBerat().equals("") ? null : umpanDetailData.getBerat();
				String panjangUmpan = umpanDetailData.getPanjang().equals("") ? null : umpanDetailData.getPanjang();
				
				Integer umpanDetailId = umpanDetailData.getId();
				
				Integer warnaUmpanId = umpanDetailData.getWarnaUmpanId();
				
				name = serviceInput.getProductName().equals("") ? null : serviceInput.getProductName();
				merkId = serviceInput.getMerkId();
				productId = serviceInput.getProductId();
				
				// update product name and brand
				productRepository.updateProductNameAndBrand(name, merkId, productId);
				
				// Update product Detail
				umpanDetailRepository.updateUmpanDetail(ukuranUmpan, beratUmpan, panjangUmpan, warnaUmpanId, umpanDetailId);
				
				break;
			}

			serviceOutput.put("status", MasterGeneralConstant.success);
		} catch (Exception e) {
			serviceOutput.put("status", MasterGeneralConstant.failed);
			serviceOutput.put("errorMessage", e.getMessage());
			e.printStackTrace();
		}

		return serviceOutput;
	}

	@RequestMapping(value = "/deleteProduct", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public JSONObject deleteProduct(UpdateProductDTO serviceInput) {
		JSONObject serviceOutput = new JSONObject();

		try {
			
			switch(serviceInput.getKategoriId()) {
			case 1 : 
				JoranDetail joranDetailData = serviceInput.getJoranDetail();
				productRepository.deleteJoranProduct(joranDetailData.getId(), MasterGeneralConstant.inactive);	
				break;
			case 2 : 
				ReelDetail reelDetailData = serviceInput.getReelDetail();
				reelDetailRepository.deleteReelProduct(reelDetailData.getId(), MasterGeneralConstant.inactive);
				break;
			case 3 : 
				LineDetail lineDetailData = serviceInput.getLineDetail();
				lineDetailRepository.deleteLineProduct(lineDetailData.getId(), MasterGeneralConstant.inactive);
				break;
			case 4 : 
				KailDetail kailDetailData = serviceInput.getKailDetail();
				kailDetailRepository.deleteKailProduct(kailDetailData.getId(), MasterGeneralConstant.inactive);
				break;
			case 5 : 
				AksesorisDetail aksesorisDetailData = serviceInput.getAksesorisDetail();
				aksesorisDetailRepository.deleteAksesorisProduct(aksesorisDetailData.getId(), MasterGeneralConstant.inactive);
				break;
			case 6 : 
				UmpanDetail umpanDetailData = serviceInput.getUmpanDetail();
				umpanDetailRepository.deleteUmpanProduct(umpanDetailData.getId(), MasterGeneralConstant.inactive);
				break;
			}
			
		} catch (Exception E) {
			serviceOutput.put("status", MasterGeneralConstant.failed);
			serviceOutput.put("errorMessage", E.getMessage());
		}

		return serviceOutput;
	}

	// insert joran
	public JSONObject insertJoranProduct(JoranDetail serviceInput, int newKategoriKonektorId) {
		JSONObject serviceReturn = new JSONObject();

		try {
			// insert joran detail
			JoranDetail newJoranDetail = new JoranDetail();
			newJoranDetail.setMaxLbs(serviceInput.getMaxLbs());
			newJoranDetail.setMinLbs(serviceInput.getMinLbs());
			newJoranDetail.setRing(serviceInput.getRing());
			newJoranDetail.setTahunProduksi(serviceInput.getTahunProduksi());
			newJoranDetail.setUkuran(serviceInput.getUkuran());
			newJoranDetail.setMaxPE(serviceInput.getMaxPE());
			newJoranDetail.setMinPE(serviceInput.getMinPE());

			newJoranDetail.setBahanJoranId(serviceInput.getBahanJoranId());
			newJoranDetail.setTipeJoranId(serviceInput.getTipeJoranId());
			newJoranDetail.setWarnaJoranId(serviceInput.getWarnaJoranId());
			newJoranDetail.setConnectorCategoryId(newKategoriKonektorId);
			newJoranDetail.setStatus(MasterGeneralConstant.active);

			joranDetailRepository.save(newJoranDetail);
		} catch (Exception E) {
			serviceReturn.put("status", MasterGeneralConstant.failed);
			serviceReturn.put("errorMessage", E.getMessage());
		}
		serviceReturn.put("status", MasterGeneralConstant.success);

		return serviceReturn;
	}

	// insert reel

	public JSONObject insertReelProduct(ReelDetail serviceInput, int newKategoriKonektorId) {
		JSONObject serviceReturn = new JSONObject();

		try {
			// insert joran detail
			ReelDetail newReelDetail = new ReelDetail();

			newReelDetail.setBallBearing(serviceInput.getBallBearing());
			newReelDetail.setUkuran(serviceInput.getUkuran());
			newReelDetail.setTipePh(serviceInput.getTipePh());
			newReelDetail.setTipeWay(serviceInput.getTipeWay());
			
			newReelDetail.setTipeReelId(serviceInput.getTipeReelId());
			newReelDetail.setWarnaReelId(serviceInput.getWarnaReelId());
			newReelDetail.setConnectorCategoryId(newKategoriKonektorId);
			newReelDetail.setStatus(MasterGeneralConstant.active);

			reelDetailRepository.save(newReelDetail);
		} catch (Exception E) {
			serviceReturn.put("status", MasterGeneralConstant.failed);
			serviceReturn.put("errorMessage", E.getMessage());
		}
		serviceReturn.put("status", MasterGeneralConstant.success);

		return serviceReturn;
	}

	// insert senar/line
	public JSONObject insertLineProduct(LineDetail serviceInput, int newKategoriKonektorId) {
		JSONObject serviceReturn = new JSONObject();

		try {
			// insert joran detail
			LineDetail newLineDetail = new LineDetail();

			newLineDetail.setUkuranPanjang(serviceInput.getUkuranPanjang());
			newLineDetail.setSatuanUkur(serviceInput.getSatuanUkur());
			newLineDetail.setDiameter(serviceInput.getDiameter());
			newLineDetail.setKekuatan(serviceInput.getKekuatan());
			newLineDetail.setLbs(serviceInput.getLbs());
			newLineDetail.setPe(serviceInput.getPe());
			newLineDetail.setTipeFluorCarbon(serviceInput.getTipeFluorCarbon());
			newLineDetail.setTipeKonektorSenar(serviceInput.getTipeKonektorSenar());
			newLineDetail.setTipeNo(serviceInput.getTipeNo());
			
			newLineDetail.setTipeLineId(serviceInput.getTipeLineId());
			newLineDetail.setWarnaLineId(serviceInput.getWarnaLineId());
			newLineDetail.setConnectorCategoryId(newKategoriKonektorId);
			newLineDetail.setStatus(MasterGeneralConstant.active);

			lineDetailRepository.save(newLineDetail);
		} catch (Exception E) {
			serviceReturn.put("status", MasterGeneralConstant.failed);
			serviceReturn.put("errorMessage", E.getMessage());
		}
		serviceReturn.put("status", MasterGeneralConstant.success);

		return serviceReturn;
	}

	// insert aksesoris
	public JSONObject insertAksesorisProduct(AksesorisDetail serviceInput, int newKategoriKonektorId) {
		JSONObject serviceReturn = new JSONObject();

		try {
			// insert Aksesoris detail
			AksesorisDetail newAksesorisDetail = new AksesorisDetail();

			newAksesorisDetail.setUkuran(serviceInput.getUkuran());
			newAksesorisDetail.setWarnaAksesorisId(serviceInput.getWarnaAksesorisId());

			newAksesorisDetail.setConnectorCategoryId(newKategoriKonektorId);
			newAksesorisDetail.setStatus(MasterGeneralConstant.active);

			aksesorisDetailRepository.save(newAksesorisDetail);
			
		} catch (Exception E) {
			serviceReturn.put("status", MasterGeneralConstant.failed);
			serviceReturn.put("errorMessage", E.getMessage());
		}
		serviceReturn.put("status", MasterGeneralConstant.success);

		return serviceReturn;
	}

	// insert kail
	public JSONObject insertKailProduct(KailDetail serviceInput, int newKategoriKonektorId) {
		JSONObject serviceReturn = new JSONObject();

		try {
			// insert Bait detail
			KailDetail newBaitDetail = new KailDetail();

			newBaitDetail.setUkuran(serviceInput.getUkuran());

			newBaitDetail.setConnectorCategoryId(newKategoriKonektorId);
			newBaitDetail.setStatus(MasterGeneralConstant.active);

			kailDetailRepository.save(newBaitDetail);
			
		} catch (Exception E) {
			serviceReturn.put("status", MasterGeneralConstant.failed);
			serviceReturn.put("errorMessage", E.getMessage());
		}
		serviceReturn.put("status", MasterGeneralConstant.success);

		return serviceReturn;
	}
	
	
	public String generateBarcode(int productId, int kategoriId, int merkId) {
		Random random = new Random();

		int randomNumber = random.nextInt(10000);

		// CategoryId-MerkId-RandomNumber-ProductId
		String productIdStr = zeroPad(String.valueOf(productId), 6);
		String kategoriIdStr = zeroPad(String.valueOf(kategoriId), 1);
		String merkIdStr = zeroPad(String.valueOf(merkId), 3);

		String randomNumberStr = zeroPad(String.valueOf(randomNumber),4);
		
		String barcode = kategoriIdStr + '-' + merkIdStr + '-' + randomNumberStr + '-' + productIdStr;

		return barcode;
	}

	/*
	 * Untuk nambah angka 0 di depan account number
	 */
	public String zeroPad(String input, int expectedLength) {
		int diff = expectedLength - input.length();
		for (int i = 0; i < diff; i++) {
			input = ("0").concat(input);
		}

		return input;
	}
	
	
}

