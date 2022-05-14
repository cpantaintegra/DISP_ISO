package com.integrasystemsonline.Process;

import java.io.InputStream;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.servlet.ServletContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

@ManagedBean
public class FileDownloadView {
  private StreamedContent file;
  
  public FileDownloadView() {
    InputStream stream = FacesContext.getCurrentInstance().getExternalContext().getResourceAsStream("/archivosTemp/fileGenerado.txt");
    ServletContext servletContext = (ServletContext)FacesContext.getCurrentInstance().getExternalContext().getContext();
    String resHomeImgPath = servletContext.getRealPath("/archivosTemp");
    System.out.println(resHomeImgPath);
    this.file = (StreamedContent)new DefaultStreamedContent(stream, "application/txt", "fileGenerado.txt");
  }
  
  public StreamedContent getFile() {
    return this.file;
  }
}