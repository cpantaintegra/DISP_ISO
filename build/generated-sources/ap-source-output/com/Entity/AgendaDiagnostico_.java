package com.Entity;

import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(AgendaDiagnostico.class)
public class AgendaDiagnostico_ { 

    public static volatile SingularAttribute<AgendaDiagnostico, Date> fechaInicioMedico;
    public static volatile SingularAttribute<AgendaDiagnostico, Date> fechaLlamada;
    public static volatile SingularAttribute<AgendaDiagnostico, Date> fechaInicioAtencion;
    public static volatile SingularAttribute<AgendaDiagnostico, String> estado;
    public static volatile SingularAttribute<AgendaDiagnostico, String> servicio;
    public static volatile SingularAttribute<AgendaDiagnostico, String> especialidad;
    public static volatile SingularAttribute<AgendaDiagnostico, Integer> id_agendamiento;
    public static volatile SingularAttribute<AgendaDiagnostico, String> nombPaciente;
    public static volatile SingularAttribute<AgendaDiagnostico, String> motivoConsulta;
    public static volatile SingularAttribute<AgendaDiagnostico, Date> fecha;
    public static volatile SingularAttribute<AgendaDiagnostico, Date> fechaAtendidoMedico;
    public static volatile SingularAttribute<AgendaDiagnostico, String> diagnostico;
    public static volatile SingularAttribute<AgendaDiagnostico, Date> fechaAtendido;
    public static volatile SingularAttribute<AgendaDiagnostico, Date> fechaLlamarMedico;

}