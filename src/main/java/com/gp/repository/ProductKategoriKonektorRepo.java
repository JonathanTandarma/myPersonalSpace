package com.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gp.entity.ProductKategoriKonektor;

@Repository
public interface ProductKategoriKonektorRepo extends JpaRepository<ProductKategoriKonektor,Long>  {

}
