package com.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gp.entity.MasterKategori;

@Repository
public interface MasterKategoriRepo extends JpaRepository<MasterKategori,Long>  {

}
