package com.gp.repository;

import java.util.List;

import javax.transaction.Transactional;

import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gp.MasterGeneralConstant;
import com.gp.entity.MasterProduct;

@Repository
public interface MasterProductRepo extends JpaRepository<MasterProduct,Long>{

	// Cari Kategori dan Merk dari Nama Barang
	@Query(value="select kategori.nama_kategori,connector.category_id, " + 
			"product.nama_barang, product.id, " + 
			"merk.nama_merk,product.merk_id from master_product product " + 
			"inner join master_merk merk on product.merk_id = merk.id " + 
			"inner join category_detail_product_connector connector on connector.product_id = product.id " + 
			"inner join master_category kategori on connector.category_id = kategori.id " + 
			"where product.nama_barang like concat('%',:namaBarang,'%') " + 
			"group by product.id "
			,nativeQuery = true)
	List<JSONObject> findCategoryAndBrandFromProductName(
			@Param("namaBarang") String namaBarang);
	
	
	// Cari Joran Detail All
	@Query(value="select detail.id as productDetailId, kategori.nama_kategori, " + 
			"product.nama_barang, " + 
			"merk.nama_merk,detail.ukuran,detail.lbs_min,detail.lbs_max,detail.ring, " + 
			"detail.tahun_produksi,warna.nama_warna,tipe.nama_tipe,bahan.nama_bahan, "
			+ "detail.pe_min, detail.pe_max, "
			+ "detail.warna_joran_id, detail.bahan_joran_id, detail.tipe_joran_id "
			+ "from master_product product " + 
			"inner join master_merk merk on product.merk_id = merk.id " + 
			"inner join category_detail_product_connector connector on connector.product_id = product.id " + 
			"inner join master_category kategori on connector.category_id = kategori.id " + 
			"inner join joran_detail detail on detail.connector_category_id = connector.id " + 
			"left join master_tipe tipe on detail.tipe_joran_id = tipe.id " + 
			"left join master_warna warna on detail.warna_joran_id = warna.id " + 
			"left join master_bahan bahan on detail.bahan_joran_id = bahan.id " + 
			"where kategori.id = :kategoriId and product.id = :productId and merk.id = :merkId "
			+ "and detail.status = :status ", 
			nativeQuery = true)
	List<JSONObject> findJoranDetailAll(
			@Param("kategoriId") int categoryId,
			@Param("productId") int productId,
			@Param("merkId") int merkId,
			@Param("status") String status
			);
	
	
	// Cari Joran Detail By Filter
	@Query(value="select kategori.nama_kategori, " + 
			"product.nama_barang, " + 
			"merk.nama_merk,detail.ukuran,detail.lbs_min,detail.lbs_max,detail.ring, " + 
			"detail.tahun_produksi,warna.nama_warna,tipe.nama_tipe,bahan.nama_bahan, "
			+ "detail.pe_min, detail.pe_max "
			+ "from master_product product " + 
			"inner join master_merk merk on product.merk_id = merk.id " + 
			"inner join category_detail_product_connector connector on connector.product_id = product.id " + 
			"inner join master_category kategori on connector.category_id = kategori.id " + 
			"inner join joran_detail detail on detail.connector_category_id = connector.id " + 
			"left join master_tipe tipe on detail.tipe_joran_id = tipe.id " + 
			"left join master_warna warna on detail.warna_joran_id = warna.id " + 
			"left join master_bahan bahan on detail.bahan_joran_id = bahan.id " + 
			"where detail.ukuran like concat('%',:size,'%') " + 
			// "and detail.lbs_min like concat('%',:minLbs,'%') " + 
			"and detail.lbs_max like concat('%',:maxLbs,'%') " + 
			"and detail.ring like concat('%',:ring,'%') " + 
			"and detail.tahun_produksi like concat('%',:productionDate,'%') " + 
			"and warna.nama_warna like concat('%',:color,'%') " + 
			"and bahan.nama_bahan like concat('%',:material,'%') " + 
			"and tipe.nama_tipe like concat('%',:type,'%') "+
			"and tipe.category_id = "+MasterGeneralConstant.categoryJoran +" "+
			"and detail.status = :status "
			,nativeQuery=true)
	List<JSONObject> findJoranDetailByFilter(
			@Param("size") String size,
		//	@Param("minLbs") String minLbs,
			@Param("maxLbs") String maxLbs,
			@Param("ring") String ring,
			@Param("productionDate") String productionDate,
			@Param("color") String color,
			@Param("material") String material,
			@Param("type") String type,
			@Param("status") String status
			);
	
