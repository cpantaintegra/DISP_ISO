package com.integrasystemsonline.Utilidades;

import java.util.Date;
import java.util.Map;
import org.primefaces.model.ScheduleEvent;
import org.primefaces.model.ScheduleRenderingMode;

public class CustomScheduleEvent implements ScheduleEvent {
  private String id;
  
  private String title;
  
  private Date startDate;
  
  private Date endDate;
  
  private String styleClass;
  
  private Object data;
  
  private String url;
  
  private String description;
  
  private boolean allDay;
  
  private boolean editable;
  
  private ScheduleRenderingMode renderingMode;
  
  public CustomScheduleEvent() {}
  
  public CustomScheduleEvent(String title, Date start, Date end, boolean allDay, Object data) {
    this.title = title;
    this.startDate = start;
    this.endDate = end;
    this.allDay = allDay;
    this.data = data;
  }
  
  public CustomScheduleEvent(String title, Date start, Date end, String styleClass, boolean allDay, Object data) {
    this.title = title;
    this.startDate = start;
    this.endDate = end;
    this.styleClass = styleClass;
    this.allDay = allDay;
    this.data = data;
  }
  
  @Override
  public String getId() {
    return this.id;
  }
  
  @Override
  public void setId(String id) {
    this.id = id;
  }
  
  @Override
  public String getTitle() {
    return this.title;
  }
  
  public void setTitle(String title) {
    this.title = title;
  }
  
  @Override
  public Date getStartDate() {
    return this.startDate;
  }
  
  public void setStartDate(Date startDate) {
    this.startDate = startDate;
  }
  
  @Override
  public Date getEndDate() {
    return this.endDate;
  }
  
  public void setEndDate(Date endDate) {
    this.endDate = endDate;
  }
  
  @Override
  public boolean isAllDay() {
    return this.allDay;
  }
  
  public void setAllDay(boolean allDay) {
    this.allDay = allDay;
  }
  
  @Override
  public String getStyleClass() {
    return this.styleClass;
  }
  
  public void setStyleClass(String styleClass) {
    this.styleClass = styleClass;
  }
  
  @Override
  public Object getData() {
    return this.data;
  }
  
  public void setData(Object data) {
    this.data = data;
  }
  
  @Override
  public String getUrl() {
    return this.url;
  }
  
  public void setUrl(String url) {
    this.url = url;
  }
  
  @Override
  public String getDescription() {
    return this.description;
  }
  
  public void setDescription(String description) {
    this.description = description;
  }
  
  @Override
  public boolean isEditable() {
    return this.editable;
  }
  
  public void setEditable(boolean editable) {
    this.editable = editable;
  }
  
  @Override
  public ScheduleRenderingMode getRenderingMode() {
    return this.renderingMode;
  }
  
  public void setRenderingMode(ScheduleRenderingMode renderingMode) {
    this.renderingMode = renderingMode;
  }
  
  @Override
  public Map<String, Object> getDynamicProperties() {
    throw new UnsupportedOperationException("Not supported yet.");
  }
}
