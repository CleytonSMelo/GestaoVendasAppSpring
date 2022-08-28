package com.appspring.appspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;


import com.appspring.appspring.modelo.Estoque;

@Repository
@Transactional
public interface EstoqueRepository extends JpaRepository<Estoque, Long> {
	
	@Query("select e from Estoque e where e.id = ?1")
	Estoque buscarPorId(Long id);
	
	@Query("select e from Estoque e where e.deletado = 'FALSE'")
	List<Estoque> ListarEstoque();
	
	@Query(value = "select c.nome as categoria, sum(e.quantidade) from estoque e join produto p on p.id = produto_id join categoria c on p.categoria_id = c.id group by c.nome", nativeQuery = true)
	List<Object[]> buscarCategoriaComEstoque();
	
	@Query("select sum(e.quantidade) from Estoque e where e.deletado = 'FALSE'")
	Long QuantidadedeProdutosRegistrado();
}
