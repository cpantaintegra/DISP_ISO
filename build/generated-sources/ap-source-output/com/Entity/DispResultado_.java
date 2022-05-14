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
@StaticMetamodel(DispResultado.class)
public class DispResultado_ { 

    public static volatile SingularAttribute<DispResultado, String> estado;
    public static volatile SingularAttribute<DispResultado, Date> fechaModificacion;
    public static volatile SingularAttribute<DispResultado, Date> proximaCita;
    public static volatile SingularAttribute<DispResultado, Date> hora;
    public static volatile SingularAttribute<DispResultado, DispAgendamiento> idAgendamiento;
    public static volatile SingularAttribute<DispResultado, IsSector> idSector;
    public static volatile SingularAttribute<DispResultado, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispResultado, Integer> idResultado;
    public static volatile SingularAttribute<DispResultado, String> tratamiento;
    public static volatile SingularAttribute<DispResultado, String> motivoConsulta;
    public static volatile SingularAttribute<DispResultado, Date> fecha;
    public static volatile SingularAttribute<DispResultado, Date> fechaIngreso;
    public static volatile SingularAttribute<DispResultado, String> usuarioIngreso;
    public static volatile SingularAttribute<DispResultado, String> antecedentes;
    public static volatile SingularAttribute<DispResultado, String> tiempoEnfermedad;
    public static volatile SingularAttribute<DispResultado, String> usuarioModificacion;
    public static volatile SingularAttribute<DispResultado, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispResultado, String> examenFisico;
    public static volatile SingularAttribute<DispResultado, String> plan;

}