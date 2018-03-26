package unitTests;

import static org.junit.Assert.*;

import org.junit.Test;

import conecta2.transfer.TransferEmpresa;
import conecta2.modelo.Empresa;
import conecta2.servicioAplicacion.*;

public class HU1CrearEmpresa {

	@Test
	public void testInsert() {
		/*
		SAEmpresaImp sa = new SAEmpresaImp();
		
		TransferEmpresa trEmpresa = new TransferEmpresa("empresaPruebaNombre", "empresaPruebaEmail@empresaPruebaEmail.com", "Z99999999", "Abc1111", "");
		sa.crearEmpresa(trEmpresa);
		
		Empresa empresaBD = sa.buscarPorEmail(trEmpresa.getEmail());
		
		boolean email = empresaBD.getEmail() == trEmpresa.getEmail();
		boolean nombre = empresaBD.getNombre() == trEmpresa.getNombre();
		boolean cif = empresaBD.getCif() == trEmpresa.getCif();
		boolean password = empresaBD.getPassword() == trEmpresa.getPassword();
		
		assertEquals(true, (email && nombre && cif && password));
		*/
	}

}
