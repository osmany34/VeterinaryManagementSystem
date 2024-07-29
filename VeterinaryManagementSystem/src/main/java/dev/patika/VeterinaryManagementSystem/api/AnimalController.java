package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAnimalService;
import dev.patika.VeterinaryManagementSystem.business.abstracts.ICustomerService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.converter.AnimalConverter;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.animal.AnimalUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.AnimalResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/animal")
public class AnimalController {

    private final IAnimalService animalService;

    public AnimalController(
            IAnimalService animalService,
            ICustomerService customerService, AnimalConverter animalConverter) {
        this.animalService = animalService;
    }

    // Yeni hayvan eklemek için
    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AnimalResponse> saveAnimal(@Valid @RequestBody AnimalSaveRequest animalSaveRequest) {
        return this.animalService.saveAnimal(animalSaveRequest);
    }

    // Mevcut hayvanı güncellemek için
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> updateAnimal(
            @PathVariable("id") Long id,
            @Valid @RequestBody AnimalUpdateRequest animalUpdateRequest) {
        animalUpdateRequest.setId(id); // Güncellenmiş id'yi ayarla
        return this.animalService.updateAnimal(animalUpdateRequest);
    }

    // ID ile hayvan bulmak için
    @GetMapping("/id/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> findAnimalById(@PathVariable("id") Long id) {
        return this.animalService.findAnimalById(id);
    }

    // İsimle hayvan bulmak için
    @GetMapping("/name/{animalName}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> findAnimalByName(@PathVariable("animalName") String name) {
        return this.animalService.findAnimalByName(name);
    }

    // Tüm hayvanları listelemek için
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> findAllAnimals() {
        return this.animalService.findAllAnimals();
    }

    // ID ile hayvan silmek için
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteAnimal(@PathVariable("id") Long id) {
        return this.animalService.deleteAnimal(id);
    }

    // Müşteri ID'sine göre hayvanları listelemek için
    @GetMapping("/customer/{customerId}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AnimalResponse>> findByCustomerId(@PathVariable("customerId") Long customerId) {
        return this.animalService.findByCustomerId(customerId);
    }
}
