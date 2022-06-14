package com.delivery;

import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "app", value = "/app")
public class Controller extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        if (request.getParameterMap().isEmpty()) {
            request.setAttribute("citiesList", Dao.getCitiesList());
            try {
                request.getRequestDispatcher("form.jsp").forward(request, response);
            } catch (ServletException | IOException e) {
                System.err.println(e.getMessage());
            }
        } else {
            processRequest(request, response);
        }
    }

    private void processRequest(HttpServletRequest request, HttpServletResponse response) {
        var requestParameters = new RequestParameters(request);
        AntColonyOptimization antColonyOptimization = new AntColonyOptimization(requestParameters);
        var solution = antColonyOptimization.solve();
        request.setAttribute("solution", solution);
        try {
            request.getRequestDispatcher("result-page.jsp").forward(request, response);
        } catch (ServletException | IOException e) {
            System.err.println(e.getMessage());
        }
    }


}