package com.btg.pactual.business;

import com.btg.pactual.model.Cliente;
import com.btg.pactual.model.Fondo;
import com.btg.pactual.model.Notificacion;

public interface NotificacionService {
    void enviarNotificacion(Cliente cliente, Fondo fondo, String tipoTransaccion);
}
