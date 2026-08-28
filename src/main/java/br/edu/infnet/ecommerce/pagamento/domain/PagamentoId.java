package br.edu.infnet.ecommerce.pagamento.domain;

public record PagamentoId(Long valor) {

    public PagamentoId {
        if (valor == null || valor <= 0) {
            throw new IllegalArgumentException("PagamentoId deve ser um valor positivo");
        }
    }

    public static PagamentoId de(Long valor) {
        return new PagamentoId(valor);
    }

}
