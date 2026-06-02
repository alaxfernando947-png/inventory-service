package br.com.divenclasse.inventory.dto;

import br.com.divenclasse.inventory.entity.enums.Category;
import br.com.divenclasse.inventory.entity.enums.Gender;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.*;
import java.math.BigDecimal;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Dados de entrada para cadastro ou atualização de produto")
public class ProductRequestDTO {

    @NotBlank(message = "Nome é obrigatório")
    @Size(min = 3, max = 150, message = "Nome deve ter entre 3 e 150 caracteres")
    @Schema(example = "Terno Slim Azul Marinho")
    private String nome;

    @Size(max = 500, message = "Descrição deve ter no máximo 500 caracteres")
    @Schema(example = "Terno slim fit em lã premium")
    private String descricao;

    @NotNull(message = "Categoria é obrigatória")
    @Schema(example = "TERNO")
    private Category categoria;

    @NotBlank(message = "Tamanho é obrigatório")
    @Size(max = 10)
    @Schema(example = "48")
    private String tamanho;

    @NotBlank(message = "Cor é obrigatória")
    @Schema(example = "Azul Marinho")
    private String cor;

    @NotNull(message = "Preço é obrigatório")
    @DecimalMin(value = "0.01", message = "Preço deve ser maior que zero")
    @Digits(integer = 8, fraction = 2)
    @Schema(example = "1299.90")
    private BigDecimal preco;

    @NotNull(message = "Quantidade em estoque é obrigatória")
    @Min(value = 0, message = "Quantidade não pode ser negativa")
    @Schema(example = "15")
    private Integer quantidadeEstoque;

    @Size(max = 80)
    @Schema(example = "Divenclasse")
    private String marca;

    @NotNull(message = "Gênero é obrigatório")
    @Schema(example = "MASCULINO")
    private Gender genero;
}
