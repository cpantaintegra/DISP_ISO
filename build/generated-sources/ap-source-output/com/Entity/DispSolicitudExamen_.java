package com.Entity;

import com.Entity.DispCliente;
import com.Entity.DispDetalleFactura;
import com.Entity.DispExamen;
import com.Entity.DispMedicoPersonal;
import com.Entity.DispResultadoExamen;
import com.Entity.DispTomaMuestra;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispSolicitudExamen.class)
public class DispSolicitudExamen_ { 

    public static volatile SingularAttribute<DispSolicitudExamen, String> estado;
    public static volatile CollectionAttribute<DispSolicitudExamen, DispTomaMuestra> dispTomaMuestraCollection;
    public static volatile SingularAttribute<DispSolicitudExamen, Date> fechaModificacion;
    public static volatile CollectionAttribute<DispSolicitudExamen, DispDetalleFactura> dispDetalleFacturaCollection;
    public static volatile SingularAttribute<DispSolicitudExamen, IsSector> idSector;
    public static volatile SingularAttribute<DispSolicitudExamen, IsCiudad> idCiudad;
    public static volatile CollectionAttribute<DispSolicitudExamen, DispResultadoExamen> dispResultadoExamenCollection;
    public static volatile SingularAttribute<DispSolicitudExamen, Date> fecha;
    public static volatile SingularAttribute<DispSolicitudExamen, Date> fechaIngreso;
    public static volatile SingularAttribute<DispSolicitudExamen, DispExamen> idExamen;
    public static volatile SingularAttribute<DispSolicitudExamen, DispCliente> idCliente;
    public static volatile SingularAttribute<DispSolicitudExamen, String> usuarioIngreso;
    public static volatile SingularAttribute<DispSolicitudExamen, String> usuarioModificacion;
    public static volatile SingularAttribute<DispSolicitudExamen, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispSolicitudExamen, Integer> idSolicitudExamen;
    public static volatile SingularAttribute<DispSolicitudExamen, DispMedicoPersonal> idMedicoPersonal;

}