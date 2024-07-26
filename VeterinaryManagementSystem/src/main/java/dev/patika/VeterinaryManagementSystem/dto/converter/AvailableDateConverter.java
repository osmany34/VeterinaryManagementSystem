package dev.patika.VeterinaryManagementSystem.dto.converter;

import dev.patika.VeterinaryManagementSystem.dao.DoctorRepository;
import dev.patika.VeterinaryManagementSystem.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.AvailableDateResponse;
import dev.patika.VeterinaryManagementSystem.entities.AvailableDate;
import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import org.springframework.stereotype.Component;

@Component
public class AvailableDateConverter {

    private final DoctorRepository doctorRepo;

    public AvailableDateConverter(DoctorRepository doctorRepo) {
        this.doctorRepo = doctorRepo;
    }


    public AvailableDate convertToAvailableDate(AvailableDateSaveRequest availableDateSaveRequest) {
        if (availableDateSaveRequest == null) {
            return null;
        }
        AvailableDate availableDate = new AvailableDate();
        availableDate.setAvailableDate(availableDateSaveRequest.getAvailableDate()); // LocalDateTime
        Doctor doctor = doctorRepo.findById(availableDateSaveRequest.getDoctorId()).orElse(null);
        availableDate.setDoctor(doctor);
        return availableDate;
    }

    public AvailableDate convertToUpdateAvailableDate(AvailableDateUpdateRequest availableDateUpdateRequest) {
        if (availableDateUpdateRequest == null) {
            return null;
        }
        AvailableDate availableDate = new AvailableDate();
        availableDate.setId(availableDateUpdateRequest.getId());
        availableDate.setAvailableDate(availableDateUpdateRequest.getAvailableDate()); // LocalDateTime
        Doctor doctor = doctorRepo.findById(availableDateUpdateRequest.getDoctorId()).orElse(null);
        availableDate.setDoctor(doctor);
        return availableDate;
    }

    public AvailableDateResponse toAvailableDateResponse(AvailableDate availableDate) {
        if (availableDate == null) {
            return null;
        }
        AvailableDateResponse response = new AvailableDateResponse();
        response.setId(availableDate.getId());
        response.setAvailableDate(availableDate.getAvailableDate()); // LocalDateTime
        response.setDoctorId(availableDate.getDoctor().getId());
        return response;
    }
}
