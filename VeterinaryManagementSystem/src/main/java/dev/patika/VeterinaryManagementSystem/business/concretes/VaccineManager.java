package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IVaccineService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilities.ResultInfo;
import dev.patika.VeterinaryManagementSystem.dao.VaccineRepo;
import dev.patika.VeterinaryManagementSystem.dto.converter.AnimalConverter;
import dev.patika.VeterinaryManagementSystem.dto.converter.VaccineConverter;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.vaccine.VaccineUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.VaccineResponse;
import dev.patika.VeterinaryManagementSystem.entities.Vaccine;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class VaccineManager implements IVaccineService {

    private final VaccineRepo vaccineRepo;
    private final AnimalManager animalManager;
    private final VaccineConverter vaccineConverter;

    public VaccineManager(VaccineRepo vaccineRepo, AnimalManager animalManager, VaccineConverter vaccineConverter, AnimalConverter animalConverter) {
        this.vaccineRepo = vaccineRepo;
        this.animalManager = animalManager;
        this.vaccineConverter = vaccineConverter;
    }

    // Yeni bir aşı kaydeder
    @Override
    public ResultData<VaccineResponse> saveVaccine(VaccineSaveRequest vaccineSaveRequest) {
        // Hayvanın var olup olmadığını kontrol eder
        this.animalManager.findAnimalById(vaccineSaveRequest.getAnimalId());

        // Mevcut aşıları doğrular
        validateExistingVaccines(vaccineSaveRequest);

        // Yeni aşı nesnesini oluşturur ve kaydeder
        Vaccine saveVaccine = this.vaccineConverter.convertToVaccine(vaccineSaveRequest);
        this.vaccineRepo.save(saveVaccine);

        // Başarıyla kaydedilen aşı bilgilerini döner
        return ResultInfo.created(this.vaccineConverter.toVaccineResponse(saveVaccine));
    }

    // Mevcut aşıları doğrular
    private void validateExistingVaccines(VaccineSaveRequest vaccineSaveRequest) {
        // Aynı isim, kod ve hayvan ID'sine sahip mevcut aşıları bulur
        List<Vaccine> existingVaccines = vaccineRepo.findByNameAndCodeAndAnimalId(
                vaccineSaveRequest.getName(),
                vaccineSaveRequest.getCode(),
                vaccineSaveRequest.getAnimalId()
        );

        // Koruma başlangıç tarihi bitiş tarihinden önce olmalı
        if (vaccineSaveRequest.getExpirationDate().isBefore(LocalDate.now())) {
            throw new IllegalArgumentException("Aşının koruyuculuk bitiş tarihi geçmiş.");
        }

        // Mevcut aşıların koruma bitiş tarihlerini kontrol eder
        for (Vaccine existingVaccine : existingVaccines) {
            if (!existingVaccine.getProtectionFinishDate().isBefore(vaccineSaveRequest.getProtectionFinishDate())) {
                throw new IllegalArgumentException("Aynı isim, kod ve hayvan ID'sine sahip bir aşı zaten mevcut " +
                        "ve mevcut aşının koruma bitiş tarihi, yeni aşının tarihleriyle örtüşüyor.");
            }
        }
    }

    // Var olan bir aşıyı günceller
    @Override
    public ResultData<VaccineResponse> updateVaccine(VaccineUpdateRequest vaccineUpdateRequest) {
        // Güncellenecek aşının var olup olmadığını kontrol eder
        this.findVaccineById(vaccineUpdateRequest.getId());

        // Hayvanın var olup olmadığını kontrol eder
        this.animalManager.findAnimalById(vaccineUpdateRequest.getAnimalId());

        // Güncellenmiş aşı nesnesini oluşturur
        Vaccine updateVaccine = this.vaccineConverter.convertToUpdateVaccine(vaccineUpdateRequest);

        // Güncellenmiş aşının veritabanına kaydeder
        this.vaccineRepo.save(updateVaccine);

        // Başarıyla güncellenen aşı bilgilerini döner
        return ResultInfo.success(this.vaccineConverter.toVaccineResponse(updateVaccine));
    }

    @Override
    // ID'ye göre aşının bilgilerini bulur
    public ResultData<VaccineResponse> findVaccineById(Long id) {
        Vaccine vaccine = vaccineRepo.findById(id).orElseThrow(()
                -> new EntityNotFoundException("ID " + id + " olan aşı bulunamadı."));

        // Bulunan aşıyı yanıt DTO'suna çevirir
        return ResultInfo.success(this.vaccineConverter.toVaccineResponse(vaccine));
    }

    @Override
    // Tüm aşılara erişir
    public ResultData<List<VaccineResponse>> findAllVaccines() {
        List<Vaccine> allVaccines = this.vaccineRepo.findAll();

        List<VaccineResponse> vaccineResponses = allVaccines.stream()
                .map(this.vaccineConverter::toVaccineResponse)
                .collect(Collectors.toList());

        // Tüm aşı bilgilerini yanıt olarak döner
        return ResultInfo.success(vaccineResponses);
    }

    @Override
    // ID'ye göre aşıyı siler
    public Result deleteVaccine(Long id) {
        Vaccine vaccine = this.findVaccine(id);

        // Aşıyı veritabanından siler
        this.vaccineRepo.delete(vaccine);

        // Silme işlemi başarılı yanıtı döner
        return ResultInfo.ok();
    }

    // ID'ye göre aşıyı bulur
    public Vaccine findVaccine(Long id){
        return this.vaccineRepo.findById(id).orElseThrow(()
                -> new EntityNotFoundException("ID " + id + " olan aşı bulunamadı."));
    }

    @Override
    // Hayvan ID'sine göre aşılara erişir
    public ResultData<List<VaccineResponse>> findByAnimalId(Long animalId) {
        // Hayvanın var olup olmadığını kontrol eder
        this.animalManager.findAnimalById(animalId);

        // Hayvana ait aşıları bulur
        List<Vaccine> vaccines = this.vaccineRepo.findByAnimalId(animalId);

        if (vaccines.isEmpty()) {
            throw new EntityNotFoundException("ID " + animalId + " olan hayvan için aşı bulunamadı.");
        }

        List<VaccineResponse> vaccineResponses = vaccines.stream()
                .map(this.vaccineConverter::toVaccineResponse)
                .collect(Collectors.toList());

        // Aşı bilgilerini yanıt olarak döner
        return ResultInfo.success(vaccineResponses);
    }

    @Override
    // Belirli tarihler arasında sona erecek aşıları bulur
    public ResultData<List<VaccineResponse>> findExpiringVaccines(LocalDate startDate, LocalDate endDate) {
        // Koruma bitiş tarihi belirli tarihler arasında olan aşıları bulur
        List<Vaccine> expiringVaccines = vaccineRepo.findByProtectionFinishDateBetween(startDate, endDate);

        List<VaccineResponse> vaccineResponses = expiringVaccines.stream()
                .map(this.vaccineConverter::toVaccineResponse)
                .collect(Collectors.toList());

        // Sona erecek aşı bilgilerini yanıt olarak döner
        return ResultInfo.success(vaccineResponses);
    }
}
