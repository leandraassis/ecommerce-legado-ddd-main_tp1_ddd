package br.edu.infnet.ecommerce.pagamento.infra.persistence;

import br.edu.infnet.ecommerce.pagamento.domain.Pagamento;
import br.edu.infnet.ecommerce.pagamento.domain.PagamentoId;
import br.edu.infnet.ecommerce.pagamento.domain.PagamentoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class PagamentoRepositoryImpl implements PagamentoRepository {

    private final PagamentoJpaRepository jpaRepository;

    public PagamentoRepositoryImpl(PagamentoJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Pagamento salvar(Pagamento pagamento) {
        return jpaRepository.save(pagamento);
    }

    @Override
    public Optional<Pagamento> buscarPorId(PagamentoId id) {
        return jpaRepository.findById(id.valor());
    }

    @Override
    public Optional<Pagamento> buscarPorPedidoId(Long pedidoId) {
        return jpaRepository.findByPedidoId(pedidoId);
    }
}
