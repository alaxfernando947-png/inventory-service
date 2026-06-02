package br.com.divenclasse.inventory.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException(Long productId, int requested, int available) {
        super(String.format(
            "Estoque insuficiente para o produto ID %d. Solicitado: %d, Disponível: %d",
            productId, requested, available
        ));
    }
}
