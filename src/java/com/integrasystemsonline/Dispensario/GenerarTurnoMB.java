package com.integrasystemsonline.Dispensario;

import com.Entity.DispEspecialidad;
import com.Entity.IsRolesPermisos;
import com.Entity.IsUsuarios;
import com.Session.DispEspecialidadFacade;
import com.Session.IsRolesPermisosFacade;
import com.integrasystemsonline.Process.TablaDinamica;
import com.integrasystemsonline.Utilidades.Estado;
import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import javax.ejb.EJB;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Named;
import javax.transaction.UserTransaction;
import org.primefaces.component.datatable.DataTable;

@Named("generarTurnoMB")
@ViewScoped
public class GenerarTurnoMB implements Serializable {

    @Resource
    UserTransaction userTransaction;

    @EJB
    private DispEspecialidadFacade dispEspecialidadFacade;

    @EJB
    IsRolesPermisosFacade isRolesPermisosFacade;

    DataTable table = new DataTable();

    IsUsuarios usuario;

    List<String> lstEspecialidad = new ArrayList<>();

    private String estado;

    private String labelMant;

    private List<Estado> listaEstado;

    private Estado estadoObj;

    private List<IsRolesPermisos> listIsRolesPermisos;

    private boolean editar = true, eliminarBl = true, consultar = true, ingresar = true;

    @PostConstruct
    public void ini() {
        try {
            this.estado = "A";
            this.labelMant = "Ingresar";
            this.listaEstado = new ArrayList<>();
            Estado estado = new Estado();
            estado.setValor("A");
            estado.setDetalle("Activo");
            this.estadoObj = estado;
            this.listaEstado.add(estado);
            estado = new Estado();
            estado.setValor("I");
            estado.setDetalle("Inactivo");
            this.listaEstado.add(estado);
            this.usuario = (IsUsuarios) FacesContext.getCurrentInstance().getExternalContext().getSessionMap().get("usuario");
            this.listIsRolesPermisos = this.isRolesPermisosFacade.findByRol(this.usuario.getIdRoles());
            String eli = "", edi = "", con = "", ing = "";
            Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");
            try {
                eli = propParam.getProperty("eliminar");
                edi = propParam.getProperty("editar");
                con = propParam.getProperty("consultar");
                ing = propParam.getProperty("ingresar");
            } catch (Exception exception) {
            }
            for (IsRolesPermisos isRolesPermisos : this.listIsRolesPermisos) {
                if (isRolesPermisos.getIdPermisos().getNombre().equals(edi)) {
                    this.editar = false;
                    continue;
                }
                if (isRolesPermisos.getIdPermisos().getNombre().equals(eli)) {
                    this.eliminarBl = false;
                    continue;
                }
                if (isRolesPermisos.getIdPermisos().getNombre().equals(con)) {
                    this.consultar = false;
                    continue;
                }
                if (isRolesPermisos.getIdPermisos().getNombre().equals(ing)) {
                    this.ingresar = false;
                }
            }
            generarTabla();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generarTabla() {
        List<DispEspecialidad> lstEspecialidades = this.dispEspecialidadFacade.findAllActivos(this.usuario.getIdEmpresa().getIdEmpresa(), this.usuario.getIdCiudad().getIdCiudad(), this.usuario.getIdSector().getIdSector());
        for (int i = 0; i < lstEspecialidades.size(); i++) {
            this.lstEspecialidad.add(((DispEspecialidad) lstEspecialidades.get(i)).getNombre());
        }
        TablaDinamica myTable = new TablaDinamica();
        myTable.TablaNueva(3, this.lstEspecialidad);
        this.table = myTable.getMyDataTable();
    }

    public DataTable getTable() {
        return this.table;
    }

    public void setTable(DataTable table) {
        this.table = table;
    }

    public List<String> getLstEspecialidad() {
        return this.lstEspecialidad;
    }

    public void setLstEspecialidad(List<String> lstEspecialidad) {
        this.lstEspecialidad = lstEspecialidad;
    }
}
