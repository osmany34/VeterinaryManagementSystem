package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilities.ResultInfo;
import dev.patika.VeterinaryManagementSystem.dao.AnimalRepository;
import dev.patika.VeterinaryManagementSystem.dto.converter.AnimalConverter;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.AnimalResponse;
import dev.patika.VeterinaryManagementSystem.entities.Animal;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AnimalManager implements IAnimalService {

    private final AnimalRepository animalRepo;
    private final CustomerManager customerManager;
    private final AnimalConverter converter;

    public AnimalManager(AnimalRepository animalRepo, CustomerManager customerManager, AnimalConverter animalConverter) {
        this.animalRepo = animalRepo;
        this.customerManager = customerManager;
        this.converter = animalConverter;
    }

    // Yeni bir hayvan kaydeder
    public ResultData<AnimalResponse> saveAnimal(AnimalSaveRequest animalSaveRequest) {
        // Hayvanın zaten mevcut olup olmadığını kontrol eder
        checkIfAnimalExists(animalSaveRequest);
        // Müşterinin var olup olmadığını kontrol eder
        this.customerManager.findCustomerId(animalSaveRequest.getCustomerId());
        // DTO'dan hayvan entity'sine dönüştürür
        Animal saveAnimal = this.converter.convertToAnimal(animalSaveRequest);
        // Hayvanı veritabanına kaydeder
        this.animalRepo.save(saveAnimal);
        // Başarı mesajıyla birlikte döner
        return ResultInfo.created(this.converter.toAnimalResponse(saveAnimal));
    }

    // Hayvanın zaten mevcut olup olmadığını kontrol eder
    private void checkIfAnimalExists(AnimalSaveRequest animalSaveRequest) {
        Animal existingAnimal = animalRepo.findByNameAndSpeciesAndBreedAndGenderAndColourAndDateOfBirthAndCustomerId(
                animalSaveRequest.getName(),
                animalSaveRequest.getSpecies(),
                animalSaveRequest.getBreed(),
                animalSaveRequest.getGender(),
                animalSaveRequest.getColour(),
                animalSaveRequest.getDateOfBirth(),
                animalSaveRequest.getCustomerId()
        );

        if (existingAnimal != null) {
            throw new IllegalArgumentException("Aynı özelliklere sahip bir hayvan zaten mevcut.");
        }
    }

    @Override
    // Bir hayvan kaydını günceller
    public ResultData<AnimalResponse> updateAnimal(AnimalUpdateRequest animalUpdateRequest) {
        // Hayvanın var olup olmadığını kontrol eder
        this.findAnimalById(animalUpdateRequest.getId());
        // Müşterinin var olup olmadığını kontrol eder
        this.customerManager.findCustomerId(animalUpdateRequest.getCustomerId());
        // DTO'dan güncellenmiş hayvan entity'sine dönüştürür
        Animal updateAnimal = this.converter.convertToUpdateAnimal(animalUpdateRequest);
        // Güncellenmiş hayvanı veritabanına kaydeder
        this.animalRepo.save(updateAnimal);
        // Başarı mesajıyla birlikte döner
        return ResultInfo.success(this.converter.toAnimalResponse(updateAnimal));
    }

    @Override
    // ID'ye göre bir hayvanı bulur
    public ResultData<AnimalResponse> findAnimalById(Long id) {
        Animal animal = animalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " olan hayvan bulunamadı."));
        return ResultInfo.success(this.converter.toAnimalResponse(animal));
    }

    @Override
    // İsimle bir hayvanı bulur
    public ResultData<List<AnimalResponse>> findAnimalByName(String name) {
        List<Animal> animals = animalRepo.findByName(name);
        if (animals.isEmpty()) {
            throw new EntityNotFoundException("İsim: " + name + " ile hayvan bulunamadı.");
        }
        List<AnimalResponse> animalResponses = animals.stream()
                .map(this.converter::toAnimalResponse)
                .collect(Collectors.toList());
        return ResultInfo.success(animalResponses);
    }

    @Override
    // Tüm hayvanları bulur
    public ResultData<List<AnimalResponse>> findAllAnimals() {
        List<Animal> allAnimals = this.animalRepo.findAll();
        List<AnimalResponse> animalResponses = allAnimals.stream()
                .map(this.converter::toAnimalResponse)
                .collect(Collectors.toList());
        return ResultInfo.success(animalResponses);
    }

    @Override
    // Bir hayvanı siler
    public Result deleteAnimal(Long id) {
        Animal animal = this.findAnimal(id);
        this.animalRepo.delete(animal);
        return ResultInfo.ok();
    }

    @Override
    // Müşteri ID'sine göre hayvanları bulur
    public ResultData<List<AnimalResponse>> findByCustomerId(Long customerId) {
        this.customerManager.findCustomerId(customerId);
        List<Animal> animals = this.animalRepo.findByCustomerId(customerId);
        if (animals.isEmpty()) {
            throw new EntityNotFoundException("Müşteri ID: " + customerId + " için hayvan bulunamadı.");
        }
        List<AnimalResponse> animalResponses = animals.stream()
                .map(this.converter::toAnimalResponse)
                .collect(Collectors.toList());
        return ResultInfo.success(animalResponses);
    }

    // ID'ye göre bir hayvanı bulur
    public Animal findAnimal(Long id) {
        return animalRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID: " + id + " olan hayvan bulunamadı."));
    }
}
