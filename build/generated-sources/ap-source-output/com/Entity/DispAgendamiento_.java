package com.Entity;

import com.Entity.DispCliente;
import com.Entity.DispEspecialidad;
import com.Entity.DispMedicoPersonal;
import com.Entity.DispReceta;
import com.Entity.DispResultado;
import com.Entity.DispServicio;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispAgendamiento.class)
public class DispAgendamiento_ { 

    public static volatile SingularAttribute<DispAgendamiento, Boolean> accion;
    public static volatile SingularAttribute<DispAgendamiento, Date> fechaInicioMedico;
    public static volatile SingularAttribute<DispAgendamiento, Date> fechaLlamada;
    public static volatile SingularAttribute<DispAgendamiento, Date> fechaInicioAtencion;
    public static volatile SingularAttribute<DispAgendamiento, String> estado;
    public static volatile CollectionAttribute<DispAgendamiento, DispResultado> dispResultadoCollection;
    public static volatile SingularAttribute<DispAgendamiento, Integer> idAgendamiento;
    public static volatile SingularAttribute<DispAgendamiento, DispResultado> dispResultado;
    public static volatile SingularAttribute<DispAgendamiento, Integer> turno;
    public static volatile SingularAttribute<DispAgendamiento, IsSector> idSector;
    public static volatile SingularAttribute<DispAgendamiento, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispAgendamiento, DispCliente> idCliente;
    public static volatile SingularAttribute<DispAgendamiento, String> usuarioIngreso;
    public static volatile SingularAttribute<DispAgendamiento, Date> fechaFinalizado;
    public static volatile SingularAttribute<DispAgendamiento, String> usuarioModificacion;
    public static volatile SingularAttribute<DispAgendamiento, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispAgendamiento, Date> fechaAtendido;
    public static volatile SingularAttribute<DispAgendamiento, Date> fechaLlamarMedico;
    public static volatile SingularAttribute<DispAgendamiento, DispMedicoPersonal> idMedicoPersonal;
    public static volatile CollectionAttribute<DispAgendamiento, DispReceta> dispRecetaCollection;
    public static volatile SingularAttribute<DispAgendamiento, Date> fechaRecuperacion;
    public static volatile SingularAttribute<DispAgendamiento, Date> fechaModificacion;
    public static volatile SingularAttribute<DispAgendamiento, BigDecimal> costo;
    public static volatile SingularAttribute<DispAgendamiento, String> enTiempoPasado;
    public static volatile SingularAttribute<DispAgendamiento, Date> fecha;
    public static volatile SingularAttribute<DispAgendamiento, Date> fechaIngreso;
    public static volatile SingularAttribute<DispAgendamiento, Date> fechaAtendidoMedico;
    public static volatile SingularAttribute<DispAgendamiento, String> pagado;
    public static volatile SingularAttribute<DispAgendamiento, DispServicio> idServicio;
    public static volatile SingularAttribute<DispAgendamiento, DispEspecialidad> idEspecialidad;
    public static volatile SingularAttribute<DispAgendamiento, Integer> intentos;

}