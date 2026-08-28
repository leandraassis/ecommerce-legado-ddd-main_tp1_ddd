package br.edu.infnet.ecommerce.pagamento.application;

import br.edu.infnet.ecommerce.pagamento.domain.*;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PagamentoApplicationService {

    private final PagamentoRepository pagamentoRepository;
    private final PedidoConsulta pedidoConsulta;
    private final UsuarioConsulta usuarioConsulta;
    private final ProcessadorCartao processadorCartao;

    public PagamentoApplicationService(
            PagamentoRepository pagamentoRepository,
            PedidoConsulta pedidoConsulta,
            UsuarioConsulta usuarioConsulta,
            ProcessadorCartao processadorCartao
    ) {
        this.pagamentoRepository = pagamentoRepository;
        this.pedidoConsulta = pedidoConsulta;
        this.usuarioConsulta = usuarioConsulta;
        this.processadorCartao = processadorCartao;
    }

    @Transactional
    public Pagamento processar(Long pedidoId, Long usuarioId, BigDecimal valorBruto, String numeroCartaoBruto) {
        if (!pedidoConsulta.existePedido(pedidoId)) {
            throw new IllegalArgumentException("Pedido não encontrado");
        }
        if (!usuarioConsulta.existeUsuario(usuarioId)) {
            throw new IllegalArgumentException("Usuário não encontrado");
        }

        Dinheiro valor = Dinheiro.de(valorBruto);
        NumeroCartao numeroCartao = NumeroCartao.de(numeroCartaoBruto);

        Pagamento pagamento = Pagamento.solicitar(pedidoId, usuarioId, valor, FormaPagamento.CARTAO, numeroCartao);

        ResultadoProcessamento resultado = processadorCartao.processar(valor, numeroCartao);

        if (resultado.aprovado()) {
            pagamento.aprovar(resultado.codigoAutorizacao());
        } else {
            pagamento.recusar(resultado.motivo());
        }

        return pagamentoRepository.salvar(pagamento);
    }
}
