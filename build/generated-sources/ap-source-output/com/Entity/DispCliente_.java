package com.Entity;

import com.Entity.DispAgendamiento;
import com.Entity.DispAntecedentes;
import com.Entity.DispFactura;
import com.Entity.DispOrigen;
import com.Entity.DispSolicitudExamen;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispCliente.class)
public class DispCliente_ { 

    public static volatile SingularAttribute<DispCliente, String> numDocumento;
    public static volatile SingularAttribute<DispCliente, String> estado;
    public static volatile SingularAttribute<DispCliente, Date> fechaNacimiento;
    public static volatile SingularAttribute<DispCliente, String> ocupacion;
    public static volatile SingularAttribute<DispCliente, IsSector> idSector;
    public static volatile SingularAttribute<DispCliente, String> nombre;
    public static volatile SingularAttribute<DispCliente, IsCiudad> idCiudad;
    public static volatile CollectionAttribute<DispCliente, DispFactura> dispFacturaCollection;
    public static volatile SingularAttribute<DispCliente, Integer> idCliente;
    public static volatile CollectionAttribute<DispCliente, DispAgendamiento> dispAgendamientoCollection;
    public static volatile SingularAttribute<DispCliente, String> usuarioIngreso;
    public static volatile SingularAttribute<DispCliente, String> celular;
    public static volatile SingularAttribute<DispCliente, String> personaResponsable;
    public static volatile SingularAttribute<DispCliente, String> usuarioModificacion;
    public static volatile SingularAttribute<DispCliente, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispCliente, String> telefono;
    public static volatile SingularAttribute<DispCliente, String> email;
    public static volatile CollectionAttribute<DispCliente, DispAntecedentes> dispAntecedentesCollection;
    public static volatile SingularAttribute<DispCliente, Date> fechaModificacion;
    public static volatile CollectionAttribute<DispCliente, DispSolicitudExamen> dispSolicitudExamenCollection;
    public static volatile SingularAttribute<DispCliente, String> direccion;
    public static volatile SingularAttribute<DispCliente, String> estadoCivil;
    public static volatile SingularAttribute<DispCliente, String> tipoDocumento;
    public static volatile SingularAttribute<DispCliente, Date> fechaIngreso;
    public static volatile SingularAttribute<DispCliente, String> apaterno;
    public static volatile SingularAttribute<DispCliente, DispOrigen> idOrigen;
    public static volatile SingularAttribute<DispCliente, String> amaterno;
    public static volatile SingularAttribute<DispCliente, String> sexo;

}