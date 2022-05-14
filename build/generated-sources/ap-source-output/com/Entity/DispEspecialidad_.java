package com.Entity;

import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispEspecialidad.class)
public class DispEspecialidad_ { 

    public static volatile SingularAttribute<DispEspecialidad, String> descripcion;
    public static volatile SingularAttribute<DispEspecialidad, Date> fechaIngreso;
    public static volatile SingularAttribute<DispEspecialidad, String> codigo;
    public static volatile SingularAttribute<DispEspecialidad, String> estado;
    public static volatile SingularAttribute<DispEspecialidad, Date> fechaModificacion;
    public static volatile SingularAttribute<DispEspecialidad, String> usuarioIngreso;
    public static volatile SingularAttribute<DispEspecialidad, String> usuarioModificacion;
    public static volatile SingularAttribute<DispEspecialidad, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispEspecialidad, IsSector> idSector;
    public static volatile SingularAttribute<DispEspecialidad, Integer> idEspecialidad;
    public static volatile SingularAttribute<DispEspecialidad, String> nombre;
    public static volatile SingularAttribute<DispEspecialidad, IsCiudad> idCiudad;

}