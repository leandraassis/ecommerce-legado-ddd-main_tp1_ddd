package br.edu.infnet.ecommerce.pagamento.domain;

import java.math.BigDecimal;

public record Dinheiro(BigDecimal valor) {
    public Dinheiro {
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) {
            throw new IllegalArgumentException("Valor do pagamento deve ser POSITIVO");
        }
        valor = valor.setScale(2, BigDecimal.ROUND_HALF_EVEN);
    }

    public static Dinheiro de(BigDecimal valor) {
        return new Dinheiro(valor);
    }

    public boolean diferenteDe (Dinheiro outro) {
        return this.valor.compareTo(outro.valor) != 0;
    }
}
