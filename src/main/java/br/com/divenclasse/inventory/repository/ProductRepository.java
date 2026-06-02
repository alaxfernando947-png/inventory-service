package br.com.divenclasse.inventory.repository;

import br.com.divenclasse.inventory.entity.Product;
import br.com.divenclasse.inventory.entity.enums.Category;
import br.com.divenclasse.inventory.entity.enums.Gender;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNomeContainingIgnoreCase(String nome, Pageable pageable);
    Page<Product> findByCategoria(Category categoria, Pageable pageable);
    Page<Product> findByTamanhoIgnoreCase(String tamanho, Pageable pageable);
    Page<Product> findByGenero(Gender genero, Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.quantidadeEstoque <= :minimo ORDER BY p.quantidadeEstoque ASC")
    Page<Product> findLowStock(@Param("minimo") int minimo, Pageable pageable);
}
