package dev.patika.VeterinaryManagementSystem.dto.converter;


import dev.patika.VeterinaryManagementSystem.dao.AnimalRepository;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.VaccineResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.Vaccine;
import org.springframework.stereotype.Component;

@Component
public class VaccineConverter {
    private final AnimalRepository animalRepo;

    public VaccineConverter(AnimalRepository animalRepo) {
        this.animalRepo = animalRepo;
    }


    public Vaccine convertToVaccine(VaccineSaveRequest vaccineSaveRequest) {
        if (vaccineSaveRequest == null) {
            return null;
        }
        Vaccine vaccine = new Vaccine();
        vaccine.setName(vaccineSaveRequest.getName());
        vaccine.setCode(vaccineSaveRequest.getCode());
        vaccine.setProtectionStartDate(vaccineSaveRequest.getProtectionStartDate());
        vaccine.setProtectionFinishDate(vaccineSaveRequest.getProtectionFinishDate());
        Animal animal = this.animalRepo.findById(vaccineSaveRequest.getAnimalId()).get();
        vaccine.setAnimal(animal);
        return vaccine;
    }

    public Vaccine convertToUpdateVaccine(VaccineUpdateRequest vaccineUpdateRequest) {
        if (vaccineUpdateRequest == null) {
            return null;
        }
        Vaccine vaccine = new Vaccine();
        vaccine.setId(vaccineUpdateRequest.getId());
        vaccine.setName(vaccineUpdateRequest.getName());
        vaccine.setCode(vaccineUpdateRequest.getCode());
        vaccine.setProtectionStartDate(vaccineUpdateRequest.getProtectionStartDate());
        vaccine.setProtectionFinishDate(vaccineUpdateRequest.getProtectionFinishDate());
        Animal animal = animalRepo.findById(vaccineUpdateRequest.getAnimalId()).get();
        vaccine.setAnimal(animal);
        return vaccine;
    }

    public VaccineResponse toVaccineResponse(Vaccine vaccine) {
        if (vaccine == null) {
            return null;
        }
        VaccineResponse response = new VaccineResponse();
        response.setId(vaccine.getId());
        response.setName(vaccine.getName());
        response.setCode(vaccine.getCode());
        response.setProtectionStartDate(vaccine.getProtectionStartDate());
        response.setProtectionFinishDate(vaccine.getProtectionFinishDate());
        response.setAnimalId(vaccine.getAnimal().getId());
        return response;
    }
}
