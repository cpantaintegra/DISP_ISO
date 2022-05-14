package com.Entity;

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
@StaticMetamodel(DispUnidadMedica.class)
public class DispUnidadMedica_ { 

    public static volatile SingularAttribute<DispUnidadMedica, String> descripcion;
    public static volatile SingularAttribute<DispUnidadMedica, Date> fechaIngreso;
    public static volatile SingularAttribute<DispUnidadMedica, String> estado;
    public static volatile SingularAttribute<DispUnidadMedica, Date> fechaModificacion;
    public static volatile SingularAttribute<DispUnidadMedica, String> usuarioIngreso;
    public static volatile SingularAttribute<DispUnidadMedica, String> usuarioModificacion;
    public static volatile SingularAttribute<DispUnidadMedica, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispUnidadMedica, Integer> idUnidadMedica;
    public static volatile SingularAttribute<DispUnidadMedica, IsSector> idSector;
    public static volatile CollectionAttribute<DispUnidadMedica, DispExamen> dispExamenCollection;
    public static volatile SingularAttribute<DispUnidadMedica, String> nombre;
    public static volatile SingularAttribute<DispUnidadMedica, IsCiudad> idCiudad;

}