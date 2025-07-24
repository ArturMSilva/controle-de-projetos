package com.controle.model;

import java.time.LocalDateTime;

public class HistoricoAlteracao {
    private Integer historicoId;
    private Integer tarefaId;
    private LocalDateTime dataAlteracao;
    private String campoAlterado;
    private String valorAntigo;
    private String valorNovo;

    // Construtor padrão
    public HistoricoAlteracao() {
        this.dataAlteracao = LocalDateTime.now();
    }

    // Construtor com parâmetros principais
    public HistoricoAlteracao(Integer tarefaId, String campoAlterado, String valorAntigo, String valorNovo) {
        this();
        this.tarefaId = tarefaId;
        this.campoAlterado = campoAlterado;
        this.valorAntigo = valorAntigo;
        this.valorNovo = valorNovo;
    }

    // Getters e Setters
    public Integer getHistoricoId() {
        return historicoId;
    }

    public void setHistoricoId(Integer historicoId) {
        this.historicoId = historicoId;
    }

    public Integer getTarefaId() {
        return tarefaId;
    }

    public void setTarefaId(Integer tarefaId) {
        this.tarefaId = tarefaId;
    }

    public LocalDateTime getDataAlteracao() {
        return dataAlteracao;
    }

    public void setDataAlteracao(LocalDateTime dataAlteracao) {
        this.dataAlteracao = dataAlteracao;
    }

    public String getCampoAlterado() {
        return campoAlterado;
    }

    public void setCampoAlterado(String campoAlterado) {
        this.campoAlterado = campoAlterado;
    }

    public String getValorAntigo() {
        return valorAntigo;
    }

    public void setValorAntigo(String valorAntigo) {
        this.valorAntigo = valorAntigo;
    }

    public String getValorNovo() {
        return valorNovo;
    }

    public void setValorNovo(String valorNovo) {
        this.valorNovo = valorNovo;
    }

    // Métodos auxiliares
    public static HistoricoAlteracao criarAlteracao(Integer tarefaId, String campo, String valorAntigo, String valorNovo) {
        return new HistoricoAlteracao(tarefaId, campo, valorAntigo, valorNovo);
    }

    @Override
    public String toString() {
        return "HistoricoAlteracao{" +
                "historicoId=" + historicoId +
                ", tarefaId=" + tarefaId +
                ", dataAlteracao=" + dataAlteracao +
                ", campoAlterado='" + campoAlterado + '\'' +
                ", valorAntigo='" + valorAntigo + '\'' +
                ", valorNovo='" + valorNovo + '\'' +
                '}';
    }
}
