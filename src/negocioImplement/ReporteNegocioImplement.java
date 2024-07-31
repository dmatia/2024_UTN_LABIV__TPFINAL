package negocioImplement;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.SQLException;

import dao.iDaoReportes;
import daoImplement.DaoReportes;
import negocio.ReporteNegocio;

public  class ReporteNegocioImplement implements ReporteNegocio {
   
	private iDaoReportes daoReportes;

    public ReporteNegocioImplement() {
        this.daoReportes = new DaoReportes();;
    }

    @Override
    public int obtenerCantidadPrestamos(int clienteId) throws SQLException {
        return daoReportes.obtenerCantidadPrestamos(clienteId);
    }

    @Override
    public BigDecimal obtenerTotalTransferencias(int clienteId) throws SQLException {
        return daoReportes.obtenerTotalTransferencias(clienteId);
    }

    @Override
    public String obtenerNombreCliente(int clienteId) throws SQLException {
        return daoReportes.obtenerNombreCliente(clienteId);
    }

    @Override
    public BigDecimal obtenerImporteRestantePrestamo(int clienteId) throws SQLException {
        return daoReportes.obtenerImporteRestantePrestamo(clienteId);
    }
    
    @Override
    public BigDecimal obtenerSaldoEntreFechas(int clienteId, Date fechaInicio, Date fechaFin) throws SQLException{
    	return daoReportes.obtenerSaldoEntreFechas(clienteId, fechaInicio, fechaFin);
    }
    
    @Override
   	public BigDecimal obtenerSaldoRecibidoEntreFechas(int clienteId, Date fechaInicio, Date fechaFin) throws SQLException{
    	return daoReportes.obtenerSaldoRecibidoEntreFechas(clienteId, fechaInicio, fechaFin);
   	}
    
    public double obtenerTotalTransferencias(Date startDate, Date endDate) throws SQLException {
    	return daoReportes.obtenerTotalTransferencias(startDate, endDate);
    }
}