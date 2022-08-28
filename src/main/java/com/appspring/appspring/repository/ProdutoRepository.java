package com.appspring.appspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.appspring.appspring.modelo.Produto;

@Repository
@Transactional
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	@Query("select p from Produto p where p.id = ?1")
	Produto buscarPorId(Long id);
	
	@Query("select p from Produto p where p.deletado = 'FALSE'")
	List<Produto> ListarProdutos();
	
	@Query("select p.nome from Produto p join Estoque e on p.id = e.produto.id where e.quantidade = 0 group by p.nome order by p.nome")
	List<String> ListadeProdutosSemEstoque();
}
