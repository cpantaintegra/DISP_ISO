package com.Entity;

import com.Entity.DispMedicoPersonal;
import com.Entity.IsArea;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsRoles;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(IsUsuarios.class)
public class IsUsuarios_ { 

    public static volatile SingularAttribute<IsUsuarios, String> telefonoCel;
    public static volatile SingularAttribute<IsUsuarios, String> estado;
    public static volatile SingularAttribute<IsUsuarios, String> tipoPersona;
    public static volatile SingularAttribute<IsUsuarios, IsArea> idArea;
    public static volatile SingularAttribute<IsUsuarios, String> cedula;
    public static volatile SingularAttribute<IsUsuarios, IsSector> idSector;
    public static volatile SingularAttribute<IsUsuarios, String> direccionDom;
    public static volatile SingularAttribute<IsUsuarios, IsCiudad> idCiudad;
    public static volatile SingularAttribute<IsUsuarios, String> nombres;
    public static volatile SingularAttribute<IsUsuarios, String> usuarioIngreso;
    public static volatile SingularAttribute<IsUsuarios, String> correo;
    public static volatile SingularAttribute<IsUsuarios, String> logo;
    public static volatile SingularAttribute<IsUsuarios, String> usuarioModificacion;
    public static volatile SingularAttribute<IsUsuarios, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<IsUsuarios, String> apellidos;
    public static volatile SingularAttribute<IsUsuarios, String> clave;
    public static volatile SingularAttribute<IsUsuarios, String> logoRuta;
    public static volatile SingularAttribute<IsUsuarios, Date> fechaModificacion;
    public static volatile CollectionAttribute<IsUsuarios, DispMedicoPersonal> dispMedicoPersonalCollection;
    public static volatile SingularAttribute<IsUsuarios, IsRoles> idRoles;
    public static volatile SingularAttribute<IsUsuarios, String> detalle;
    public static volatile SingularAttribute<IsUsuarios, String> telefonoCasa;
    public static volatile SingularAttribute<IsUsuarios, Date> fechaIngreso;
    public static volatile SingularAttribute<IsUsuarios, String> usuario;
    public static volatile SingularAttribute<IsUsuarios, Integer> idUsuarios;
    public static volatile SingularAttribute<IsUsuarios, Integer> intentos;

}