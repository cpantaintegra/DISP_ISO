package com.Entity;

import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispImagen.class)
public class DispImagen_ { 

    public static volatile SingularAttribute<DispImagen, String> noombre;
    public static volatile SingularAttribute<DispImagen, Date> fechaIngreso;
    public static volatile SingularAttribute<DispImagen, String> estado;
    public static volatile SingularAttribute<DispImagen, Date> fechaModificacion;
    public static volatile SingularAttribute<DispImagen, String> usuarioIngreso;
    public static volatile SingularAttribute<DispImagen, Integer> idImagen;
    public static volatile SingularAttribute<DispImagen, String> usuarioModificacion;
    public static volatile SingularAttribute<DispImagen, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispImagen, IsSector> idSector;
    public static volatile SingularAttribute<DispImagen, String> url;
    public static volatile SingularAttribute<DispImagen, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispImagen, String> observacion;

}