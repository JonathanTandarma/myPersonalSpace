package com.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gp.entity.MasterWarna;

@Repository
public interface MasterWarnaRepo extends JpaRepository<MasterWarna,Long>  {

}
