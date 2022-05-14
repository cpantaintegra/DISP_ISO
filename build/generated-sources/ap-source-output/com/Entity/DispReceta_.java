package com.Entity;

import com.Entity.DispAgendamiento;
import com.Entity.DispDetalleReceta;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispReceta.class)
public class DispReceta_ { 

    public static volatile SingularAttribute<DispReceta, Date> fechaIngreso;
    public static volatile SingularAttribute<DispReceta, String> estado;
    public static volatile SingularAttribute<DispReceta, Date> fechaModificacion;
    public static volatile SingularAttribute<DispReceta, String> usuarioIngreso;
    public static volatile SingularAttribute<DispReceta, Integer> idReceta;
    public static volatile SingularAttribute<DispReceta, DispAgendamiento> idAgendamiento;
    public static volatile SingularAttribute<DispReceta, String> observaciones;
    public static volatile CollectionAttribute<DispReceta, DispDetalleReceta> dispDetalleRecetaCollection;
    public static volatile SingularAttribute<DispReceta, String> usuarioModificacion;
    public static volatile SingularAttribute<DispReceta, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispReceta, IsSector> idSector;
    public static volatile SingularAttribute<DispReceta, IsCiudad> idCiudad;

}