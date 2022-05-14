package com.Entity;

import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispDiagnostico.class)
public class DispDiagnostico_ { 

    public static volatile SingularAttribute<DispDiagnostico, Date> fechaIngreso;
    public static volatile SingularAttribute<DispDiagnostico, String> codigo;
    public static volatile SingularAttribute<DispDiagnostico, String> estado;
    public static volatile SingularAttribute<DispDiagnostico, String> enfermedad;
    public static volatile SingularAttribute<DispDiagnostico, Date> fechaModificacion;
    public static volatile SingularAttribute<DispDiagnostico, String> usuarioIngreso;
    public static volatile SingularAttribute<DispDiagnostico, Integer> idDiagnostico;
    public static volatile SingularAttribute<DispDiagnostico, String> usuarioModificacion;
    public static volatile SingularAttribute<DispDiagnostico, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispDiagnostico, IsSector> idSector;
    public static volatile SingularAttribute<DispDiagnostico, IsCiudad> idCiudad;

}