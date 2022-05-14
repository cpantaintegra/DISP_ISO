package com.Entity;

import com.Entity.DispAgendamiento;
import com.Entity.DispFactura;
import com.Entity.DispSolicitudExamen;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispDetalleFactura.class)
public class DispDetalleFactura_ { 

    public static volatile SingularAttribute<DispDetalleFactura, String> estado;
    public static volatile SingularAttribute<DispDetalleFactura, Date> fechaModificacion;
    public static volatile SingularAttribute<DispDetalleFactura, DispAgendamiento> idAgendamiento;
    public static volatile SingularAttribute<DispDetalleFactura, IsSector> idSector;
    public static volatile SingularAttribute<DispDetalleFactura, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispDetalleFactura, Date> fechaIngreso;
    public static volatile SingularAttribute<DispDetalleFactura, String> usuarioIngreso;
    public static volatile SingularAttribute<DispDetalleFactura, DispFactura> idFactura;
    public static volatile SingularAttribute<DispDetalleFactura, String> usuarioModificacion;
    public static volatile SingularAttribute<DispDetalleFactura, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispDetalleFactura, Integer> cantidad;
    public static volatile SingularAttribute<DispDetalleFactura, DispSolicitudExamen> idSolicitudExamen;
    public static volatile SingularAttribute<DispDetalleFactura, Integer> idDetalleFactura;

}