package com.appspring.appspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.appspring.appspring.modelo.Vendas;

@Repository
@Transactional
public interface VendasRepository extends JpaRepository<Vendas, Long> {
	
	@Query("select v from Vendas v where v.id = ?1")
	Vendas buscarPorId(Long id);
	
	@Query("select v from Vendas v where v.deletado = 'FALSE'")
	List<Vendas> ListarVendas();
	
	@Query("select count(v) from Vendas v ")
	Long totalVendas();
	
	@Query("select count(v) from Vendas v where v.deletado = 'FALSE'")
	Long TotalVendasRegistradas();
}
