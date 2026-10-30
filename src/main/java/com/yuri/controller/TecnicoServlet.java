package com.yuri.controller;

import com.google.gson.Gson;
import com.yuri.dao.TecnicoDAO;
import com.yuri.model.Tecnico;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// Definimos a URL da nossa API
@WebServlet("/api/tecnicos")
public class TecnicoServlet extends HttpServlet {

    private TecnicoDAO dao;
    private Gson gson;

    @Override
    public void init() {
        dao = new TecnicoDAO();
        gson = new Gson();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        // Configura o cabeçalho para responder em JSON com suporte a acentuação (UTF-8)
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        // Habilita o CORS para o Angular conseguir consumir a API futuramente
        response.setHeader("Access-Control-Allow-Origin", "*");

        try {
            // Busca os dados no MySQL
            List<Tecnico> tecnicos = dao.listarTodos();

            // Converte a lista de objetos Java para uma String no formato JSON
            String json = gson.toJson(tecnicos);

            // Envia o JSON como resposta HTTP
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"erro\": \"Erro interno ao processar a requisição\"}");
        }
    }
}