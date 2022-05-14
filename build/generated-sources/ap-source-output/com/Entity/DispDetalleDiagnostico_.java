package com.Entity;

import com.Entity.DispCliente;
import com.Entity.DispDiagnostico;
import com.Entity.DispMedicoPersonal;
import com.Entity.DispResultado;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispDetalleDiagnostico.class)
public class DispDetalleDiagnostico_ { 

    public static volatile SingularAttribute<DispDetalleDiagnostico, String> tipo;
    public static volatile SingularAttribute<DispDetalleDiagnostico, String> estado;
    public static volatile SingularAttribute<DispDetalleDiagnostico, Date> fechaModificacion;
    public static volatile SingularAttribute<DispDetalleDiagnostico, Integer> idImagen;
    public static volatile SingularAttribute<DispDetalleDiagnostico, DispDiagnostico> idDiagnostico;
    public static volatile SingularAttribute<DispDetalleDiagnostico, Integer> idDetalleDiagnostico;
    public static volatile SingularAttribute<DispDetalleDiagnostico, IsSector> idSector;
    public static volatile SingularAttribute<DispDetalleDiagnostico, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispDetalleDiagnostico, DispResultado> idResultado;
    public static volatile SingularAttribute<DispDetalleDiagnostico, Date> fechaIngreso;
    public static volatile SingularAttribute<DispDetalleDiagnostico, DispCliente> idCliente;
    public static volatile SingularAttribute<DispDetalleDiagnostico, String> usuarioIngreso;
    public static volatile SingularAttribute<DispDetalleDiagnostico, String> usuarioModificacion;
    public static volatile SingularAttribute<DispDetalleDiagnostico, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispDetalleDiagnostico, DispMedicoPersonal> idMedicoPersonal;

}