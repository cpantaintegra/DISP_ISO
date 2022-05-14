package com.Entity;

import com.Entity.DispAgendamiento;
import com.Entity.DispConsultorio;
import com.Entity.DispEspecialidad;
import com.Entity.DispSolicitudExamen;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import com.Entity.IsUsuarios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispMedicoPersonal.class)
public class DispMedicoPersonal_ { 

    public static volatile SingularAttribute<DispMedicoPersonal, String> numDocumento;
    public static volatile SingularAttribute<DispMedicoPersonal, String> estado;
    public static volatile SingularAttribute<DispMedicoPersonal, Date> fechaNacimiento;
    public static volatile SingularAttribute<DispMedicoPersonal, IsUsuarios> idUsuario;
    public static volatile SingularAttribute<DispMedicoPersonal, String> ocupacion;
    public static volatile SingularAttribute<DispMedicoPersonal, IsSector> idSector;
    public static volatile SingularAttribute<DispMedicoPersonal, String> nombre;
    public static volatile SingularAttribute<DispMedicoPersonal, IsCiudad> idCiudad;
    public static volatile CollectionAttribute<DispMedicoPersonal, DispAgendamiento> dispAgendamientoCollection;
    public static volatile SingularAttribute<DispMedicoPersonal, String> usuarioIngreso;
    public static volatile SingularAttribute<DispMedicoPersonal, String> celular;
    public static volatile SingularAttribute<DispMedicoPersonal, String> usuarioModificacion;
    public static volatile SingularAttribute<DispMedicoPersonal, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispMedicoPersonal, String> telefono;
    public static volatile SingularAttribute<DispMedicoPersonal, Integer> idMedicoPersonal;
    public static volatile SingularAttribute<DispMedicoPersonal, String> email;
    public static volatile SingularAttribute<DispMedicoPersonal, Date> fechaModificacion;
    public static volatile SingularAttribute<DispMedicoPersonal, DispConsultorio> idConsultorio;
    public static volatile CollectionAttribute<DispMedicoPersonal, DispSolicitudExamen> dispSolicitudExamenCollection;
    public static volatile SingularAttribute<DispMedicoPersonal, String> direccion;
    public static volatile SingularAttribute<DispMedicoPersonal, String> estadoCivil;
    public static volatile SingularAttribute<DispMedicoPersonal, String> tipoDocumento;
    public static volatile SingularAttribute<DispMedicoPersonal, Date> fechaIngreso;
    public static volatile SingularAttribute<DispMedicoPersonal, String> apaterno;
    public static volatile SingularAttribute<DispMedicoPersonal, String> amaterno;
    public static volatile SingularAttribute<DispMedicoPersonal, String> sexo;
    public static volatile SingularAttribute<DispMedicoPersonal, DispEspecialidad> idEspecialidad;

}