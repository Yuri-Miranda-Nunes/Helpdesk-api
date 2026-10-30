package com.yuri.model;

import java.sql.Timestamp;

public class Chamado {
    private int id;
    private String titulo;
    private String descricao;
    private String prioridade;
    private String status;
    private Timestamp dataAbertura;
    private Timestamp dataFechamento;
    private int clienteId;
    private Integer tecnicoId; // Integer permite ser null (chamado não atribuído)

    public Chamado() {}

    // Construtor completo
    public Chamado(int id, String titulo, String descricao, String prioridade, String status,
                   Timestamp dataAbertura, Timestamp dataFechamento, int clienteId, Integer tecnicoId) {
        this.id = id;
        this.titulo = titulo;
        this.descricao = descricao;
        this.prioridade = prioridade;
        this.status = status;
        this.dataAbertura = dataAbertura;
        this.dataFechamento = dataFechamento;
        this.clienteId = clienteId;
        this.tecnicoId = tecnicoId;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescricao() { return descricao; }
    public void setDescricao(String descricao) { this.descricao = descricao; }

    public String getPrioridade() { return prioridade; }
    public void setPrioridade(String prioridade) { this.prioridade = prioridade; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public Timestamp getDataAbertura() { return dataAbertura; }
    public void setDataAbertura(Timestamp dataAbertura) { this.dataAbertura = dataAbertura; }

    public Timestamp getDataFechamento() { return dataFechamento; }
    public void setDataFechamento(Timestamp dataFechamento) { this.dataFechamento = dataFechamento; }

    public int getClienteId() { return clienteId; }
    public void setClienteId(int clienteId) { this.clienteId = clienteId; }

    public Integer getTecnicoId() { return tecnicoId; }
    public void setTecnicoId(Integer tecnicoId) { this.tecnicoId = tecnicoId; }
}