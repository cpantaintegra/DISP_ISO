package com.Entity;

import com.Entity.DispMedicamento;
import com.Entity.DispReceta;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispDetalleReceta.class)
public class DispDetalleReceta_ { 

    public static volatile SingularAttribute<DispDetalleReceta, DispMedicamento> idMedicamento;
    public static volatile SingularAttribute<DispDetalleReceta, String> estado;
    public static volatile SingularAttribute<DispDetalleReceta, Date> fechaModificacion;
    public static volatile SingularAttribute<DispDetalleReceta, Integer> idDetalleReceta;
    public static volatile SingularAttribute<DispDetalleReceta, String> dosis;
    public static volatile SingularAttribute<DispDetalleReceta, DispReceta> idReceta;
    public static volatile SingularAttribute<DispDetalleReceta, IsSector> idSector;
    public static volatile SingularAttribute<DispDetalleReceta, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispDetalleReceta, Date> fechaIngreso;
    public static volatile SingularAttribute<DispDetalleReceta, String> usuarioIngreso;
    public static volatile SingularAttribute<DispDetalleReceta, String> duracion;
    public static volatile SingularAttribute<DispDetalleReceta, String> usuarioModificacion;
    public static volatile SingularAttribute<DispDetalleReceta, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispDetalleReceta, Integer> cantidad;

}