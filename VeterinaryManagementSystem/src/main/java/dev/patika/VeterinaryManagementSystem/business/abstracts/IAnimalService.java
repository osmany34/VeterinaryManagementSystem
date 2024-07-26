package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import org.springframework.data.domain.Page;
import java.util.List;

public interface IAnimalService {
    ResultData<AnimalResponse> saveAnimal(AnimalSaveRequest animalSaveRequest);

    ResultData<AnimalResponse> updateAnimal(AnimalUpdateRequest animalUpdateRequest);

    ResultData<AnimalResponse> findAnimalById(Long id);

    ResultData<List<AnimalResponse>> findAnimalByName(String name);

    ResultData<List<AnimalResponse>> findAllAnimals();

    Result deleteAnimal(Long id);

    ResultData<List<AnimalResponse>> findByCustomerId(Long customerId);
}

