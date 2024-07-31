package negocio;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

public interface ReporteNegocio {
    int obtenerCantidadPrestamos(int clienteId) throws SQLException;
    BigDecimal obtenerTotalTransferencias(int clienteId) throws SQLException;
    String obtenerNombreCliente(int clienteId) throws SQLException;
    BigDecimal obtenerImporteRestantePrestamo(int clienteId) throws SQLException;
    public BigDecimal obtenerSaldoEntreFechas(int clienteId, Date fechaInicio, Date fechaFin) throws SQLException;
    double obtenerTotalTransferencias(Date startDate, Date endDate) throws SQLException;
    public BigDecimal obtenerSaldoRecibidoEntreFechas(int clienteId, Date fechaInicio, Date fechaFin) throws SQLException;
    
}