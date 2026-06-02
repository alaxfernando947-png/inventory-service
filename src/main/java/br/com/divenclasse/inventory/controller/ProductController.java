package br.com.divenclasse.inventory.controller;

import br.com.divenclasse.inventory.dto.*;
import br.com.divenclasse.inventory.entity.enums.Category;
import br.com.divenclasse.inventory.entity.enums.Gender;
import br.com.divenclasse.inventory.service.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/products")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Products", description = "Gerenciamento de produtos e estoque — Divenclasse")
public class ProductController {

    private final ProductService productService;

    @Operation(summary = "Cadastrar produto")
    @PostMapping
    public ResponseEntity<ProductResponseDTO> createProduct(@Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(productService.createProduct(dto));
    }

    @Operation(summary = "Listar todos os produtos (paginado)")
    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> findAll(
            @PageableDefault(size = 10, sort = "nome") Pageable pageable) {
        return ResponseEntity.ok(productService.findAll(pageable));
    }

    @Operation(summary = "Buscar produto por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> findById(@PathVariable Long id) {
        return ResponseEntity.ok(productService.findById(id));
    }

    @Operation(summary = "Buscar produto por nome")
    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchByName(
            @RequestParam String name,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(productService.searchByName(name, pageable));
    }

    @Operation(summary = "Buscar por categoria")
    @GetMapping("/category/{category}")
    public ResponseEntity<Page<ProductResponseDTO>> findByCategory(
            @PathVariable Category category,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(productService.findByCategory(category, pageable));
    }

    @Operation(summary = "Buscar por tamanho")
    @GetMapping("/size/{size}")
    public ResponseEntity<Page<ProductResponseDTO>> findBySize(
            @PathVariable String size,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(productService.findBySize(size, pageable));
    }

    @Operation(summary = "Buscar por gênero")
    @GetMapping("/gender/{gender}")
    public ResponseEntity<Page<ProductResponseDTO>> findByGender(
            @PathVariable Gender gender,
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(productService.findByGender(gender, pageable));
    }

    @Operation(summary = "Produtos com estoque baixo (≤ 3 unidades)")
    @GetMapping("/low-stock")
    public ResponseEntity<Page<ProductResponseDTO>> findLowStock(
            @PageableDefault(size = 10) Pageable pageable) {
        return ResponseEntity.ok(productService.findLowStock(pageable));
    }

    @Operation(summary = "Atualizar produto completo")
    @PutMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> updateProduct(
            @PathVariable Long id, @Valid @RequestBody ProductRequestDTO dto) {
        return ResponseEntity.ok(productService.updateProduct(id, dto));
    }

    @Operation(summary = "Atualizar estoque (ADICIONAR ou REMOVER)")
    @PatchMapping("/{id}/stock")
    public ResponseEntity<ProductResponseDTO> updateStock(
            @PathVariable Long id, @Valid @RequestBody StockUpdateDTO dto) {
        return ResponseEntity.ok(productService.updateStock(id, dto));
    }

    @Operation(summary = "Excluir produto")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
