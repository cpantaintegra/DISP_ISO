package com.Entity;

import com.Entity.DispDetalleReceta;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispMedicamento.class)
public class DispMedicamento_ { 

    public static volatile SingularAttribute<DispMedicamento, Integer> idMedicamento;
    public static volatile SingularAttribute<DispMedicamento, String> descripcion;
    public static volatile SingularAttribute<DispMedicamento, Date> fechaIngreso;
    public static volatile SingularAttribute<DispMedicamento, String> estado;
    public static volatile SingularAttribute<DispMedicamento, Date> fechaModificacion;
    public static volatile SingularAttribute<DispMedicamento, String> usuarioIngreso;
    public static volatile CollectionAttribute<DispMedicamento, DispDetalleReceta> dispDetalleRecetaCollection;
    public static volatile SingularAttribute<DispMedicamento, String> usuarioModificacion;
    public static volatile SingularAttribute<DispMedicamento, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispMedicamento, IsSector> idSector;
    public static volatile SingularAttribute<DispMedicamento, String> nombre;
    public static volatile SingularAttribute<DispMedicamento, IsCiudad> idCiudad;

}