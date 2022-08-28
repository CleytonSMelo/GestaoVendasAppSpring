package com.appspring.appspring.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.appspring.appspring.modelo.Categoria;

@Repository
@Transactional
public interface CategoriaRepository extends JpaRepository<Categoria, Long> {
	
	@Query("select c from Categoria c where c.id = ?1")
	Categoria buscarPorId(Long id);
	
	@Query("select c from Categoria c where c.deletado = 'FALSE' ")
	List<Categoria> ListarCategorias();
}
