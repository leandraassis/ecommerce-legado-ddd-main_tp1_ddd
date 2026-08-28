package br.edu.infnet.ecommerce.pagamento.domain;

import jakarta.persistence.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;

@Entity
@Table(name = "pagamentos")
public class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "pedido_id", nullable = false, unique = true)
    private Long pedidoId;

    @Column(name = "usuario_id", nullable = false)
    private Long usuarioId;

    @Column(nullable = false, precision = 15, scale = 2)
    private BigDecimal valor;

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento", nullable = false)
    private FormaPagamento formaPagamento;

    @Column(name = "numero_cartao_mascarado")
    private String numeroCartaoMascarado;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusPagamento statusPagamento;

    private String motivo;

    private String codigoAutorizacao;

    @Column(nullable = false)
    private LocalDateTime processadoEm;

    public Pagamento() {
    }

    private Pagamento(Long pedidoId, Long usuarioId, Dinheiro valor,
                      FormaPagamento formaPagamento, NumeroCartao numeroCartao) {
        this.pedidoId = Objects.requireNonNull(pedidoId, "pedidoId é obrigatório");
        this.usuarioId = Objects.requireNonNull(usuarioId, "usuarioId é obrigatório");
        this.valor = valor.valor();
        this.formaPagamento = Objects.requireNonNull(formaPagamento, "formaPagamento é obrigatório");
        this.numeroCartaoMascarado = numeroCartao != null ? numeroCartao.getMascarado() : null;
        this.statusPagamento = StatusPagamento.PENDENTE;
        this.processadoEm = LocalDateTime.now();
    }

    public static Pagamento solicitar(Long pedidoId, Long usuarioId, Dinheiro valor,
                                      FormaPagamento formaPagamento, NumeroCartao numeroCartao) {
        return new Pagamento(pedidoId, usuarioId, valor, formaPagamento, numeroCartao);
    }

    public void aprovar(String codigoAutorizacao) {
        exigirPendente();
        if (codigoAutorizacao == null || codigoAutorizacao.isBlank()) {
            throw new IllegalArgumentException("Código de autorização é obrigatório para aprovar um pagamento");
        }
        this.statusPagamento = StatusPagamento.APROVADO;
        this.codigoAutorizacao = codigoAutorizacao;
        this.processadoEm = LocalDateTime.now();
    }

    public void recusar(String motivo) {
        exigirPendente();
        if (motivo == null || motivo.isBlank()) {
            throw new IllegalArgumentException("Motivo é obrigatório para recusar um pagamento");
        }
        this.statusPagamento = StatusPagamento.RECUSADO;
        this.motivo = motivo;
        this.processadoEm = LocalDateTime.now();
    }

    private void exigirPendente() {
        if (this.statusPagamento != StatusPagamento.PENDENTE) {
            throw new IllegalStateException(
                    "Pagamento já foi processado (status atual: " + this.statusPagamento + "); não pode ser reprocessado");
        }
    }

    public PagamentoId getId() { return id != null ? PagamentoId.de(id) : null; }

    public Long getPedidoId() {
        return pedidoId;
    }

    public Long getUsuarioId() {
        return usuarioId;
    }

    public Dinheiro getValor() {
        return Dinheiro.de(valor);
    }

    public FormaPagamento getFormaPagamento() {
        return formaPagamento;
    }

    public String getNumeroCartaoMascarado() {
        return numeroCartaoMascarado;
    }

    public StatusPagamento getStatusPagamento() {
        return statusPagamento;
    }

    public String getMotivo() {
        return motivo;
    }

    public String getCodigoAutorizacao() {
        return codigoAutorizacao;
    }

    public LocalDateTime getProcessadoEm() {
        return processadoEm;
    }
}

