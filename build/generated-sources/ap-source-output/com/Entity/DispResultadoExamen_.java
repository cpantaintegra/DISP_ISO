package com.Entity;

import com.Entity.DispResultadoDetalle;
import com.Entity.DispSolicitudExamen;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispResultadoExamen.class)
public class DispResultadoExamen_ { 

    public static volatile SingularAttribute<DispResultadoExamen, Integer> idResultadoExamen;
    public static volatile SingularAttribute<DispResultadoExamen, String> conclusion;
    public static volatile SingularAttribute<DispResultadoExamen, Date> fechaIngreso;
    public static volatile SingularAttribute<DispResultadoExamen, String> estado;
    public static volatile SingularAttribute<DispResultadoExamen, Date> fechaModificacion;
    public static volatile SingularAttribute<DispResultadoExamen, String> usuarioIngreso;
    public static volatile SingularAttribute<DispResultadoExamen, String> usuarioModificacion;
    public static volatile SingularAttribute<DispResultadoExamen, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispResultadoExamen, IsSector> idSector;
    public static volatile SingularAttribute<DispResultadoExamen, DispSolicitudExamen> idSolicitudExamen;
    public static volatile CollectionAttribute<DispResultadoExamen, DispResultadoDetalle> dispResultadoDetalleCollection;
    public static volatile SingularAttribute<DispResultadoExamen, IsCiudad> idCiudad;

}