package br.com.divenclasse.inventory.service;

import br.com.divenclasse.inventory.dto.*;
import br.com.divenclasse.inventory.entity.Product;
import br.com.divenclasse.inventory.entity.enums.Category;
import br.com.divenclasse.inventory.entity.enums.Gender;
import br.com.divenclasse.inventory.exception.InsufficientStockException;
import br.com.divenclasse.inventory.exception.ProductNotFoundException;
import br.com.divenclasse.inventory.repository.ProductRepository;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.Assertions.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ProductService — Testes Unitários")
class ProductServiceTest {

    @Mock private ProductRepository productRepository;
    @InjectMocks private ProductService productService;

    private Product buildProduct(Long id, int estoque) {
        return Product.builder().id(id).nome("Terno Slim Azul Marinho")
                .categoria(Category.TERNO).tamanho("48").cor("Azul Marinho")
                .preco(new BigDecimal("1299.90")).quantidadeEstoque(estoque)
                .marca("Divenclasse").genero(Gender.MASCULINO).build();
    }

    private ProductRequestDTO buildRequestDTO() {
        return ProductRequestDTO.builder().nome("Terno Slim Azul Marinho")
                .categoria(Category.TERNO).tamanho("48").cor("Azul Marinho")
                .preco(new BigDecimal("1299.90")).quantidadeEstoque(15)
                .marca("Divenclasse").genero(Gender.MASCULINO).build();
    }

    @Test @DisplayName("Criar produto com sucesso")
    void createProduct_success() {
        when(productRepository.save(any())).thenReturn(buildProduct(1L, 15));
        ProductResponseDTO result = productService.createProduct(buildRequestDTO());
        assertThat(result.getId()).isEqualTo(1L);
        assertThat(result.isEmEstoque()).isTrue();
    }

    @Test @DisplayName("Buscar por ID existente")
    void findById_found() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(buildProduct(1L, 10)));
        assertThat(productService.findById(1L).getId()).isEqualTo(1L);
    }

    @Test @DisplayName("Buscar por ID inexistente — lança exceção")
    void findById_notFound() {
        when(productRepository.findById(99L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productService.findById(99L))
                .isInstanceOf(ProductNotFoundException.class);
    }

    @Test @DisplayName("Listar com paginação")
    void findAll_returnPage() {
        Pageable pageable = PageRequest.of(0, 10);
        when(productRepository.findAll(pageable))
                .thenReturn(new PageImpl<>(List.of(buildProduct(1L, 5), buildProduct(2L, 0))));
        Page<ProductResponseDTO> result = productService.findAll(pageable);
        assertThat(result.getTotalElements()).isEqualTo(2);
        assertThat(result.getContent().get(1).isEmEstoque()).isFalse();
    }

    @Test @DisplayName("Adicionar estoque com sucesso")
    void updateStock_adicionar() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(buildProduct(1L, 10)));
        when(productRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        ProductResponseDTO result = productService.updateStock(1L,
                new StockUpdateDTO(5, StockUpdateDTO.StockOperation.ADICIONAR));
        assertThat(result.getQuantidadeEstoque()).isEqualTo(15);
    }

    @Test @DisplayName("Remover estoque com sucesso")
    void updateStock_remover_success() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(buildProduct(1L, 10)));
        when(productRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        ProductResponseDTO result = productService.updateStock(1L,
                new StockUpdateDTO(3, StockUpdateDTO.StockOperation.REMOVER));
        assertThat(result.getQuantidadeEstoque()).isEqualTo(7);
    }

    @Test @DisplayName("Remover sem saldo — lança InsufficientStockException")
    void updateStock_remover_insufficient() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(buildProduct(1L, 2)));
        assertThatThrownBy(() -> productService.updateStock(1L,
                new StockUpdateDTO(10, StockUpdateDTO.StockOperation.REMOVER)))
                .isInstanceOf(InsufficientStockException.class);
        verify(productRepository, never()).save(any());
    }

    @Test @DisplayName("Estoque zerado — emEstoque false")
    void updateStock_zero() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(buildProduct(1L, 5)));
        when(productRepository.save(any())).thenAnswer(inv -> inv.getArgument(0));
        ProductResponseDTO result = productService.updateStock(1L,
                new StockUpdateDTO(5, StockUpdateDTO.StockOperation.REMOVER));
        assertThat(result.isEmEstoque()).isFalse();
    }

    @Test @DisplayName("Deletar produto existente")
    void deleteProduct_success() {
        Product p = buildProduct(1L, 5);
        when(productRepository.findById(1L)).thenReturn(Optional.of(p));
        productService.deleteProduct(1L);
        verify(productRepository, times(1)).delete(p);
    }

    @Test @DisplayName("Deletar produto inexistente — lança exceção")
    void deleteProduct_notFound() {
        when(productRepository.findById(999L)).thenReturn(Optional.empty());
        assertThatThrownBy(() -> productService.deleteProduct(999L))
                .isInstanceOf(ProductNotFoundException.class);
        verify(productRepository, never()).delete(any());
    }
}
