package com.Entity;

import com.Entity.DispCliente;
import com.Entity.IsCiudad;
import com.Entity.IsEmpresa;
import com.Entity.IsSector;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(DispAntecedentes.class)
public class DispAntecedentes_ { 

    public static volatile SingularAttribute<DispAntecedentes, String> alcohol;
    public static volatile SingularAttribute<DispAntecedentes, String> estado;
    public static volatile SingularAttribute<DispAntecedentes, Date> fechaModificacion;
    public static volatile SingularAttribute<DispAntecedentes, Integer> interQuirurgicas;
    public static volatile SingularAttribute<DispAntecedentes, String> coagulacion;
    public static volatile SingularAttribute<DispAntecedentes, String> anestesicos;
    public static volatile SingularAttribute<DispAntecedentes, IsSector> idSector;
    public static volatile SingularAttribute<DispAntecedentes, Integer> idAntecedentes;
    public static volatile SingularAttribute<DispAntecedentes, String> medicamentos;
    public static volatile SingularAttribute<DispAntecedentes, IsCiudad> idCiudad;
    public static volatile SingularAttribute<DispAntecedentes, String> alergias;
    public static volatile SingularAttribute<DispAntecedentes, String> tabaco;
    public static volatile SingularAttribute<DispAntecedentes, String> anteFamiliares;
    public static volatile SingularAttribute<DispAntecedentes, Date> fechaIngreso;
    public static volatile SingularAttribute<DispAntecedentes, DispCliente> idCliente;
    public static volatile SingularAttribute<DispAntecedentes, String> anteOftalmologicos;
    public static volatile SingularAttribute<DispAntecedentes, String> usuarioIngreso;
    public static volatile SingularAttribute<DispAntecedentes, String> antePersonales;
    public static volatile SingularAttribute<DispAntecedentes, String> usuarioModificacion;
    public static volatile SingularAttribute<DispAntecedentes, IsEmpresa> idEmpresa;

}