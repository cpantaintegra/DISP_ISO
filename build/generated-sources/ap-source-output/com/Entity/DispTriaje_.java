package com.Entity;

import com.Entity.DispAgendamiento;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispTriaje.class)
public class DispTriaje_ { 

    public static volatile SingularAttribute<DispTriaje, String> estado;
    public static volatile SingularAttribute<DispTriaje, Date> fechaModificacion;
    public static volatile SingularAttribute<DispTriaje, String> talla;
    public static volatile SingularAttribute<DispTriaje, String> peso;
    public static volatile SingularAttribute<DispTriaje, DispAgendamiento> idAgendamiento;
    public static volatile SingularAttribute<DispTriaje, String> frecuenciaRespiratoria;
    public static volatile SingularAttribute<DispTriaje, String> presionArterial;
    public static volatile SingularAttribute<DispTriaje, IsSector> idSector;
    public static volatile SingularAttribute<DispTriaje, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispTriaje, Date> fechaIngreso;
    public static volatile SingularAttribute<DispTriaje, Integer> idTriaje;
    public static volatile SingularAttribute<DispTriaje, String> usuarioIngreso;
    public static volatile SingularAttribute<DispTriaje, String> saturacion;
    public static volatile SingularAttribute<DispTriaje, String> usuarioModificacion;
    public static volatile SingularAttribute<DispTriaje, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispTriaje, String> temperatura;
    public static volatile SingularAttribute<DispTriaje, String> frecuenciaCardiaca;
    public static volatile SingularAttribute<DispTriaje, String> imc;

}