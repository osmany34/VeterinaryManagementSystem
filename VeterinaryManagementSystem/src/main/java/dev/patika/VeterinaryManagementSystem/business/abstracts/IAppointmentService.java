package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.AppointmentResponse;
import dev.patika.VeterinaryManagementSystem.entities.Appointment;
import org.springframework.data.domain.Page;
import java.time.LocalDateTime;
import java.util.List;

public interface IAppointmentService {
    ResultData<AppointmentResponse> saveAppointment(AppointmentSaveRequest appointmentSaveRequest);

    ResultData<AppointmentResponse> updateAppointment(AppointmentUpdateRequest appointmentUpdateRequest);

    ResultData<AppointmentResponse> findAppointmentById(Long id);

    ResultData<List<AppointmentResponse>> findAllAppointments();

    ResultData<List<AppointmentResponse>> getAppointmentsByDoctorAndDateRange(Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime);

    ResultData<List<AppointmentResponse>> getAppointmentsByDateAndAnimal(LocalDateTime startDateTime, LocalDateTime endDateTime, Long animalId);

    Result deleteAppointment(Long id);
}
