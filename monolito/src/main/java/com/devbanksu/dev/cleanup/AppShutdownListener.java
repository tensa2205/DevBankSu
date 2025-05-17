package com.devbanksu.dev.cleanup;

import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;
import org.springframework.stereotype.Component;

@Component
public class AppShutdownListener implements ApplicationListener<ContextClosedEvent> {
    private final LimpiadorBaseDatosService service;

    public AppShutdownListener(LimpiadorBaseDatosService service) {
        this.service = service;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent event) {
        service.limpiarDB();
    }
}
