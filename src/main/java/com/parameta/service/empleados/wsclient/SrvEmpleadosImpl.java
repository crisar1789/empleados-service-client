package com.parameta.service.empleados.wsclient;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Optional;

import org.apache.cxf.configuration.jsse.TLSClientParameters;
import org.apache.cxf.ext.logging.LoggingInInterceptor;
import org.apache.cxf.ext.logging.LoggingOutInterceptor;
import org.apache.cxf.frontend.ClientProxy;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.apache.cxf.transport.http.HTTPConduit;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.parameta.service.empleados.exception.SrvException;
import com.parameta.service.empleados.srv.EmpleadoRequest;
import com.parameta.service.empleados.srv.EmpleadoResponse;
import com.parameta.service.empleados.srv.SrvGuardarEmpleadosPort;
import com.parameta.service.empleados.srv.SrvGuardarEmpleadosPortService;
import com.parameta.service.empleados.util.ConstantsUtil;

public class SrvEmpleadosImpl implements SrvEmpleadosInt {

	private static final Logger log = LoggerFactory.getLogger(SrvEmpleadosImpl.class);
	private SrvGuardarEmpleadosPort srvEmpleados;
	
	public SrvEmpleadosImpl() {
        super();
        if (null == srvEmpleados) {
        	SrvGuardarEmpleadosPortService studentDetailsPortService = new SrvGuardarEmpleadosPortService();
            this.srvEmpleados = studentDetailsPortService.getSrvGuardarEmpleadosPortSoap11();
        }
    }
	
	/**
	 * Método encargado de configurar el endpoint parta consumo del servicio SOAP
	 * @param endpoint Endpont del servicio
	 * @param requestTimeout Request timeout
	 * @param connectionTimeOut Connection Timeout
	 */
	public SrvEmpleadosImpl(String endpoint, int requestTimeout, int connectionTimeOut) {
        if (null == srvEmpleados) {
            try {
                JaxWsProxyFactoryBean factory = new JaxWsProxyFactoryBean();
                factory.getInInterceptors().add(new LoggingInInterceptor());
                factory.getOutInterceptors().add(new LoggingOutInterceptor());
                factory.setServiceClass(SrvGuardarEmpleadosPort.class);
                factory.setAddress(endpoint);

                this.srvEmpleados = (SrvGuardarEmpleadosPort) factory.create();

                setupTLS(this.srvEmpleados, requestTimeout, connectionTimeOut);
            } catch (Exception e) {
                log.error("[WSClient - Construc] Error iniciando", e);
            }
        }
    }
	
	private static void setupTLS(SrvGuardarEmpleadosPort port, int requestTimeout, int connectionTimeout)
            throws FileNotFoundException, IOException, GeneralSecurityException {
        HTTPConduit httpConduit = (HTTPConduit) ClientProxy.getClient(port).getConduit();
        httpConduit.getClient().setConnectionTimeout(connectionTimeout);
        httpConduit.getClient().setConnectionRequestTimeout(requestTimeout);

        TLSClientParameters tlsClientParameters = new TLSClientParameters();
        tlsClientParameters.setDisableCNCheck(true);

        httpConduit.setTlsClientParameters(tlsClientParameters);
    }
	
	/**
	 * ;étodo encargado de consumir el servicio SOAP
	 */
	@Override
	public EmpleadoResponse registrarEmpleado(EmpleadoRequest request) {
		EmpleadoResponse response = null;
        try {
            response = this.srvEmpleados.empleado(request);
            if (!Optional.ofNullable(response).isPresent()) {
                throw new SrvException(ConstantsUtil.CLIENT_COD_ERROR, ConstantsUtil.SRV_RS_ERROR);
            }
            return response;
        } catch (javax.xml.ws.WebServiceException e) {
        	log.error("WSClient Error", e);
        	throw new SrvException(ConstantsUtil.CLIENT_COD_ERROR, ConstantsUtil.SRV_ERROR + e.getMessage());
        } catch (Exception e) {
        	log.error("WSClient Error", e);
        	throw new SrvException(ConstantsUtil.CLIENT_COD_ERROR, ConstantsUtil.INTERNAL_CLIENT_ERROR + e.getMessage());
        }
	}

}
