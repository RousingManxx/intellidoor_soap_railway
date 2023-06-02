package com.uv.registro;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import https.registro_uv_mx.registro.AddRegistroRequest;
import https.registro_uv_mx.registro.AddRegistroResponse;
import https.registro_uv_mx.registro.BuscarRegistroRequest;
import https.registro_uv_mx.registro.BuscarRegistroResponse;
import https.registro_uv_mx.registro.EliminarRegistroRequest;
import https.registro_uv_mx.registro.EliminarRegistroResponse;
import https.registro_uv_mx.registro.MostrarRegistrosResponse;

@Endpoint
public class EndPoint {
    @Autowired
    private IRegistro iRegistro;

    @PayloadRoot(namespace = "https://registro.uv.mx/registro", localPart = "AddRegistroRequest")
    @ResponsePayload
    public AddRegistroResponse Agregar(@RequestPayload AddRegistroRequest peticion){
        AddRegistroResponse respuesta = new AddRegistroResponse();
        Registro registro = new Registro();
        registro.setNombre(peticion.getNombre());
        registro.setFecha(peticion.getFecha());
        registro.setHora(peticion.getHora());
        registro.setAula(peticion.getAula());
        iRegistro.save(registro);
        respuesta.setRespuesta("Se agrego el registro");
        respuesta.setId(registro.getId());
        return respuesta;
    }

    @PayloadRoot(namespace = "https://registro.uv.mx/registro", localPart = "BuscarRegistroRequest")
    @ResponsePayload
    public BuscarRegistroResponse Buscar(@RequestPayload BuscarRegistroRequest peticion){
        BuscarRegistroResponse respuesta = new BuscarRegistroResponse();
        Iterable<Registro> todos = iRegistro.findAll();

        for (Registro registro : todos) {
            BuscarRegistroResponse.Registro n = new BuscarRegistroResponse.Registro();
            if(registro.getAula().equals(peticion.getAula())){
                registro.getId();
                n.setId(registro.getId());
                n.setAula(registro.getAula());
                n.setNombre(registro.getNombre());
                n.setFecha(registro.getFecha());
                n.setHora(registro.getHora());
                respuesta.getRegistro().add(n);
            }
        }

        return respuesta;
    }

    @PayloadRoot(namespace = "https://registro.uv.mx/registro", localPart = "MostrarRegistrosRequest")
    @ResponsePayload
    public MostrarRegistrosResponse Mostrar(){
        MostrarRegistrosResponse respuesta = new MostrarRegistrosResponse();
        Iterable<Registro> lista = iRegistro.findAll();
        
        for (Registro registro : lista) {
            MostrarRegistrosResponse.Registro n = new MostrarRegistrosResponse.Registro();
            n.setId(registro.getId());
            n.setAula(registro.getAula());
            n.setNombre(registro.getNombre());
            n.setFecha(registro.getFecha());
            n.setHora(registro.getHora());
            respuesta.getRegistro().add(n);
        }
        return respuesta;
    }

    @PayloadRoot(namespace = "https://registro.uv.mx/registro", localPart = "EliminarRegistroRequest")
    @ResponsePayload
    public EliminarRegistroResponse Eliminar(@RequestPayload EliminarRegistroRequest peticion){
        EliminarRegistroResponse respuesta = new EliminarRegistroResponse();
        iRegistro.deleteById(peticion.getId());
        respuesta.setRespuesta("Se elimino el registro");
        respuesta.setId(peticion.getId());
        return respuesta;
    }

}