	// Cari Reel Detail
	
	
	// Cari Aksesoris Detail
	
	
	// Cek existing product exist or not
	@Query(value="Select distinct(product.id) from master_product product "
			+ "inner join category_detail_product_connector konektor on "
			+ "konektor.product_id = product.id "
			+ "where product.merk_id = :merkId and product.nama_barang like concat('%',:name,'%') "
			+ "and konektor.category_id = :categoryId "
			,nativeQuery=true)
	List<Integer> checkIfExistNameBrandAndCategoryMasterProduct(
			@Param("name") String productName,
			@Param("merkId") Integer merkId,
			@Param("categoryId") Integer categoryId);
	
	
	@Query(value="Select p.id, p.merk_id,m.nama_merk, p.nama_barang "
			+ "from master_product p inner join master_merk m "
			+ "on p.merk_id = m.id "
			+ "where p.id in :listId ",nativeQuery = true)
	List<JSONObject> findProductByListId(
			@Param("listId") List<Integer> productIdList);
	
	//Get last inserted id 
	@Query(value = "select last_insert_id() ",nativeQuery = true)
	Integer getLastInsertedId();
	
	
	// Update Detail Joran
	  @Modifying  
	  @Transactional
	  @Query(value=" UPDATE joran_detail " + 
	  		"SET " +  
	  		"ukuran = coalesce(:ukuran,ukuran), " + 
	  		"lbs_min = coalesce(:lbsMin,lbs_min), " + 
	  		"lbs_max = coalesce(:lbsMax,lbs_max), " + 
	  		"ring = coalesce(:ring,ring), " + 
	  		"pe_min = coalesce(:peMin,pe_min), " + 
	  		"pe_max = coalesce(:peMax,pe_max), " + 
	  		"tahun_produksi = coalesce(:tahunProduksi,tahun_produksi), " + 
	  		"warna_joran_id = coalesce(:warnaId,warna_joran_id), " + 
	  		"bahan_joran_id = coalesce(:bahanId,bahan_joran_id), " + 
	  		"tipe_joran_id = coalesce(:tipeId,tipe_joran_id) " +  
	  		"WHERE id = :detailId ",nativeQuery = true) void updateJoranDetail(
	  				@Param("ukuran") String size,
	  				@Param("lbsMin") String minLbs, 
	  				@Param("lbsMax") String maxLbs, 
	  				@Param("ring") String ring, 
	  				@Param("peMin") String minPe,
	  				@Param("peMax") String maxPe,
	  				@Param("tahunProduksi") String productionDate,
	  				@Param("warnaId") int warnaId,
	  				@Param("bahanId") int bahanId,
	  				@Param("tipeId") int tipeId,
	  				@Param("detailId") int detailId);
	 
	 // Update Nama Produk dan Merk
	  @Modifying
	  @Transactional
	  @Query(value ="update master_product set "
	  		+ "nama_barang = coalesce(:name,nama_barang), "
	  		+ "merk_id = coalesce(:merkId,merk_id) "
	  		+ "where id = :productId ", nativeQuery = true)
	  void updateProductNameAndBrand(
			  @Param("name") String newProductName,
			  @Param("merkId") Integer merkId,
			  @Param("productId") Integer productId);
	  
	  //Delete product
	  @Modifying
	  @Transactional
	  @Query(value = "update joran_detail "
	  		+ "set status = :status "
	  		+ "where id = :detailId ", nativeQuery = true)
	  void deleteJoranProduct(
			  @Param("detailId") Integer detailId,
			  @Param("status") String status);
	  
	  
}
