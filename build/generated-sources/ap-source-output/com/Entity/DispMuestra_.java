package com.Entity;

import com.Entity.DispTomaMuestra;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispMuestra.class)
public class DispMuestra_ { 

    public static volatile SingularAttribute<DispMuestra, String> descripcion;
    public static volatile SingularAttribute<DispMuestra, Integer> idMuestra;
    public static volatile SingularAttribute<DispMuestra, Date> fechaIngreso;
    public static volatile SingularAttribute<DispMuestra, String> estado;
    public static volatile CollectionAttribute<DispMuestra, DispTomaMuestra> dispTomaMuestraCollection;
    public static volatile SingularAttribute<DispMuestra, Date> fechaModificacion;
    public static volatile SingularAttribute<DispMuestra, String> usuarioIngreso;
    public static volatile SingularAttribute<DispMuestra, String> usuarioModificacion;
    public static volatile SingularAttribute<DispMuestra, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispMuestra, IsSector> idSector;
    public static volatile SingularAttribute<DispMuestra, String> nombre;
    public static volatile SingularAttribute<DispMuestra, IsCiudad> idCiudad;

}