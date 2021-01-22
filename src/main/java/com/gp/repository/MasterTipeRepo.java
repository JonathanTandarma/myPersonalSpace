package com.gp.repository;

import java.util.List;

import org.json.simple.JSONObject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.gp.entity.MasterTipe;

@Repository
public interface MasterTipeRepo extends JpaRepository<MasterTipe,Long>  {

	@Query(value=" select * from master_tipe "
			+ "where category_id = :categoryId ",nativeQuery=true)
	List<MasterTipe> findAllByCategory(
			@Param("categoryId") int categoryId);
}
