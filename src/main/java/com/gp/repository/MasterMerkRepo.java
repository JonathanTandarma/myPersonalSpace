package com.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gp.entity.MasterMerk;

@Repository
public interface MasterMerkRepo extends JpaRepository<MasterMerk,Long>  {

}
