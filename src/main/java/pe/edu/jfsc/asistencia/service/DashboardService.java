package pe.edu.jfsc.asistencia.service;

import org.springframework.stereotype.Service;

import pe.edu.jfsc.asistencia.model.AttendanceStatus;

@Service
public class DashboardService {

    private final AttendanceService a;

    public DashboardService(AttendanceService a) {
        this.a = a;
    }

    public long totalToday() {
        return a.countToday();
    }

    public long presentToday() {
        return a.countToday(AttendanceStatus.PRESENTE);
    }

    public long lateToday() {
        return a.countToday(AttendanceStatus.TARDANZA);
    }

    public long absentToday() {
        return a.countToday(AttendanceStatus.FALTA);
    }
}