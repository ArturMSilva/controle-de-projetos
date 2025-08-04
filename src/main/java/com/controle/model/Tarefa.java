package com.controle.model;

import java.time.LocalDate;
import java.util.List;
import java.util.ArrayList;

public class Tarefa {
    private Integer tarefaId;
    private Integer projetoId;
    private String titulo;
    private String responsavel;
    private LocalDate prazo;
    private Boolean concluida;
    private List<HistoricoAlteracao> historico;

    public Tarefa() {
        this.concluida = false;
        this.historico = new ArrayList<>();
    }

    public Tarefa(Integer projetoId, String titulo, String responsavel, LocalDate prazo) {
        this();
        this.projetoId = projetoId;
        this.titulo = titulo;
        this.responsavel = responsavel;
        this.prazo = prazo;
    }

    public Integer getTarefaId() {
        return tarefaId;
    }

    public void setTarefaId(Integer tarefaId) {
        this.tarefaId = tarefaId;
    }

    public Integer getProjetoId() {
        return projetoId;
    }

    public void setProjetoId(Integer projetoId) {
        this.projetoId = projetoId;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getResponsavel() {
        return responsavel;
    }

    public void setResponsavel(String responsavel) {
        this.responsavel = responsavel;
    }

    public LocalDate getPrazo() {
        return prazo;
    }

    public void setPrazo(LocalDate prazo) {
        this.prazo = prazo;
    }

    public Boolean getConcluida() {
        return concluida;
    }

    public void setConcluida(Boolean concluida) {
        this.concluida = concluida;
    }

    public List<HistoricoAlteracao> getHistorico() {
        return historico;
    }

    public void setHistorico(List<HistoricoAlteracao> historico) {
        this.historico = historico;
    }

    public void adicionarHistorico(HistoricoAlteracao alteracao) {
        this.historico.add(alteracao);
        alteracao.setTarefaId(this.tarefaId);
    }

    public void marcarComoConcluida() {
        this.concluida = true;
    }

    public void marcarComoPendente() {
        this.concluida = false;
    }

    public boolean isPrazoVencido() {
        if (prazo == null || concluida) {
            return false;
        }
        return LocalDate.now().isAfter(prazo);
    }

    @Override
    public String toString() {
        return "Tarefa{" +
                "tarefaId=" + tarefaId +
                ", projetoId=" + projetoId +
                ", titulo='" + titulo + '\'' +
                ", responsavel='" + responsavel + '\'' +
                ", prazo=" + prazo +
                ", concluida=" + concluida +
                ", numHistorico=" + (historico != null ? historico.size() : 0) +
                '}';
    }
}
