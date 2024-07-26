package dev.patika.VeterinaryManagementSystem.dto.converter;


import dev.patika.VeterinaryManagementSystem.dao.AnimalRepository;
import dev.patika.VeterinaryManagementSystem.dao.DoctorRepository;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.appointment.AppointmentUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.AppointmentResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.Appointment;
import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class AppointmentConverter {
    private final AnimalRepository animalRepo;
    private final DoctorRepository doctorRepo;

    public AppointmentConverter(AnimalRepository animalRepo, DoctorRepository doctorRepo) {
        this.animalRepo = animalRepo;
        this.doctorRepo = doctorRepo;
    }


    public Appointment saveAppointment(AppointmentSaveRequest appointmentSaveRequest) {
        if (appointmentSaveRequest == null) {
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setAppointmentDateTime(appointmentSaveRequest.getAppointmentDateTime());
        Animal animal = animalRepo.findById(appointmentSaveRequest.getAnimalId()).get();
        appointment.setAnimal(animal);
        Doctor doctor = doctorRepo.findById(appointmentSaveRequest.getDoctorId()).get();
        appointment.setDoctor(doctor);
        return appointment;
    }


    public Appointment updateAppointment(AppointmentUpdateRequest appointmentUpdateRequest) {
        if (appointmentUpdateRequest == null) {
            return null;
        }
        Appointment appointment = new Appointment();
        appointment.setId(appointmentUpdateRequest.getId());
        appointment.setAppointmentDateTime(appointmentUpdateRequest.getAppointmentDateTime());
        Animal animal = animalRepo.findById(appointmentUpdateRequest.getAnimalId()).get();
        appointment.setAnimal(animal);
        Doctor doctor = doctorRepo.findById(appointmentUpdateRequest.getDoctorId()).get();
        appointment.setDoctor(doctor);
        return appointment;
    }


    public AppointmentResponse toAppointmentResponse(Appointment appointment) {
        if (appointment == null) {
            return null;
        }
        AppointmentResponse response = new AppointmentResponse();
        response.setId(appointment.getId());
        response.setAppointmentDate(appointment.getAppointmentDateTime());
        response.setAnimalId(appointment.getAnimal().getId());
        response.setDoctorId(appointment.getDoctor().getId());
        return response;
    }


    public List<AppointmentResponse> toAppointmentResponseList(List<Appointment> appointments) {
        return appointments.stream()
                .map(this::toAppointmentResponse)
                .collect(Collectors.toList());
    }
}


