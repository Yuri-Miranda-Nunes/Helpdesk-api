package com.yuri.controller;

import com.google.gson.Gson;
import com.yuri.dao.ClienteDAO;
import com.yuri.model.Cliente;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/clientes")
public class ClienteServlet extends HttpServlet {

    private ClienteDAO dao;
    private Gson gson;

    @Override
    public void init() {
        dao = new ClienteDAO();
        gson = new Gson();
    }

    // Configuração global de CORS para todas as respostas deste Servlet
    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

    // Lida com requisições de pré-verificação (Preflight) do navegador/Angular
    @Override
    protected void doOptions(HttpServletRequest request, HttpServletResponse response) {
        setAccessControlHeaders(response);
        response.setStatus(HttpServletResponse.SC_OK);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setAccessControlHeaders(response);

        try {
            List<Cliente> clientes = dao.listarTodos();
            String json = gson.toJson(clientes);

            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"erro\": \"Erro interno ao listar clientes\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setAccessControlHeaders(response);

        try {
            // Lê o JSON recebido no corpo da requisição HTTP
            BufferedReader reader = request.getReader();

            // Converte o JSON mágico do Gson para um objeto Cliente
            Cliente novoCliente = gson.fromJson(reader, Cliente.class);

            // Grava na base de dados
            dao.salvar(novoCliente);

            // Retorna o status 201 (Created) e o objeto JSON salvo
            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().print(gson.toJson(novoCliente));

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"erro\": \"Erro ao salvar o cliente\"}");
        }
    }
}