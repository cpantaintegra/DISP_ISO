package com.Entity;

import com.Entity.DispCliente;
import com.Entity.DispPrecio;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispOrigen.class)
public class DispOrigen_ { 

    public static volatile SingularAttribute<DispOrigen, String> ruc;
    public static volatile SingularAttribute<DispOrigen, String> estado;
    public static volatile SingularAttribute<DispOrigen, Date> fechaModificacion;
    public static volatile SingularAttribute<DispOrigen, String> direccion;
    public static volatile SingularAttribute<DispOrigen, IsSector> idSector;
    public static volatile SingularAttribute<DispOrigen, String> nombre;
    public static volatile SingularAttribute<DispOrigen, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispOrigen, String> detalle;
    public static volatile SingularAttribute<DispOrigen, Date> fechaIngreso;
    public static volatile SingularAttribute<DispOrigen, String> nombreJuridico;
    public static volatile SingularAttribute<DispOrigen, Integer> idOrigen;
    public static volatile SingularAttribute<DispOrigen, String> usuarioIngreso;
    public static volatile SingularAttribute<DispOrigen, String> telefono1;
    public static volatile SingularAttribute<DispOrigen, String> telefono2;
    public static volatile SingularAttribute<DispOrigen, String> usuarioModificacion;
    public static volatile SingularAttribute<DispOrigen, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispOrigen, String> telefono3;
    public static volatile CollectionAttribute<DispOrigen, DispPrecio> dispPrecioCollection;
    public static volatile CollectionAttribute<DispOrigen, DispCliente> dispClienteCollection;

}