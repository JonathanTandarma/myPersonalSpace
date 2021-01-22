package com.gp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.gp.entity.JoranDetail;

@Repository
public interface JoranDetailRepo extends JpaRepository<JoranDetail,Long>  {
	
}
