package br.com.divenclasse.inventory.dto;

import br.com.divenclasse.inventory.entity.enums.Category;
import br.com.divenclasse.inventory.entity.enums.Gender;
import lombok.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
public class ProductResponseDTO {
    private Long id;
    private String nome;
    private String descricao;
    private Category categoria;
    private String tamanho;
    private String cor;
    private BigDecimal preco;
    private Integer quantidadeEstoque;
    private String marca;
    private Gender genero;
    private LocalDateTime dataCadastro;
    private LocalDateTime dataAtualizacao;
    private boolean emEstoque;
}
