package dev.hafeez.jsp2jsf.web;

import dev.hafeez.jsp2jsf.model.DashboardData;
import dev.hafeez.jsp2jsf.service.DashboardService;
import jakarta.annotation.PostConstruct;
import jakarta.faces.view.ViewScoped;
import jakarta.inject.Inject;
import jakarta.inject.Named;
import java.io.Serializable;
import java.util.List;

@Named("jsfApp")
@ViewScoped
public class JsfApplicationBean implements Serializable {
    private DashboardData data;
    private String selectedStatus = "All";
    private String title;
    private String owner;
    private String formMessage;
    private String formError;
    @Inject private DashboardService service;

    @PostConstruct
    public void init() {
        data = service.loadDashboard();
    }

    public void filter() {
        formMessage = null;
        formError = null;
    }

    public void submitTicket() {
        formMessage = null;
        formError = null;
        if (title == null || title.isBlank() || owner == null || owner.isBlank()) {
            formError = "Title and owner are required.";
            return;
        }
        formMessage = "Ticket request received for '" + title.trim() + "' and assigned to " + owner.trim() + ".";
        title = null;
        owner = null;
    }

    public List<DashboardData.Ticket> getTickets() {
        return service.filterTickets(selectedStatus);
    }

    public DashboardData getData() { return data; }
    public String getSelectedStatus() { return selectedStatus; }
    public void setSelectedStatus(String selectedStatus) { this.selectedStatus = selectedStatus; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getOwner() { return owner; }
    public void setOwner(String owner) { this.owner = owner; }
    public String getFormMessage() { return formMessage; }
    public String getFormError() { return formError; }
}
