package br.com.divenclasse.inventory.service;

import br.com.divenclasse.inventory.dto.*;
import br.com.divenclasse.inventory.entity.Product;
import br.com.divenclasse.inventory.entity.enums.Category;
import br.com.divenclasse.inventory.entity.enums.Gender;
import br.com.divenclasse.inventory.exception.InsufficientStockException;
import br.com.divenclasse.inventory.exception.ProductNotFoundException;
import br.com.divenclasse.inventory.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class ProductService {

    private final ProductRepository productRepository;
    private static final int LOW_STOCK_THRESHOLD = 3;

    @Transactional
    public ProductResponseDTO createProduct(ProductRequestDTO dto) {
        log.info("Criando produto: {}", dto.getNome());
        Product saved = productRepository.save(mapToEntity(dto));
        log.info("Produto criado com ID: {}", saved.getId());
        return mapToResponse(saved);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findAll(Pageable pageable) {
        return productRepository.findAll(pageable).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public ProductResponseDTO findById(Long id) {
        return mapToResponse(getProductOrThrow(id));
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> searchByName(String nome, Pageable pageable) {
        return productRepository.findByNomeContainingIgnoreCase(nome, pageable).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findByCategory(Category category, Pageable pageable) {
        return productRepository.findByCategoria(category, pageable).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findBySize(String tamanho, Pageable pageable) {
        return productRepository.findByTamanhoIgnoreCase(tamanho, pageable).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findByGender(Gender gender, Pageable pageable) {
        return productRepository.findByGenero(gender, pageable).map(this::mapToResponse);
    }

    @Transactional(readOnly = true)
    public Page<ProductResponseDTO> findLowStock(Pageable pageable) {
        return productRepository.findLowStock(LOW_STOCK_THRESHOLD, pageable).map(this::mapToResponse);
    }

    @Transactional
    public ProductResponseDTO updateProduct(Long id, ProductRequestDTO dto) {
        log.info("Atualizando produto ID: {}", id);
        Product product = getProductOrThrow(id);
        product.setNome(dto.getNome());
        product.setDescricao(dto.getDescricao());
        product.setCategoria(dto.getCategoria());
        product.setTamanho(dto.getTamanho());
        product.setCor(dto.getCor());
        product.setPreco(dto.getPreco());
        product.setQuantidadeEstoque(dto.getQuantidadeEstoque());
        product.setMarca(dto.getMarca());
        product.setGenero(dto.getGenero());
        return mapToResponse(productRepository.save(product));
    }

    @Transactional
    public ProductResponseDTO updateStock(Long id, StockUpdateDTO dto) {
        log.info("Atualizando estoque — produto ID: {} | operação: {} | qtd: {}", id, dto.getOperacao(), dto.getQuantidade());
        Product product = getProductOrThrow(id);
        switch (dto.getOperacao()) {
            case ADICIONAR -> product.setQuantidadeEstoque(product.getQuantidadeEstoque() + dto.getQuantidade());
            case REMOVER -> {
                if (product.getQuantidadeEstoque() < dto.getQuantidade()) {
                    throw new InsufficientStockException(id, dto.getQuantidade(), product.getQuantidadeEstoque());
                }
                product.setQuantidadeEstoque(product.getQuantidadeEstoque() - dto.getQuantidade());
                if (product.getQuantidadeEstoque() <= LOW_STOCK_THRESHOLD) {
                    log.warn("ALERTA: Produto '{}' com estoque baixo: {}", product.getNome(), product.getQuantidadeEstoque());
                }
            }
        }
        return mapToResponse(productRepository.save(product));
    }

    @Transactional
    public void deleteProduct(Long id) {
        log.info("Excluindo produto ID: {}", id);
        productRepository.delete(getProductOrThrow(id));
        log.info("Produto ID {} excluído", id);
    }

    private Product getProductOrThrow(Long id) {
        return productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));
    }

    private Product mapToEntity(ProductRequestDTO dto) {
        return Product.builder()
                .nome(dto.getNome()).descricao(dto.getDescricao())
                .categoria(dto.getCategoria()).tamanho(dto.getTamanho())
                .cor(dto.getCor()).preco(dto.getPreco())
                .quantidadeEstoque(dto.getQuantidadeEstoque())
                .marca(dto.getMarca()).genero(dto.getGenero())
                .build();
    }

    private ProductResponseDTO mapToResponse(Product p) {
        return ProductResponseDTO.builder()
                .id(p.getId()).nome(p.getNome()).descricao(p.getDescricao())
                .categoria(p.getCategoria()).tamanho(p.getTamanho())
                .cor(p.getCor()).preco(p.getPreco())
                .quantidadeEstoque(p.getQuantidadeEstoque())
                .marca(p.getMarca()).genero(p.getGenero())
                .dataCadastro(p.getDataCadastro()).dataAtualizacao(p.getDataAtualizacao())
                .emEstoque(p.getQuantidadeEstoque() > 0)
                .build();
    }
}
