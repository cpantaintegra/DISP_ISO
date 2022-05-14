package com.Entity;

import com.Entity.DispCliente;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.math.BigDecimal;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispFactura.class)
public class DispFactura_ { 

    public static volatile SingularAttribute<DispFactura, String> estado;
    public static volatile SingularAttribute<DispFactura, Date> fechaModificacion;
    public static volatile SingularAttribute<DispFactura, BigDecimal> descuento;
    public static volatile SingularAttribute<DispFactura, IsSector> idSector;
    public static volatile SingularAttribute<DispFactura, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispFactura, Date> fecha;
    public static volatile SingularAttribute<DispFactura, Date> fechaIngreso;
    public static volatile SingularAttribute<DispFactura, BigDecimal> total;
    public static volatile SingularAttribute<DispFactura, DispCliente> idCliente;
    public static volatile SingularAttribute<DispFactura, BigDecimal> iva;
    public static volatile SingularAttribute<DispFactura, String> usuarioIngreso;
    public static volatile SingularAttribute<DispFactura, Integer> idFactura;
    public static volatile SingularAttribute<DispFactura, String> usuarioModificacion;
    public static volatile SingularAttribute<DispFactura, IsEmpresa> idEmpresa;

}