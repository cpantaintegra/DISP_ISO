package com.integrasystemsonline.Utilidades;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.Callable;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

public class SMTPConfig {

    public synchronized boolean sendMail(String titulo, String mensaje, String paraEmail) {
        boolean envio = false;
        try {
            final Properties props = Utilidades.obtenerProperties("smtp.properties");
            Properties propiedades = new Properties();
            propiedades.put("mail.smtp.host", props.getProperty("mail.smtp.host"));
            propiedades.put("mail.smtp.starttls.enable", "true");
            propiedades.put("mail.smtp.auth", props.getProperty("mail.smtp.auth"));
            propiedades.put("mail.smtp.port", props.getProperty("mail.smtp.port"));
            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(props.getProperty("mail.smtp.mail.sender"), props.getProperty("mail.password"));
                }
            });
            try {
                MimeMessage message = new MimeMessage(session);
                message.setSender((Address) new InternetAddress(props.getProperty("mail.email")));
                message.setSubject(titulo);
                message.setContent(mensaje, "text/html; charset=utf-8");
                message.setFrom((Address) new InternetAddress(props
                        .getProperty("mail.smtp.mail.sender")));
                message.setReplyTo((Address[]) InternetAddress.parse(props
                        .getProperty("mail.smtp.mail.sender")));
                if (paraEmail.indexOf(',') > 0) {
                    message.setRecipients(Message.RecipientType.TO,
                            (Address[]) InternetAddress.parse(paraEmail));
                } else {
                    message.setRecipient(Message.RecipientType.TO, (Address) new InternetAddress(paraEmail));
                }
                Transport.send((Message) message);
                envio = true;
            } catch (MessagingException e) {
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            envio = false;
            e.printStackTrace();
        }
        return envio;
    }

    public static synchronized boolean sendMail(String titulo, String mensaje, String paraEmail, String copiaEmail, String user, String clave, String autent) {
        boolean envio = false;
        try {
            Properties props = Utilidades.obtenerProperties("smtp.properties");
            Properties propiedades = new Properties();
            propiedades.put("mail.transport.protocol", props.getProperty("mail.transport.protocol"));
            propiedades.put("mail.smtp.host", props.getProperty("mail.smtp.host"));
            propiedades.put("mail.smtp.auth", props.getProperty("mail.smtp.auth"));
            propiedades.put("mail.smtp.port", props.getProperty("mail.smtp.port"));
            propiedades.put("mail.smtp.socketFactory.port", props.getProperty("mail.smtp.socketFactory.port"));
            propiedades.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            propiedades.put("mail.debug", props.getProperty("mail.debug"));
            Session session = getSession(propiedades, props, Boolean.parseBoolean(props.getProperty("mail.smtp.auth")));
            MimeMessage message = new MimeMessage(session);
            message.setSender((Address) new InternetAddress(props.getProperty("mail.email")));
            message.setSubject(titulo);
            message.setContent(mensaje, "text/html; charset=utf-8");
            message.setFrom((Address) new InternetAddress(props.getProperty("mail.smtp.mail.sender")));
            message.setReplyTo((Address[]) InternetAddress.parse(props.getProperty("mail.smtp.mail.sender")));
            if (paraEmail.indexOf(',') > 0) {
                message.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(paraEmail));
            } else {
                message.setRecipient(Message.RecipientType.TO, (Address) new InternetAddress(paraEmail));
            }
            if (copiaEmail.indexOf(',') > 0) {
                message.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copiaEmail));
            } else {
                try {
                    message.setRecipient(Message.RecipientType.CC, (Address) new InternetAddress(copiaEmail));
                } catch (Exception exception) {
                }
            }
            Collection<Callable<Void>> tasks = new ArrayList<>();
            tasks.add(new MyTask("Transport.send(message)", 50000L));
            Transport.send((Message) message);
            envio = true;
        } catch (Exception e) {
            envio = false;
        }
        return envio;
    }

    public static Session getSession(Properties propiedades, final Properties props, boolean autenticacion) {
        Session session = null;
        if (autenticacion) {
            session = Session.getInstance(propiedades, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(props.getProperty("mail.user"), props.getProperty("mail.password"));
                }
            });
        } else {
            session = Session.getInstance(propiedades);
        }
        return session;
    }

    public synchronized boolean sendMail(String titulo, String mensaje, String paraEmail, String copiaEmail, File file, String user, String clave, String autent) {
        boolean envio = false;
        try {
            Properties props = Utilidades.obtenerProperties("smtp.properties");
            Properties propiedades = new Properties();
            propiedades.put("mail.transport.protocol", props.getProperty("mail.transport.protocol"));
            propiedades.put("mail.smtp.host", props.getProperty("mail.smtp.host"));
            propiedades.put("mail.smtp.auth", props.getProperty("mail.smtp.auth"));
            propiedades.put("mail.smtp.port", props.getProperty("mail.smtp.port"));
            propiedades.put("mail.smtp.socketFactory.port", props.getProperty("mail.smtp.socketFactory.port"));
            propiedades.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            propiedades.put("mail.debug", props.getProperty("mail.debug"));
            Session session = getSession(propiedades, props, Boolean.parseBoolean(props.getProperty("mail.smtp.auth")));
            MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
            mimeBodyPart1.setText(mensaje);
            MimeBodyPart mimeBodyPart2 = new MimeBodyPart();
            mimeBodyPart2.setDataHandler(new DataHandler(new FileDataSource(file)));
            mimeBodyPart2.setFileName(file.getName());
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart((BodyPart) mimeBodyPart1);
            multiParte.addBodyPart((BodyPart) mimeBodyPart2);
            MimeMessage message = new MimeMessage(session);
            message.setSender((Address) new InternetAddress(props.getProperty("mail.email")));
            message.setSubject(titulo);
            message.setContent(multiParte, "text/html");
            message.setFrom((Address) new InternetAddress(props.getProperty("mail.smtp.mail.sender")));
            message.setReplyTo((Address[]) InternetAddress.parse(props.getProperty("mail.smtp.mail.sender")));
            if (paraEmail.indexOf(',') > 0) {
                message.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(paraEmail));
            } else {
                message.setRecipient(Message.RecipientType.TO, (Address) new InternetAddress(paraEmail));
            }
            if (copiaEmail.indexOf(',') > 0) {
                message.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copiaEmail));
            } else {
                try {
                    message.setRecipient(Message.RecipientType.CC, (Address) new InternetAddress(copiaEmail));
                } catch (Exception exception) {
                }
            }
            Collection<Callable<Void>> tasks = new ArrayList<>();
            tasks.add(new MyTask("Transport.send(message)", 50000L));
            Transport.send((Message) message);
            envio = true;
        } catch (Exception e) {
            envio = false;
        }
        return envio;
    }

    public synchronized int sendMail(String titulo, String mensaje, String paraEmail, String copiaEmail, List<String> adjuntos, String user, String clave, String autent) {
        int envio = 0;
        try {
            Properties props = Utilidades.obtenerProperties("smtp.properties");
            Properties propiedades = new Properties();
            propiedades.put("mail.transport.protocol", props.getProperty("mail.transport.protocol"));
            propiedades.put("mail.smtp.host", props.getProperty("mail.smtp.host"));
            propiedades.put("mail.smtp.auth", props.getProperty("mail.smtp.auth"));
            propiedades.put("mail.smtp.port", props.getProperty("mail.smtp.port"));
            propiedades.put("mail.smtp.socketFactory.port", props.getProperty("mail.smtp.socketFactory.port"));
            propiedades.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            propiedades.put("mail.debug", props.getProperty("mail.debug"));
            propiedades.put("mail.smtp.timeout", props.getProperty("mail.smtp.timeout"));
            Session session = getSession(propiedades, props, Boolean.parseBoolean(props.getProperty("mail.smtp.auth")));
            MimeBodyPart mimeBodyPart = new MimeBodyPart();
            mimeBodyPart.setContent(mensaje, "text/html; charset=utf-8");
            BodyPart messageBodyPart = null;
            MimeMultipart multiParte = new MimeMultipart();
            multiParte.addBodyPart((BodyPart) mimeBodyPart);
            if (adjuntos != null && adjuntos.size() > 0) {
                for (String rutaAdjunto : adjuntos) {
                    MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
                    File f = new File(rutaAdjunto);
                    if (f.exists()) {
                        mimeBodyPart1.setDataHandler(new DataHandler(new FileDataSource(f)));
                        mimeBodyPart1.setFileName(f.getName());
                        multiParte.addBodyPart((BodyPart) mimeBodyPart1);
                    }
                }
            }
            MimeMessage message = new MimeMessage(session);
            message.setSender((Address) new InternetAddress(props.getProperty("mail.email")));
            message.setSubject(titulo);
            message.setContent((Multipart) multiParte);
            message.setFrom((Address) new InternetAddress(props.getProperty("mail.smtp.mail.sender")));
            message.setReplyTo((Address[]) InternetAddress.parse(props.getProperty("mail.smtp.mail.sender")));
            if (paraEmail.indexOf(',') > 0) {
                message.setRecipients(Message.RecipientType.TO, (Address[]) InternetAddress.parse(paraEmail));
            } else {
                message.setRecipient(Message.RecipientType.TO, (Address) new InternetAddress(paraEmail));
            }
            if (!copiaEmail.trim().equals("")) {
                if (copiaEmail.indexOf(',') > 0) {
                    message.setRecipients(Message.RecipientType.CC, (Address[]) InternetAddress.parse(copiaEmail));
                } else {
                    try {
                        message.setRecipient(Message.RecipientType.CC, (Address) new InternetAddress(copiaEmail));
                    } catch (Exception exception) {
                    }
                }
            }
            Transport.send((Message) message);
            envio = 0;
        } catch (MessagingException e) {
            if (e.getMessage().equals("Exception reading response")) {
                envio = 2;
            } else {
                envio = 1;
            }
        }
        return envio;
    }
}
