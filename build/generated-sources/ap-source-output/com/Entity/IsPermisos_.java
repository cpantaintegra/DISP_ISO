package com.Entity;

import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsRolesPermisos;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(IsPermisos.class)
public class IsPermisos_ { 

    public static volatile SingularAttribute<IsPermisos, Integer> idPermisos;
    public static volatile SingularAttribute<IsPermisos, Date> fechaIngreso;
    public static volatile SingularAttribute<IsPermisos, String> estado;
    public static volatile SingularAttribute<IsPermisos, Date> fechaModificacion;
    public static volatile SingularAttribute<IsPermisos, String> usuarioIngreso;
    public static volatile CollectionAttribute<IsPermisos, IsRolesPermisos> isRolesPermisosCollection;
    public static volatile SingularAttribute<IsPermisos, String> usuarioModificacion;
    public static volatile SingularAttribute<IsPermisos, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<IsPermisos, IsSector> idSector;
    public static volatile SingularAttribute<IsPermisos, String> nombre;
    public static volatile SingularAttribute<IsPermisos, IsCiudad> idCiudad;
    public static volatile SingularAttribute<IsPermisos, String> detalle;

}