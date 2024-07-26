package dev.patika.VeterinaryManagementSystem.dto.converter;


import dev.patika.VeterinaryManagementSystem.dao.CustomerRepo;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import dev.patika.VeterinaryManagementSystem.entities.Customer;
import org.springframework.stereotype.Component;

@Component
public class AnimalConverter {

    private final CustomerRepo customerRepo;

    public AnimalConverter(CustomerRepo customerRepo) {
        this.customerRepo = customerRepo;
    }

    /**
     * Converts an AnimalSaveRequest object to an Animal entity.
     *
     * @param animalSaveRequest The AnimalSaveRequest object to convert.
     * @return The converted Animal entity.
     */
    public Animal convertToAnimal(AnimalSaveRequest animalSaveRequest) {
        if (animalSaveRequest == null) {
            return null;
        }
        Animal animal = new Animal();
        animal.setName(animalSaveRequest.getName());
        animal.setSpecies(animalSaveRequest.getSpecies());
        animal.setBreed(animalSaveRequest.getBreed());
        animal.setGender(animalSaveRequest.getGender());
        animal.setColour(animalSaveRequest.getColour());
        animal.setDateOfBirth(animalSaveRequest.getDateOfBirth());
        Customer customer = customerRepo.findById(animalSaveRequest.getCustomerId()).get();
        animal.setCustomer(customer);
        return animal;
    }

    /**
     * Converts an AnimalUpdateRequest object to an Animal entity.
     *
     * @param animalUpdateRequest The AnimalUpdateRequest object to convert.
     * @return The converted Animal entity.
     */
    public Animal convertToUpdateAnimal(AnimalUpdateRequest animalUpdateRequest) {
        if (animalUpdateRequest == null) {
            return null;
        }
        Animal animal = new Animal();
        animal.setId(animalUpdateRequest.getId());
        animal.setName(animalUpdateRequest.getName());
        animal.setSpecies(animalUpdateRequest.getSpecies());
        animal.setBreed(animalUpdateRequest.getBreed());
        animal.setGender(animalUpdateRequest.getGender());
        animal.setColour(animalUpdateRequest.getColour());
        animal.setDateOfBirth(animalUpdateRequest.getDateOfBirth());
        Customer customer = customerRepo.findById(animalUpdateRequest.getCustomerId()).get();
        animal.setCustomer(customer);
        return animal;
    }

    /**
     * Converts an Animal entity to an AnimalResponse object.
     *
     * @param animal The Animal entity to convert.
     * @return The converted AnimalResponse object.
     */
    public AnimalResponse toAnimalResponse(Animal animal) {
        if (animal == null) {
            return null;
        }
        AnimalResponse response = new AnimalResponse();
        response.setId(animal.getId());
        response.setName(animal.getName());
        response.setSpecies(animal.getSpecies());
        response.setBreed(animal.getBreed());
        response.setGender(animal.getGender());
        response.setColour(animal.getColour());
        response.setDateOfBirth(animal.getDateOfBirth());
        response.setCustomerId(animal.getCustomer().getId());
        return response;
    }
}

