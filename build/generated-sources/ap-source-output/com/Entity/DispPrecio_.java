package com.Entity;

import com.Entity.DispOrigen;
import com.Entity.DispServicio;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispPrecio.class)
public class DispPrecio_ { 

    public static volatile SingularAttribute<DispPrecio, String> estado;
    public static volatile SingularAttribute<DispPrecio, Date> fechaModificacion;
    public static volatile SingularAttribute<DispPrecio, Integer> idPrecio;
    public static volatile SingularAttribute<DispPrecio, BigDecimal> valor;
    public static volatile SingularAttribute<DispPrecio, IsSector> idSector;
    public static volatile SingularAttribute<DispPrecio, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispPrecio, String> detalle;
    public static volatile SingularAttribute<DispPrecio, Date> fechaIngreso;
    public static volatile SingularAttribute<DispPrecio, DispOrigen> idOrigen;
    public static volatile SingularAttribute<DispPrecio, String> usuarioIngreso;
    public static volatile SingularAttribute<DispPrecio, String> usuarioModificacion;
    public static volatile SingularAttribute<DispPrecio, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispPrecio, DispServicio> idServicio;

}