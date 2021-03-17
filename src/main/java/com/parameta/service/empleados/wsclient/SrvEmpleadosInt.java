package com.parameta.service.empleados.wsclient;

import com.parameta.service.empleados.srv.EmpleadoRequest;
import com.parameta.service.empleados.srv.EmpleadoResponse;

public interface SrvEmpleadosInt {

	/**
	 * M�todo encargado de consumir el servicio SOAP
	 * @param request Objeto con los datos de la petici�n
	 * @return Objeto con los datos del response
	 */
	public EmpleadoResponse registrarEmpleado(EmpleadoRequest request);
}
