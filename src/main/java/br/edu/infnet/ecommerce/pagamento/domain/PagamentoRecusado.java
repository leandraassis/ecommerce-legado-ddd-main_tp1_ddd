package br.edu.infnet.ecommerce.pagamento.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoRecusado(
        Long pagamentoId,
        Long pedidoId,
        Long usuarioId,
        BigDecimal valor,
        String motivo,
        LocalDateTime ocorridoEm
) implements EventoDominio {
}
