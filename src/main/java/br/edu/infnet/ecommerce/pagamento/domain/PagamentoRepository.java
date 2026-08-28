package br.edu.infnet.ecommerce.pagamento.domain;

import java.util.Optional;

public interface PagamentoRepository {
    Pagamento salvar(Pagamento pagamento);
    Optional<Pagamento> buscarPorId(PagamentoId id);
    Optional<Pagamento> buscarPorPedidoId(Long pedidoId);
}
