package com.Entity;

import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsPermisos;
import com.Entity.IsRoles;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(IsRolesPermisos.class)
public class IsRolesPermisos_ { 

    public static volatile SingularAttribute<IsRolesPermisos, Integer> idRolesPermisos;
    public static volatile SingularAttribute<IsRolesPermisos, Date> fechaIngreso;
    public static volatile SingularAttribute<IsRolesPermisos, IsPermisos> idPermisos;
    public static volatile SingularAttribute<IsRolesPermisos, String> estado;
    public static volatile SingularAttribute<IsRolesPermisos, Date> fechaModificacion;
    public static volatile SingularAttribute<IsRolesPermisos, String> usuarioIngreso;
    public static volatile SingularAttribute<IsRolesPermisos, IsRoles> idRoles;
    public static volatile SingularAttribute<IsRolesPermisos, String> usuarioModificacion;
    public static volatile SingularAttribute<IsRolesPermisos, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<IsRolesPermisos, IsSector> idSector;
    public static volatile SingularAttribute<IsRolesPermisos, IsCiudad> idCiudad;

}