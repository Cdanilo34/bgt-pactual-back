package com.btg.pactual.business.impl;

import com.btg.pactual.business.NotificacionService;
import com.btg.pactual.model.Cliente;
import com.btg.pactual.model.Fondo;
import com.btg.pactual.model.Notificacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class NotificacionServiceImpl implements NotificacionService {

    private final JavaMailSender mailSender;

    @Autowired
    public NotificacionServiceImpl(JavaMailSender mailSender) {
        this.mailSender = mailSender;
    }

    @Override
    public void enviarNotificacion(Cliente cliente, Fondo fondo, String tipoTransaccion ) {
        String mensaje = generarMensaje(cliente, fondo, tipoTransaccion);

        // Enviar correo electrónico
        enviarEmail(cliente.getEmail(), "Notificación de " + tipoTransaccion, mensaje);
    }

    private void enviarEmail(String destinatario, String asunto, String cuerpo) {
        SimpleMailMessage mensaje = new SimpleMailMessage();
        mensaje.setTo(destinatario);
        mensaje.setSubject(asunto);
        mensaje.setText(cuerpo);

        // Enviar el email
        mailSender.send(mensaje);
    }

    private String generarMensaje(Cliente cliente, Fondo fondo, String tipoTransaccion) {
        return "Cliente " + cliente.getNombre() + ",\n\n" +
                "Se ha realizado una " + tipoTransaccion + " en el fondo: " + fondo.getNombre() +
                " por un monto de: " + fondo.getMontoMinimo() + " COP.\n\n" +
                "Gracias por confiar en nosotros.";
    }
}
