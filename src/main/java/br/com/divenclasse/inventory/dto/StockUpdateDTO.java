package br.com.divenclasse.inventory.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @Builder
@Schema(description = "Dados para atualização de estoque")
public class StockUpdateDTO {

    @NotNull(message = "Quantidade é obrigatória")
    @Min(value = 1, message = "Quantidade deve ser pelo menos 1")
    @Schema(example = "10")
    private Integer quantidade;

    @NotNull(message = "Operação é obrigatória")
    @Schema(example = "ADICIONAR", allowableValues = {"ADICIONAR", "REMOVER"})
    private StockOperation operacao;

    public enum StockOperation {
        ADICIONAR, REMOVER
    }
}
