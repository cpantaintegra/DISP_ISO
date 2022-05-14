package com.Entity;

import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsEmpresaRoles;
import com.Entity.IsRolesMenu;
import com.Entity.IsRolesPermisos;
import com.Entity.IsSector;
import com.Entity.IsUsuarios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(IsRoles.class)
public class IsRoles_ { 

    public static volatile SingularAttribute<IsRoles, String> estado;
    public static volatile SingularAttribute<IsRoles, Date> fechaModificacion;
    public static volatile CollectionAttribute<IsRoles, IsRolesMenu> isRolesMenuCollection;
    public static volatile CollectionAttribute<IsRoles, IsEmpresaRoles> isEmpresaRolesCollection;
    public static volatile SingularAttribute<IsRoles, Integer> idRoles;
    public static volatile CollectionAttribute<IsRoles, IsRolesPermisos> isRolesPermisosCollection;
    public static volatile SingularAttribute<IsRoles, IsSector> idSector;
    public static volatile SingularAttribute<IsRoles, String> nombre;
    public static volatile CollectionAttribute<IsRoles, IsUsuarios> isUsuariosCollection;
    public static volatile SingularAttribute<IsRoles, IsCiudad> idCiudad;
    public static volatile SingularAttribute<IsRoles, String> detalle;
    public static volatile SingularAttribute<IsRoles, Date> fechaIngreso;
    public static volatile SingularAttribute<IsRoles, String> usuarioIngreso;
    public static volatile SingularAttribute<IsRoles, String> usuarioModificacion;
    public static volatile SingularAttribute<IsRoles, IsEmpresa> idEmpresa;

}