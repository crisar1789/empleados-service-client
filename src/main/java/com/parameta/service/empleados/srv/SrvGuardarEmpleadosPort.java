package com.parameta.service.empleados.srv;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.jws.soap.SOAPBinding;
import javax.xml.bind.annotation.XmlSeeAlso;

/**
 * This class was generated by Apache CXF 3.3.1
 * 2021-03-15T22:26:13.634-05:00
 * Generated source version: 3.3.1
 *
 */
@WebService(targetNamespace = "http://www.parameta.com/service/empleados", name = "SrvGuardarEmpleadosPort")
@XmlSeeAlso({ObjectFactory.class})
@SOAPBinding(parameterStyle = SOAPBinding.ParameterStyle.BARE)
public interface SrvGuardarEmpleadosPort {

    @WebMethod(operationName = "Empleado")
    @WebResult(name = "EmpleadoResponse", targetNamespace = "http://www.parameta.com/service/empleados", partName = "EmpleadoResponse")
    public EmpleadoResponse empleado(
        @WebParam(partName = "EmpleadoRequest", name = "EmpleadoRequest", targetNamespace = "http://www.parameta.com/service/empleados")
        EmpleadoRequest empleadoRequest
    );
}
