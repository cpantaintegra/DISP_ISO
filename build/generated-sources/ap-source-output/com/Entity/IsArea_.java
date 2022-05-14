package com.Entity;

import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import com.Entity.IsUsuarios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(IsArea.class)
public class IsArea_ { 

    public static volatile SingularAttribute<IsArea, Date> fechaIngreso;
    public static volatile SingularAttribute<IsArea, String> estado;
    public static volatile SingularAttribute<IsArea, Date> fechaModificacion;
    public static volatile SingularAttribute<IsArea, Integer> idArea;
    public static volatile SingularAttribute<IsArea, String> usuarioIngreso;
    public static volatile SingularAttribute<IsArea, String> usuarioModificacion;
    public static volatile SingularAttribute<IsArea, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<IsArea, IsSector> idSector;
    public static volatile SingularAttribute<IsArea, String> nombre;
    public static volatile CollectionAttribute<IsArea, IsUsuarios> isUsuariosCollection;
    public static volatile SingularAttribute<IsArea, IsCiudad> idCiudad;
    public static volatile SingularAttribute<IsArea, String> detalle;

}