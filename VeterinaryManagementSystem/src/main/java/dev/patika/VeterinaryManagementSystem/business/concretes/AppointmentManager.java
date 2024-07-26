package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAppointmentService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilities.ResultHelper;
import dev.patika.VeterinaryManagementSystem.dto.converter.AppointmentConverter;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.AppointmentResponse;
import dev.patika.VeterinaryManagementSystem.entities.Appointment;
import dev.patika.VeterinaryManagementSystem.dao.AppointmentRepository;
import dev.patika.VeterinaryManagementSystem.core.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AppointmentManager implements IAppointmentService {

    private final AppointmentConverter converterAppointment;
    private final AppointmentRepository appointmentRepository;
    private final AnimalManager animalManager;
    private final DoctorManager doctorManager;
    private final AvailableDateManager availableDateManager;

    @Autowired
    public AppointmentManager(AppointmentConverter converterAppointment,
                              AppointmentRepository appointmentRepository,
                              AnimalManager animalManager,
                              DoctorManager doctorManager,
                              AvailableDateManager availableDateManager) {
        this.converterAppointment = converterAppointment;
        this.appointmentRepository = appointmentRepository;
        this.animalManager = animalManager;
        this.doctorManager = doctorManager;
        this.availableDateManager = availableDateManager;
    }

    @Override
    public ResultData<AppointmentResponse> saveAppointment(AppointmentSaveRequest appointmentSaveRequest) {
        // Hayvanın mevcut olup olmadığını kontrol eder
        this.animalManager.findAnimal(appointmentSaveRequest.getAnimalId());
        // Doktorun mevcut olup olmadığını kontrol eder
        this.doctorManager.findDoctorById(appointmentSaveRequest.getDoctorId());
        // Randevu tarihi için doktorun uygun olup olmadığını kontrol eder
        LocalDate availableDate = appointmentSaveRequest.getAppointmentDateTime().toLocalDate();
        this.availableDateManager.availableDoctor(appointmentSaveRequest.getDoctorId(), availableDate);

        // Doktorun belirtilen zaman diliminde başka bir randevusu olup olmadığını kontrol eder
        this.appointmentExists(appointmentSaveRequest.getDoctorId(), appointmentSaveRequest.getAppointmentDateTime());

        // DTO'dan randevu entity'sine dönüştürür ve kaydeder
        Appointment saveAppointment = this.converterAppointment.saveAppointment(appointmentSaveRequest);
        this.appointmentRepository.save(saveAppointment);
        // Başarı mesajıyla birlikte döner
        return ResultHelper.created(this.converterAppointment.toAppointmentResponse(saveAppointment));
    }

    @Override
    public ResultData<AppointmentResponse> updateAppointment(AppointmentUpdateRequest appointmentUpdateRequest) {
        // Randevunun mevcut olup olmadığını kontrol eder
        this.findAppointment(appointmentUpdateRequest.getId());
        // Hayvanın mevcut olup olmadığını kontrol eder
        this.animalManager.findAnimal(appointmentUpdateRequest.getAnimalId());
        // Doktorun mevcut olup olmadığını kontrol eder
        this.doctorManager.findDoctorById(appointmentUpdateRequest.getDoctorId());
        // Randevu tarihi için doktorun uygun olup olmadığını kontrol eder
        LocalDate availableDate = appointmentUpdateRequest.getAppointmentDateTime().toLocalDate();
        this.availableDateManager.availableDoctor(appointmentUpdateRequest.getDoctorId(), availableDate);
        // DTO'dan güncellenmiş randevu entity'sine dönüştürür ve kaydeder
        Appointment saveAppointment = this.converterAppointment.updateAppointment(appointmentUpdateRequest);
        this.appointmentRepository.save(saveAppointment);
        // Başarı mesajıyla birlikte döner
        return ResultHelper.success(this.converterAppointment.toAppointmentResponse(saveAppointment));
    }

    void appointmentExists(Long doctorId, LocalDateTime appointmentDateTime) {
        LocalDateTime startDateTime = appointmentDateTime.withMinute(0).withSecond(0).withNano(0);
        LocalDateTime endDateTime = startDateTime.plusHours(1); // Randevu süresinin 1 saat olduğu varsayılmıştır

        boolean doctorAvailable = this.appointmentRepository
                .existsByDoctorIdAndAppointmentDateTimeBetween(doctorId, startDateTime, endDateTime);

        if (doctorAvailable) {
            throw new IllegalArgumentException("Doktorun belirtilen zaman diliminde zaten bir randevusu var.");
        }
    }

    public Appointment findAppointment(Long id) {
        return appointmentRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("ID: " + id + " olan randevu bulunamadı."));
    }

    @Override
    public ResultData<AppointmentResponse> findAppointmentById(Long id) {
        Appointment appointment = this.findAppointment(id);
        return ResultHelper.success(this.converterAppointment.toAppointmentResponse(appointment));
    }

    @Override
    public ResultData<List<AppointmentResponse>> findAllAppointments() {
        List<Appointment> allAppointments = this.appointmentRepository.findAll();
        List<AppointmentResponse> appointmentResponses = allAppointments.stream()
                .map(this.converterAppointment::toAppointmentResponse)
                .collect(Collectors.toList());
        return ResultHelper.success(appointmentResponses);
    }

    @Override
    public Result deleteAppointment(Long id) {
        Appointment appointment = this.findAppointment(id);
        this.appointmentRepository.delete(appointment);
        return ResultHelper.ok();
    }

    @Override
    public ResultData<List<AppointmentResponse>> getAppointmentsByDoctorAndDateRange(Long doctorId, LocalDateTime startDateTime, LocalDateTime endDateTime) {
        // Randevuları filtrele
        List<Appointment> appointments = appointmentRepository.findByDoctorIdAndAppointmentDateTimeBetween(doctorId, startDateTime, endDateTime);

        // Boş liste kontrolü
        if (appointments.isEmpty()) {
            return ResultHelper.success(Collections.emptyList());
        }

        // Randevu yanıtları oluştur
        List<AppointmentResponse> responses = appointments.stream()
                .map(this.converterAppointment::toAppointmentResponse)
                .collect(Collectors.toList());

        return ResultHelper.success(responses);
    }

    @Override
    public ResultData<List<AppointmentResponse>> getAppointmentsByDateAndAnimal(LocalDateTime startDateTime, LocalDateTime endDateTime, Long animalId) {
        this.animalManager.findAnimal(animalId);
        List<Appointment> appointments = appointmentRepository.findByAnimalIdAndAppointmentDateTimeBetween(animalId, startDateTime, endDateTime);
        List<AppointmentResponse> appointmentResponses = appointments.stream()
                .map(this.converterAppointment::toAppointmentResponse)
                .collect(Collectors.toList());
        return ResultHelper.success(appointmentResponses);
    }
}
