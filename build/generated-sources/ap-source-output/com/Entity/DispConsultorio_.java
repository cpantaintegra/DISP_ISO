package com.Entity;

import com.Entity.DispEspecialidad;
import com.Entity.DispMedicoPersonal;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispConsultorio.class)
public class DispConsultorio_ { 

    public static volatile SingularAttribute<DispConsultorio, String> codigo;
    public static volatile SingularAttribute<DispConsultorio, String> estado;
    public static volatile SingularAttribute<DispConsultorio, Date> fechaModificacion;
    public static volatile CollectionAttribute<DispConsultorio, DispMedicoPersonal> dispMedicoPersonalCollection;
    public static volatile SingularAttribute<DispConsultorio, Integer> idConsultorio;
    public static volatile SingularAttribute<DispConsultorio, IsSector> idSector;
    public static volatile SingularAttribute<DispConsultorio, String> nombre;
    public static volatile SingularAttribute<DispConsultorio, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispConsultorio, String> detalle;
    public static volatile SingularAttribute<DispConsultorio, Date> fechaIngreso;
    public static volatile SingularAttribute<DispConsultorio, String> usuarioIngreso;
    public static volatile SingularAttribute<DispConsultorio, String> usuarioModificacion;
    public static volatile SingularAttribute<DispConsultorio, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispConsultorio, DispEspecialidad> idEspecialidad;

}