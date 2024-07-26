package dev.patika.VeterinaryManagementSystem.dto.converter;

import dev.patika.VeterinaryManagementSystem.dao.DoctorRepo;
import dev.patika.VeterinaryManagementSystem.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.AvailableDateResponse;
import dev.patika.VeterinaryManagementSystem.entities.AvailableDate;
import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class AvailableDateConverter {

    private final DoctorRepo doctorRepo;

    /**
     * Constructs a new AvailableDateConverter with the specified DoctorRepository.
     *
     * @param doctorRepo the repository for accessing Doctor entities
     */
    public AvailableDateConverter(DoctorRepo doctorRepo) {
        this.doctorRepo = doctorRepo;
    }

    /**
     * Converts an AvailableDateSaveRequest object to an AvailableDate entity.
     *
     * @param availableDateSaveRequest the request object containing data for creating a new AvailableDate
     * @return the created AvailableDate entity
     */
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

    /**
     * Converts an AvailableDateUpdateRequest object to an AvailableDate entity.
     *
     * @param availableDateUpdateRequest the request object containing data for updating an existing AvailableDate
     * @return the updated AvailableDate entity
     */
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

    /**
     * Converts an AvailableDate entity to an AvailableDateResponse object.
     *
     * @param availableDate the AvailableDate entity to convert
     * @return the AvailableDateResponse object representing the entity
     */
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
