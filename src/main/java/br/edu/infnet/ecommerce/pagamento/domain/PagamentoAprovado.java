package br.edu.infnet.ecommerce.pagamento.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record PagamentoAprovado(
        Long pagamentoId,
        Long pedidoId,
        Long usuarioId,
        BigDecimal valor,
        String codigoAutorizacao,
        LocalDateTime ocorridoEm
) implements EventoDominio {
}

