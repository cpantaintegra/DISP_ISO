package com.Entity;

import com.Entity.DispResultadoExamen;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispResultadoDetalle.class)
public class DispResultadoDetalle_ { 

    public static volatile SingularAttribute<DispResultadoDetalle, DispResultadoExamen> idResultadoExamen;
    public static volatile SingularAttribute<DispResultadoDetalle, Date> fechaIngreso;
    public static volatile SingularAttribute<DispResultadoDetalle, String> estado;
    public static volatile SingularAttribute<DispResultadoDetalle, Date> fechaModificacion;
    public static volatile SingularAttribute<DispResultadoDetalle, String> resultado;
    public static volatile SingularAttribute<DispResultadoDetalle, String> usuarioIngreso;
    public static volatile SingularAttribute<DispResultadoDetalle, Integer> idResultadoDetalle;
    public static volatile SingularAttribute<DispResultadoDetalle, String> usuarioModificacion;
    public static volatile SingularAttribute<DispResultadoDetalle, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispResultadoDetalle, IsSector> idSector;
    public static volatile SingularAttribute<DispResultadoDetalle, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispResultadoDetalle, Integer> observacion;

}