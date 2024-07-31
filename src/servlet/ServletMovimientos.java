package servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import daoImplement.DaoCliente;
import daoImplement.DaoMovimientos;
import entidad.Cliente;
import entidad.Cuentas;
import entidad.Movimientos;
import entidad.Usuario;
import negocio.MovimientosNegocio;
import negocio.ClienteNegocio;
import negocioImplement.MovimientosNegocioImplements;
import negocioImplement.ClienteNegocioImplement;

@WebServlet("/ServletMovimientos")
public class ServletMovimientos extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ServletMovimientos() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 System.out.println("Estoy en el doGet de ServletMovimientos");
    	
    	MovimientosNegocio mdao = new MovimientosNegocioImplements();
        // Parámetros
        String todosMovimientos = request.getParameter("TodosMovimientos");
        String clienteIdCliente = request.getParameter("clienteIdCliente");
        String btnBorrarFiltrosCliente = request.getParameter("btnBorrarFiltrosCliente");
        String tipoMovimientoCli = request.getParameter("tipoMovimientoCli");
        String btnVolver = request.getParameter("btnVolver");
        String clienteIdParam = request.getParameter("idCliente");
        String tipoMovimiento = request.getParameter("tipoMovimiento");
        String btnBorrarFiltros = request.getParameter("btnBorrarFiltros");

        // Lista de movimientos para Cliente para vista ClienteTodosMovimientos
        String clienteImporteMayorOIgual = request.getParameter("clienteImporteMayorOIgual");
        
        List<Movimientos> clienteListaTodosMovimientos = null;
        if (clienteIdCliente != null) {
            System.out.println("ESTOY DENTRO DEL PARAMETRO clienteIdCliente");
            int clienteId = Integer.parseInt(clienteIdCliente);
            HttpSession SesionIdCliente = request.getSession();
            SesionIdCliente.setAttribute("idClienteClienteTodosMovimientos", clienteId);
            
            if (btnBorrarFiltrosCliente != null) {
            	HttpSession session = request.getSession();
                int idCliente = (int) session.getAttribute("idClienteClienteTodosMovimientos");

                clienteListaTodosMovimientos = mdao.obtenerMovimientosDeUnCliente(idCliente);
            } else if (tipoMovimientoCli != null && !tipoMovimientoCli.isEmpty()) {
                int tipoMovimientoId = Integer.parseInt(tipoMovimientoCli);
                HttpSession session = request.getSession();
                int idCliente = (int) session.getAttribute("idClienteClienteTodosMovimientos");

                clienteListaTodosMovimientos = mdao.obtenerMovimientosPorTipo(idCliente, tipoMovimientoId);
            } else {
            	HttpSession session = request.getSession();
                int idCliente = (int) session.getAttribute("idClienteClienteTodosMovimientos");

                clienteListaTodosMovimientos = mdao.obtenerMovimientosDeUnCliente(idCliente);
            }
            
            if(clienteImporteMayorOIgual != null && !clienteImporteMayorOIgual.isEmpty()) {
            	double importe = Double.parseDouble(clienteImporteMayorOIgual);
            	
                //int clienteId = Integer.parseInt(idClienteImporteMayorOIgual);
                clienteListaTodosMovimientos = mdao.leerTodosClienteImporteMayorOIgual(clienteId, importe);
                
            }
            
            request.setAttribute("clienteListaTodosMovimientos", clienteListaTodosMovimientos);
            RequestDispatcher rDispatcher = request.getRequestDispatcher("/ClienteTodosMovimientos.jsp");
            rDispatcher.forward(request, response);
            return;
        }
       
        
        //Lista de todos los movimientos para AdminTodosMovimientos
        String adminImporteMayorOIgual = request.getParameter("adminImporteMayorOIgual");
        List<Movimientos> listaTodosMovimientos = null;
        String btnBorrarFiltrosAdminTodosMovimientos = request.getParameter("btnBorrarFiltrosAdminTodosMovimientos");
        String tipoMovimientoTodosMovimientos = request.getParameter("tipoMovimientoTodosMovimientos");
        if (todosMovimientos != null) {
            listaTodosMovimientos = mdao.leerTodos();
            request.setAttribute("listaTodosMovimientos", listaTodosMovimientos);
            
            RequestDispatcher rDispatcher = request.getRequestDispatcher("/AdminTodosMovimientos.jsp");
            rDispatcher.forward(request, response);
            return;
        }
        
        if (btnBorrarFiltrosAdminTodosMovimientos != null) {
            listaTodosMovimientos = mdao.leerTodos();
            request.setAttribute("listaTodosMovimientos", listaTodosMovimientos);
            
            RequestDispatcher rDispatcher = request.getRequestDispatcher("/AdminTodosMovimientos.jsp");
            rDispatcher.forward(request, response);
            return;
        }
        
        if (adminImporteMayorOIgual != null && !adminImporteMayorOIgual.isEmpty()) {
            try {
                double importe = Double.parseDouble(adminImporteMayorOIgual);
                listaTodosMovimientos = mdao.leerTodosAdminImporteMayorOIgual(importe);
                request.setAttribute("listaTodosMovimientos", listaTodosMovimientos);
                System.out.println("Movimientos adminImporteMayorOIgual obtenidos: " + listaTodosMovimientos.size());
                RequestDispatcher rDispatcher = request.getRequestDispatcher("/AdminTodosMovimientos.jsp");
                rDispatcher.forward(request, response);
                return;
            } catch (NumberFormatException e) {
                System.out.println("Error: El importe ingresado no es válido.");
            }
        }
        
        if (tipoMovimientoTodosMovimientos != null) {
            
                int idTipoMov = Integer.parseInt(tipoMovimientoTodosMovimientos);
                listaTodosMovimientos = mdao.obtenerTodosMovimientosPorTipo(idTipoMov);
                request.setAttribute("listaTodosMovimientos", listaTodosMovimientos);
                RequestDispatcher rDispatcher = request.getRequestDispatcher("/AdminTodosMovimientos.jsp");
                rDispatcher.forward(request, response);
                return;
            
        }
        
        
        //Lista de movimientos filtrada por Cliente para AdminVerMovimientos:
        
        if (btnVolver != null) {
            response.sendRedirect(request.getContextPath() + "/ServletCliente");
            return;
        }
        
        if (clienteIdParam != null) {
            int clienteId = Integer.parseInt(clienteIdParam);
            ArrayList<Movimientos> lista = null;

            if (btnBorrarFiltros != null) {
                lista = mdao.obtenerMovimientosDeUnCliente(clienteId);
            } else if (tipoMovimiento != null && !tipoMovimiento.isEmpty()) {
                int tipoMovimientoId = Integer.parseInt(tipoMovimiento);
                lista = mdao.obtenerMovimientosPorTipo(clienteId, tipoMovimientoId);
            } else {
                lista = mdao.obtenerMovimientosDeUnCliente(clienteId);
            }

            request.setAttribute("listaM", lista);
            RequestDispatcher rd = request.getRequestDispatcher("/AdminVerMovimientos.jsp");
            rd.forward(request, response);
            return;
        }

        RequestDispatcher rd = request.getRequestDispatcher("/AdminVerMovimientos.jsp");
        rd.forward(request, response);
        	
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("Estoy en el POST del servlet Movimientos");

        HttpSession session = request.getSession();
        Usuario usuario = (Usuario) session.getAttribute("usuario");

        String idCuentaParam = request.getParameter("idCuenta");
        int idCuenta = 0;
        if (idCuentaParam != null && !idCuentaParam.isEmpty()) {
            try {
            	System.out.println("idCuentaParam: " + idCuentaParam);
                idCuenta = Integer.parseInt(idCuentaParam);
            } catch (NumberFormatException e) {
                System.out.println("Error al convertir idCuenta: " + e.getMessage());
            }
        }
 
        ArrayList<Cuentas> cuentasCliente = (ArrayList<Cuentas>)session.getAttribute("cuentasCliente");
        System.out.println("Recupero el usuario en el post del servlet movimientos: " + usuario.toString());
        System.out.println("Array lit: " + cuentasCliente);
        System.out.println("Recupero el is cuenta en el post del servlet movimientos: " + idCuenta);
       
        Cuentas cuenta = null;
        for (Cuentas c : cuentasCliente) {
            if (c.getNumeroCuenta_Cue() == idCuenta) {
                cuenta = c;
                break;
            }
        }
        
        System.out.println("Cuenta encontrada: " + cuenta);

        
        if (usuario != null) {
            MovimientosNegocio movimientosNegocio = new MovimientosNegocioImplements();
            ArrayList<Movimientos> listaMovimientos = null;

            if (idCuenta != 0) {
                listaMovimientos = movimientosNegocio.obtenerMovimientosPorCuentaId(idCuenta);

                // PRUEBA POR CONSOLA
                System.out.println("LISTADO MOVIMIENTOS: ");
                for (Movimientos mov : listaMovimientos) {
                    System.out.println(mov.toString());
                }

                session.setAttribute("cuenta", cuenta);
                session.setAttribute("listaMovimientos", listaMovimientos);

                RequestDispatcher miDispacher = request.getRequestDispatcher("/ClienteMovimientosCuenta.jsp");
                miDispacher.forward(request, response);
            }
        } else {
        	System.out.println("Usuario no encontrado");
        }
    }
}











