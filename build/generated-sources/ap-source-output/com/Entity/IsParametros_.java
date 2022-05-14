package com.Entity;

import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(IsParametros.class)
public class IsParametros_ { 

    public static volatile SingularAttribute<IsParametros, Integer> idParametro;
    public static volatile SingularAttribute<IsParametros, Date> fechaIngreso;
    public static volatile SingularAttribute<IsParametros, String> codigo;
    public static volatile SingularAttribute<IsParametros, String> estado;
    public static volatile SingularAttribute<IsParametros, Date> fechaModificacion;
    public static volatile SingularAttribute<IsParametros, String> usuarioIngreso;
    public static volatile SingularAttribute<IsParametros, String> valor;
    public static volatile SingularAttribute<IsParametros, String> usuarioModificacion;
    public static volatile SingularAttribute<IsParametros, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<IsParametros, IsSector> idSector;
    public static volatile SingularAttribute<IsParametros, IsCiudad> idCiudad;
    public static volatile SingularAttribute<IsParametros, String> detalle;

}