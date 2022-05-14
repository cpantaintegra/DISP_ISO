package com.Entity;

import com.Entity.DispAgendamiento;
import com.Entity.DispAntecedentes;
import com.Entity.DispCliente;
import com.Entity.DispConsultorio;
import com.Entity.DispDetalleReceta;
import com.Entity.DispDiagnostico;
import com.Entity.DispEspecialidad;
import com.Entity.DispEstudiosMedicos;
import com.Entity.DispExamen;
import com.Entity.DispFactura;
import com.Entity.DispMedicamento;
import com.Entity.DispMedicoPersonal;
import com.Entity.DispOrigen;
import com.Entity.DispPrecio;
import com.Entity.DispReceta;
import com.Entity.DispResultado;
import com.Entity.DispResultadoDetalle;
import com.Entity.DispResultadoExamen;
import com.Entity.DispServicio;
import com.Entity.DispSolicitudExamen;
import com.Entity.DispUnidadMedica;
import com.Entity.IsArea;
import com.Entity.IsEmpresa;
import com.Entity.IsRoles;
import com.Entity.IsUsuarios;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.CollectionAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.1.v20130824-rNA", date="2022-05-12T22:57:53")
@StaticMetamodel(IsCiudad.class)
public class IsCiudad_ { 

    public static volatile CollectionAttribute<IsCiudad, IsEmpresa> isEmpresaCollection;
    public static volatile SingularAttribute<IsCiudad, String> estado;
    public static volatile CollectionAttribute<IsCiudad, IsArea> isAreaCollection;
    public static volatile CollectionAttribute<IsCiudad, DispServicio> dispServicioCollection;
    public static volatile CollectionAttribute<IsCiudad, DispResultado> dispResultadoCollection;
    public static volatile CollectionAttribute<IsCiudad, DispDetalleReceta> dispDetalleRecetaCollection;
    public static volatile CollectionAttribute<IsCiudad, DispUnidadMedica> dispUnidadMedicaCollection;
    public static volatile SingularAttribute<IsCiudad, String> nombre;
    public static volatile CollectionAttribute<IsCiudad, IsUsuarios> isUsuariosCollection;
    public static volatile SingularAttribute<IsCiudad, Integer> idCiudad;
    public static volatile CollectionAttribute<IsCiudad, DispResultadoExamen> dispResultadoExamenCollection;
    public static volatile CollectionAttribute<IsCiudad, DispFactura> dispFacturaCollection;
    public static volatile CollectionAttribute<IsCiudad, DispEspecialidad> dispEspecialidadCollection;
    public static volatile CollectionAttribute<IsCiudad, DispOrigen> dispOrigenCollection;
    public static volatile CollectionAttribute<IsCiudad, DispAgendamiento> dispAgendamientoCollection;
    public static volatile SingularAttribute<IsCiudad, String> usuarioIngreso;
    public static volatile CollectionAttribute<IsCiudad, DispConsultorio> dispConsultorioCollection;
    public static volatile CollectionAttribute<IsCiudad, DispMedicamento> dispMedicamentoCollection;
    public static volatile CollectionAttribute<IsCiudad, DispDiagnostico> dispDiagnosticoCollection;
    public static volatile SingularAttribute<IsCiudad, String> usuarioModificacion;
    public static volatile CollectionAttribute<IsCiudad, DispExamen> dispExamenCollection;
    public static volatile CollectionAttribute<IsCiudad, DispResultadoDetalle> dispResultadoDetalleCollection;
    public static volatile CollectionAttribute<IsCiudad, DispEstudiosMedicos> dispEstudiosMedicosCollection;
    public static volatile CollectionAttribute<IsCiudad, DispReceta> dispRecetaCollection;
    public static volatile CollectionAttribute<IsCiudad, DispAntecedentes> dispAntecedentesCollection;
    public static volatile SingularAttribute<IsCiudad, Date> fechaModificacion;
    public static volatile CollectionAttribute<IsCiudad, DispMedicoPersonal> dispMedicoPersonalCollection;
    public static volatile CollectionAttribute<IsCiudad, DispSolicitudExamen> dispSolicitudExamenCollection;
    public static volatile SingularAttribute<IsCiudad, String> detalle;
    public static volatile SingularAttribute<IsCiudad, Date> fechaIngreso;
    public static volatile CollectionAttribute<IsCiudad, IsRoles> isRolesCollection;
    public static volatile CollectionAttribute<IsCiudad, DispPrecio> dispPrecioCollection;
    public static volatile CollectionAttribute<IsCiudad, DispCliente> dispClienteCollection;

}