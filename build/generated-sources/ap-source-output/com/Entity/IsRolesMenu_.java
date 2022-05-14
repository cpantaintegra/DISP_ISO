package com.Entity;

import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsMenu;
import com.Entity.IsRoles;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(IsRolesMenu.class)
public class IsRolesMenu_ { 

    public static volatile SingularAttribute<IsRolesMenu, Date> fechaIngreso;
    public static volatile SingularAttribute<IsRolesMenu, String> estado;
    public static volatile SingularAttribute<IsRolesMenu, Date> fechaModificacion;
    public static volatile SingularAttribute<IsRolesMenu, String> usuarioIngreso;
    public static volatile SingularAttribute<IsRolesMenu, IsMenu> idMenu;
    public static volatile SingularAttribute<IsRolesMenu, IsRoles> idRoles;
    public static volatile SingularAttribute<IsRolesMenu, String> usuarioModificacion;
    public static volatile SingularAttribute<IsRolesMenu, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<IsRolesMenu, IsSector> idSector;
    public static volatile SingularAttribute<IsRolesMenu, Integer> idRolesMenu;
    public static volatile SingularAttribute<IsRolesMenu, IsCiudad> idCiudad;

}