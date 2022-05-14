package com.integrasystemsonline.Process;

import com.integrasystemsonline.Utilidades.MockDb;
import java.util.List;
import javax.el.ELContext;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.component.html.HtmlOutputText;
import javax.faces.context.FacesContext;
import javax.faces.event.AbortProcessingException;
import javax.faces.event.ActionEvent;
import javax.faces.event.ActionListener;
import org.primefaces.component.column.Column;
import org.primefaces.component.columngroup.ColumnGroup;
import org.primefaces.component.commandbutton.CommandButton;
import org.primefaces.component.datatable.DataTable;
import org.primefaces.component.panelgrid.PanelGrid;
import org.primefaces.component.row.Row;
import org.primefaces.event.SelectEvent;

public class TablaDinamica {

    private DataTable myDataTable;

    private PanelGrid panel;

    public DataTable NuevaTabla(int numCol, List<String> lstRow) {
        this.myDataTable = new DataTable();
        this.myDataTable.setValue(MockDb.getValueList(lstRow));
        this.myDataTable.setVar("mydata");
        ColumnGroup columnGroup = new ColumnGroup();
        this.myDataTable.getChildren().add(columnGroup);
        columnGroup.setType("header");
        List<String> headerList = lstRow;
        Row headerRow = new Row();
        columnGroup.getChildren().add(headerRow);
        int j = 0;
        Column column = new Column();
        column.setColspan(1);
        headerRow.getChildren().add(column);
        column.setHeaderText("");
        for (String header : headerList) {
            column = new Column();
            column.setColspan(1);
            headerRow.getChildren().add(column);
            column.setHeaderText(lstRow.get(j));
            j++;
        }
        FacesContext fc = FacesContext.getCurrentInstance();
        Application application = fc.getApplication();
        ExpressionFactory ef = application.getExpressionFactory();
        ELContext elc = fc.getELContext();
        Column dataColumn1 = new Column();
        ValueExpression valueExp = ef.createValueExpression(elc, "#{generarTurnoMB.lstEspecialidad.get(0)}", Object.class);
        HtmlOutputText output = (HtmlOutputText) application.createComponent("javax.faces.HtmlOutputText");
        output.setValueExpression("value", valueExp);
        dataColumn1.getChildren().add(output);
        this.myDataTable.getChildren().add(dataColumn1);
        for (int i = 0; i < numCol; i++) {
            Column dataColumn = new Column();
            CommandButton button = new CommandButton();
            button.setValue(lstRow.get(i));
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }

                @Override
                public void processAction(ActionEvent event) throws AbortProcessingException {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            });
            dataColumn.getChildren().add(button);
            this.myDataTable.getChildren().add(dataColumn);
        }
        return this.myDataTable;
    }

    public void actionPerformed(SelectEvent event) {
    }

    public void TablaNueva(int numCol, List<String> lstRow) {
        this.myDataTable = new DataTable();
        this.myDataTable.setStyle("width:100%; height: 800px; text-align: center;align-content: flex-start");
        this.myDataTable.setValue(MockDb.getValueList(lstRow));
        this.myDataTable.setVar("mydata");
        FacesContext fc = FacesContext.getCurrentInstance();
        Application application = fc.getApplication();
        ExpressionFactory ef = application.getExpressionFactory();
        ELContext elc = fc.getELContext();
        for (int i = 0; i < numCol; i++) {
            Column dataColumn = new Column();
            CommandButton button = new CommandButton();
            button.setValue(lstRow.get(i));
            button.setStyle("width:300px; height: 300px;background-color: green;font: 48px serif bold");
            button.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                }

                @Override
                public void processAction(ActionEvent event) throws AbortProcessingException {
                    throw new UnsupportedOperationException("Not supported yet.");
                }
            });
            dataColumn.getChildren().add(button);
            this.myDataTable.getChildren().add(dataColumn);
        }
    }

    public void generarListCamposEntidad(List<String> lstCol) {
        try {
            this.panel = new PanelGrid();
            this.panel.setColumns(lstCol.size() / 2);
            this.panel.setStyle("width:100%;text-align: center");
            for (int i = 0; i < lstCol.size(); i++) {
                CommandButton button = new CommandButton();
                button.setValue(lstCol.get(i));
                button.setUpdate(":forma");
                button.setTitle(lstCol.get(i));
                button.setStyle("width: 100%;");
                button.addActionListener(new ActionListener() {
                    @Override
                    public void processAction(ActionEvent event) {
                        try {
                            GenerarReporte.visibilidadColumna();
                        } catch (Exception exception) {
                        }
                    }
                });
                this.panel.getChildren().add(button);
            }
        } catch (Exception exception) {
        }
    }

    public DataTable getMyDataTable() {
        return this.myDataTable;
    }

    public void setMyDataTable(DataTable myDataTable) {
        this.myDataTable = myDataTable;
    }

    public PanelGrid getPanel() {
        return this.panel;
    }

    public void setPanel(PanelGrid panel) {
        this.panel = panel;
    }
}
