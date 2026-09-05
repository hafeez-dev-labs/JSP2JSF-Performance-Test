package dev.hafeez.jsp2jsf.web;

import dev.hafeez.jsp2jsf.model.DashboardData;
import dev.hafeez.jsp2jsf.service.DashboardService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;

@Named("dashboard")
@ViewScoped
public class DashboardBean implements Serializable {
    private DashboardData data;
    @Inject private DashboardService service;
    @PostConstruct public void init() { data = service.loadDashboard(); }
    public DashboardData getData() { return data; }
}
