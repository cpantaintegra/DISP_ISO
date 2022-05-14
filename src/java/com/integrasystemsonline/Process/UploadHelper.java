package com.integrasystemsonline.Process;

import com.integrasystemsonline.Utilidades.Utilidades;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Properties;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.Part;

public class UploadHelper {

    private final int limit_max_size = 1024000;

    private final String limit_type_file = "xls|xlsx";

    Properties propParam = Utilidades.obtenerProperties("MessageResources.properties");

    private final String path_to = this.propParam.getProperty("rutaArchivo");

    public String proccessUpload(Part fileUpload) {
        String fileSaveData = "no file";
        try {
            if (fileUpload.getSize() > 0L) {
                String sumittedFileName = getFileName(fileUpload);
                if (checkFileType(sumittedFileName)) {
                    getClass();
                    if (fileUpload.getSize() > 1024000L) {
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "File size too large", ""));
                    } else {
                        String currentFileName = sumittedFileName;
                        String extension = currentFileName.substring(currentFileName.lastIndexOf("."), currentFileName.length());
                        Long nameRandom = Calendar.getInstance().getTimeInMillis();
                        String newFileName = nameRandom + extension;
                        fileSaveData = newFileName;
                        String fileSavePath = FacesContext.getCurrentInstance().getExternalContext().getRealPath(this.path_to);
                        try {
                            byte[] fileContent = new byte[(int) fileUpload.getSize()];
                            InputStream in = fileUpload.getInputStream();
                            in.read(fileContent);
                            File fileToCreate = new File(fileSavePath, newFileName);
                            File folder = new File(fileSavePath);
                            if (!folder.exists()) {
                                folder.mkdirs();
                            }
                            FileOutputStream fileOutStream = new FileOutputStream(fileToCreate);
                            fileOutStream.write(fileContent);
                            fileOutStream.flush();
                            fileOutStream.close();
                            fileSaveData = newFileName;
                        } catch (IOException e) {
                            fileSaveData = "";
                        }
                    }
                } else {
                    fileSaveData = "";
                }
            }
        } catch (Exception e) {
            fileSaveData = "";
        }
        return fileSaveData;
    }

    private String getFileName(Part part) {
        for (String cd : part.getHeader("content-disposition").split(";")) {
            if (cd.trim().startsWith("filename")) {
                String filename = cd.substring(cd.indexOf("-") + 1).trim().replace("\"", "");
                return filename.substring(filename.lastIndexOf("/") + 1).substring(filename.lastIndexOf('\\') + 1);
            }
        }
        return null;
    }

    private boolean checkFileType(String fileName) {
        if (fileName.length() > 0) {
            String[] parts = fileName.split("\\.");
            if (parts.length > 0) {
                String extension = parts[parts.length - 1];
                getClass();
                return "xls|xlsx".contains(extension);
            }
        }
        return false;
    }
}
