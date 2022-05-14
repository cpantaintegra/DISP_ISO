package com.Entity;

import com.Entity.DispAgendamiento;
import com.Entity.DispEspecialidad;
import com.Entity.DispPrecio;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispServicio.class)
public class DispServicio_ { 

    public static volatile SingularAttribute<DispServicio, String> estado;
    public static volatile SingularAttribute<DispServicio, Date> fechaModificacion;
    public static volatile SingularAttribute<DispServicio, IsSector> idSector;
    public static volatile SingularAttribute<DispServicio, String> nombre;
    public static volatile SingularAttribute<DispServicio, Boolean> condicion;
    public static volatile SingularAttribute<DispServicio, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispServicio, Date> fechaIngreso;
    public static volatile CollectionAttribute<DispServicio, DispAgendamiento> dispAgendamientoCollection;
    public static volatile SingularAttribute<DispServicio, String> usuarioIngreso;
    public static volatile SingularAttribute<DispServicio, String> usuarioModificacion;
    public static volatile SingularAttribute<DispServicio, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispServicio, Integer> idServicio;
    public static volatile SingularAttribute<DispServicio, DispEspecialidad> idEspecialidad;
    public static volatile CollectionAttribute<DispServicio, DispPrecio> dispPrecioCollection;

}