package servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import negocio.ReporteNegocio;
import negocioImplement.ReporteNegocioImplement;
import daoImplement.DaoReportes;
import daoImplement.ConexionDB;

import java.math.BigDecimal;

@WebServlet("/ServletReporte")
public class ServletReporte extends HttpServlet {
    private static final long serialVersionUID = 1L;

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");
        int clienteId = Integer.parseInt(request.getParameter("IDCliente"));

        if (startDateStr == null || endDateStr == null) {
            response.getWriter().append("Error: Missing date parameters.");
            return;
        }

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd"); 
        
        try {
            java.util.Date utilStartDate = dateFormat.parse(startDateStr);
            java.util.Date utilEndDate = dateFormat.parse(endDateStr);

            java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
            java.sql.Date sqlEndDate = new java.sql.Date(utilEndDate.getTime());

            ReporteNegocio reporteNegocio = new ReporteNegocioImplement();

            int cantidadPrestamos = reporteNegocio.obtenerCantidadPrestamos(clienteId);
            BigDecimal totalTransferencias = reporteNegocio.obtenerTotalTransferencias(clienteId);
            System.out.println("Total de Transferencias obtenido: " + totalTransferencias);
            String nombreCliente = reporteNegocio.obtenerNombreCliente(clienteId);
            BigDecimal importeRestantePrestamo = reporteNegocio.obtenerImporteRestantePrestamo(clienteId);
            BigDecimal saldoEntreFechas = reporteNegocio.obtenerSaldoEntreFechas(clienteId, sqlStartDate, sqlEndDate);
            BigDecimal saldoRecibidoEntreFechas = reporteNegocio.obtenerSaldoRecibidoEntreFechas(clienteId, sqlStartDate, sqlEndDate);

            request.setAttribute("cantidadPrestamos", cantidadPrestamos);
            request.setAttribute("totalTransferencias", totalTransferencias);
            request.setAttribute("nombreCliente", nombreCliente);
            request.setAttribute("importeRestantePrestamo", importeRestantePrestamo);
            request.setAttribute("saldoEntreFechas", saldoEntreFechas);
            request.setAttribute("saldoRecibidoEntreFechas", saldoRecibidoEntreFechas);
            
            request.getRequestDispatcher("Reportes.jsp").forward(request, response);
        } catch (ParseException e) {
            throw new ServletException("Error al parsear las fechas", e);
        } catch (SQLException e) {
            throw new ServletException("Error al obtener el reporte del cliente", e);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}