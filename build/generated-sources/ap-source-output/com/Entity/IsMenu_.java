package com.Entity;

import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsRolesMenu;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(IsMenu.class)
public class IsMenu_ { 

    public static volatile SingularAttribute<IsMenu, String> icono;
    public static volatile SingularAttribute<IsMenu, String> estado;
    public static volatile SingularAttribute<IsMenu, Date> fechaModificacion;
    public static volatile CollectionAttribute<IsMenu, IsRolesMenu> isRolesMenuCollection;
    public static volatile SingularAttribute<IsMenu, Integer> idMenu;
    public static volatile SingularAttribute<IsMenu, IsSector> idSector;
    public static volatile SingularAttribute<IsMenu, String> nombre;
    public static volatile SingularAttribute<IsMenu, String> url;
    public static volatile SingularAttribute<IsMenu, IsCiudad> idCiudad;
    public static volatile SingularAttribute<IsMenu, String> detalle;
    public static volatile SingularAttribute<IsMenu, Date> fechaIngreso;
    public static volatile SingularAttribute<IsMenu, String> usuarioIngreso;
    public static volatile SingularAttribute<IsMenu, String> usuarioModificacion;
    public static volatile SingularAttribute<IsMenu, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<IsMenu, Integer> orden;
    public static volatile SingularAttribute<IsMenu, Integer> idMenuPadre;

}