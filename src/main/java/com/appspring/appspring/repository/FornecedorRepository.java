package com.appspring.appspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.appspring.appspring.modelo.Fornecedor;

@Repository
@Transactional
public interface FornecedorRepository extends JpaRepository<Fornecedor, Long> {

	@Query("select f from Fornecedor f where f.id = ?1")
	Fornecedor buscarPorId(Long id);
	
	@Query("select f from Fornecedor f where f.deletado = 'FALSE'")
	List<Fornecedor> ListarFornecedores();
	
	@Query("select f.nome from Fornecedor f join Produto p on p.fornecedor.id = f.id join Estoque e on e.produto.id = p.id where e.quantidade = 0 group by f.nome order by f.nome")
	List<String> ListadeProdutosSemEstoque();
	
	@Query("select count(f) from Fornecedor f where f.deletado = 'FALSE'")
	Long TotalFornecedoresRegistrado();
}
