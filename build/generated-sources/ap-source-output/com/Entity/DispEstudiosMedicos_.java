package com.Entity;

import com.Entity.DispEspecialidad;
import com.Entity.DispExamen;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispEstudiosMedicos.class)
public class DispEstudiosMedicos_ { 

    public static volatile SingularAttribute<DispEstudiosMedicos, String> estado;
    public static volatile SingularAttribute<DispEstudiosMedicos, Date> fechaModificacion;
    public static volatile SingularAttribute<DispEstudiosMedicos, IsSector> idSector;
    public static volatile SingularAttribute<DispEstudiosMedicos, String> nombre;
    public static volatile SingularAttribute<DispEstudiosMedicos, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispEstudiosMedicos, String> detalle;
    public static volatile SingularAttribute<DispEstudiosMedicos, Date> fechaIngreso;
    public static volatile SingularAttribute<DispEstudiosMedicos, String> usuarioIngreso;
    public static volatile SingularAttribute<DispEstudiosMedicos, Integer> idEstudiosMedicos;
    public static volatile SingularAttribute<DispEstudiosMedicos, String> usuarioModificacion;
    public static volatile SingularAttribute<DispEstudiosMedicos, IsEmpresa> idEmpresa;
    public static volatile CollectionAttribute<DispEstudiosMedicos, DispExamen> dispExamenCollection;
    public static volatile SingularAttribute<DispEstudiosMedicos, DispEspecialidad> idEspecialidad;

}