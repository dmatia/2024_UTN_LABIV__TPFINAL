package negocioImplement;

import dao.iDaoIntereses;
import daoImplement.DaoIntereses;
import entidad.Intereses;
import negocio.InteresesNegocio;

public class InteresesNegocioImplement implements InteresesNegocio{
	
	private iDaoIntereses daoIntereses;
	
	public InteresesNegocioImplement() {
        this.daoIntereses = new DaoIntereses();
    }

	@Override
	public Intereses obternerInteres(int id) {
		return daoIntereses.obternerInteres(id);
	}

}
