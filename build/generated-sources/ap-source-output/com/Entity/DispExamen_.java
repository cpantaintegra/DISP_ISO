package com.Entity;

import com.Entity.DispEstudiosMedicos;
import com.Entity.DispUnidadMedica;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispExamen.class)
public class DispExamen_ { 

    public static volatile SingularAttribute<DispExamen, String> descripcion;
    public static volatile SingularAttribute<DispExamen, String> estado;
    public static volatile SingularAttribute<DispExamen, Date> fechaModificacion;
    public static volatile SingularAttribute<DispExamen, Date> tiempoHora;
    public static volatile SingularAttribute<DispExamen, DispUnidadMedica> idUnidadMedica;
    public static volatile SingularAttribute<DispExamen, IsSector> idSector;
    public static volatile SingularAttribute<DispExamen, String> nombre;
    public static volatile SingularAttribute<DispExamen, Boolean> vejigaLlena;
    public static volatile SingularAttribute<DispExamen, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispExamen, Boolean> ayuno;
    public static volatile SingularAttribute<DispExamen, Date> fechaIngreso;
    public static volatile SingularAttribute<DispExamen, Integer> idExamen;
    public static volatile SingularAttribute<DispExamen, Float> precio;
    public static volatile SingularAttribute<DispExamen, String> usuarioIngreso;
    public static volatile SingularAttribute<DispExamen, DispEstudiosMedicos> idEstudiosMedicos;
    public static volatile SingularAttribute<DispExamen, String> usuarioModificacion;
    public static volatile SingularAttribute<DispExamen, IsEmpresa> idEmpresa;

}