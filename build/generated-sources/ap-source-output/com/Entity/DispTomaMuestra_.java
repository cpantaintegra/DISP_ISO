package com.Entity;

import com.Entity.DispMuestra;
import com.Entity.DispSolicitudExamen;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispTomaMuestra.class)
public class DispTomaMuestra_ { 

    public static volatile SingularAttribute<DispTomaMuestra, DispMuestra> idMuestra;
    public static volatile SingularAttribute<DispTomaMuestra, String> estado;
    public static volatile SingularAttribute<DispTomaMuestra, Date> fechaModificacion;
    public static volatile SingularAttribute<DispTomaMuestra, Integer> idTomaMuestra;
    public static volatile SingularAttribute<DispTomaMuestra, IsSector> idSector;
    public static volatile SingularAttribute<DispTomaMuestra, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispTomaMuestra, Date> fecha;
    public static volatile SingularAttribute<DispTomaMuestra, Date> fechaIngreso;
    public static volatile SingularAttribute<DispTomaMuestra, String> usuarioIngreso;
    public static volatile SingularAttribute<DispTomaMuestra, String> usuarioModificacion;
    public static volatile SingularAttribute<DispTomaMuestra, IsEmpresa> idEmpresa;
    public static volatile SingularAttribute<DispTomaMuestra, Integer> cantidad;
    public static volatile SingularAttribute<DispTomaMuestra, DispSolicitudExamen> idSolicitudExamen;

}