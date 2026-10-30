package com.yuri.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.yuri.dao.ChamadoDAO;
import com.yuri.model.Chamado;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@WebServlet("/api/chamados")
public class ChamadoServlet extends HttpServlet {

    private ChamadoDAO dao;
    private Gson gson;

    @Override
    public void init() {
        dao = new ChamadoDAO();
        // Usamos o GsonBuilder aqui para garantir que as datas sejam formatadas corretamente no JSON
        gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").create();
    }

    private void setAccessControlHeaders(HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS");
        response.setHeader("Access-Control-Allow-Headers", "Content-Type");
    }

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
            List<Chamado> chamados = dao.listarTodos();
            String json = gson.toJson(chamados);

            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"erro\": \"Erro interno ao listar chamados\"}");
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setAccessControlHeaders(response);

        try {
            BufferedReader reader = request.getReader();
            Chamado novoChamado = gson.fromJson(reader, Chamado.class);

            dao.salvar(novoChamado);

            response.setStatus(HttpServletResponse.SC_CREATED);
            response.getWriter().print("{\"mensagem\": \"Chamado criado com sucesso!\"}");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"erro\": \"Erro ao salvar o chamado\"}");
        }
    }
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setAccessControlHeaders(response);

        try {
            BufferedReader reader = request.getReader();
            Chamado chamadoAtualizado = gson.fromJson(reader, Chamado.class);

            dao.atualizar(chamadoAtualizado);

            response.setStatus(HttpServletResponse.SC_OK);
            response.getWriter().print("{\"mensagem\": \"Chamado atualizado com sucesso!\"}");

        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"erro\": \"Erro ao atualizar o chamado\"}");
        }
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");
        setAccessControlHeaders(response);

        try {
            // Lemos o ID a partir da URL (ex: /api/chamados?id=1)
            String idParam = request.getParameter("id");
            if (idParam != null) {
                int id = Integer.parseInt(idParam);
                dao.deletar(id);
                response.setStatus(HttpServletResponse.SC_OK);
                response.getWriter().print("{\"mensagem\": \"Chamado deletado com sucesso!\"}");
            } else {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                response.getWriter().print("{\"erro\": \"ID do chamado não fornecido\"}");
            }
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            response.getWriter().print("{\"erro\": \"Erro ao deletar o chamado\"}");
        }
    }
}