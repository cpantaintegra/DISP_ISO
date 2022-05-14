package com.Entity;

import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsRoles;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(IsEmpresaRoles.class)
public class IsEmpresaRoles_ { 

    public static volatile SingularAttribute<IsEmpresaRoles, Date> fechaIngreso;
    public static volatile SingularAttribute<IsEmpresaRoles, String> estado;
    public static volatile SingularAttribute<IsEmpresaRoles, Date> fechaModificacion;
    public static volatile SingularAttribute<IsEmpresaRoles, String> usuarioIngreso;
    public static volatile SingularAttribute<IsEmpresaRoles, IsRoles> idRoles;
    public static volatile SingularAttribute<IsEmpresaRoles, String> usuarioModificacion;
    public static volatile SingularAttribute<IsEmpresaRoles, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<IsEmpresaRoles, IsSector> idSector;
    public static volatile SingularAttribute<IsEmpresaRoles, Integer> idEmpresaRoles;
    public static volatile SingularAttribute<IsEmpresaRoles, IsCiudad> idCiudad;

}