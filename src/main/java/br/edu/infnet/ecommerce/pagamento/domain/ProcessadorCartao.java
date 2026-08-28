package br.edu.infnet.ecommerce.pagamento.domain;

public interface ProcessadorCartao {
    ResultadoProcessamento processar(Dinheiro valor, NumeroCartao numeroCartao);
}
