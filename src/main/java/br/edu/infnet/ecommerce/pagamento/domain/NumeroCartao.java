package br.edu.infnet.ecommerce.pagamento.domain;

public final class NumeroCartao {

    private final String numeroCompleto;
    private final String mascarado;

    private NumeroCartao(String numeroCompleto) {
        this.numeroCompleto = numeroCompleto;
        this.mascarado = mascarar(numeroCompleto);
    }

    public static NumeroCartao de(String numero) {
        String limpo = numero == null ? "" : numero.replaceAll("\\D", "");
        if (limpo.length() < 4) {
            throw new IllegalArgumentException("Número de cartão inválido");
        }
        return new NumeroCartao(limpo);
    }

    private static String mascarar(String numero) {
        return "**** **** **** " + numero.substring(numero.length() - 4);
    }

    public boolean terminaEm(String sufixo) {
        return numeroCompleto.endsWith(sufixo);
    }

    public String getMascarado() {
        return mascarado;
    }

    public String getNumeroCompleto() {
        return numeroCompleto;
    }
}