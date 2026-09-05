package dev.hafeez.jsp2jsf.service;

import dev.hafeez.jsp2jsf.model.DashboardData;
import jakarta.enterprise.context.ApplicationScoped;
import java.util.List;

@ApplicationScoped
public class DashboardService {
    public DashboardData loadDashboard() {
        return new DashboardData("Aaqib",18,142,37,
            List.of(new DashboardData.Activity("09:42","Payment service recovered","Healthy"),new DashboardData.Activity("09:18","Monthly report generated","Done"),new DashboardData.Activity("08:55","New user cohort imported","Done"),new DashboardData.Activity("08:31","Cache refresh completed","Healthy")),
            List.of(new DashboardData.ReportRow("API latency",12480,"+12%"),new DashboardData.ReportRow("Successful requests",11842,"+8%"),new DashboardData.ReportRow("Error responses",638,"-21%")));
    }
}
