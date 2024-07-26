package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IAvailableDateService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilities.ResultInfo;
import dev.patika.VeterinaryManagementSystem.dao.AvailableDateRepository;
import dev.patika.VeterinaryManagementSystem.dto.converter.AvailableDateConverter;
import dev.patika.VeterinaryManagementSystem.dto.request.availableDate.AvailableDateSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.availableDate.AvailableDateUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.AvailableDateResponse;
import dev.patika.VeterinaryManagementSystem.entities.AvailableDate;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AvailableDateManager implements IAvailableDateService {

    private final AvailableDateConverter converterAvailableDate;
    private final AvailableDateRepository availableDateRepository;
    private final DoctorManager doctorManager;

    public AvailableDateManager(AvailableDateRepository availableDateRepo, AvailableDateConverter converterAvailableDate, AvailableDateRepository availableDateRepository, DoctorManager doctorManager) {
        this.converterAvailableDate = converterAvailableDate;
        this.availableDateRepository = availableDateRepository;
        this.doctorManager = doctorManager;
    }

    @Override
    // Mevcut tarihleri kaydeder
    public ResultData<AvailableDateResponse> saveAvailableDate(AvailableDateSaveRequest availableDateSaveRequest) {
        // Mevcut tarihin var olup olmadığını kontrol eder
        checkIfAvailableDateExists(availableDateSaveRequest);
        // Doktorun mevcut olup olmadığını kontrol eder
        this.doctorManager.findDoctorById(availableDateSaveRequest.getDoctorId());
        // Mevcut tarihi kaydeder
        AvailableDate saveAvailableDate = this.converterAvailableDate.convertToAvailableDate(availableDateSaveRequest);
        this.availableDateRepository.save(saveAvailableDate);
        // Başarı mesajıyla birlikte döner
        return ResultInfo.created(this.converterAvailableDate.toAvailableDateResponse(saveAvailableDate));
    }

    // Seçilen doktor ve tarihte mevcut bir tarih olup olmadığını kontrol eder
    private void checkIfAvailableDateExists(AvailableDateSaveRequest availableDateSaveRequest) {
        Optional<AvailableDate> existingAvailableDate = availableDateRepository.findByDoctorIdAndAvailableDate(
                availableDateSaveRequest.getDoctorId(), availableDateSaveRequest.getAvailableDate());

        if (existingAvailableDate.isPresent()) {
            throw new IllegalArgumentException("Seçilen doktor için belirtilen tarihte zaten mevcut bir tarih var.");
        }
    }


    @Override
    // Mevcut bir tarihi günceller
    public ResultData<AvailableDateResponse> updateAvailableDate(AvailableDateUpdateRequest availableDateUpdateRequest) {
        // Mevcut tarihin var olup olmadığını kontrol eder
        findAvailableDateById(availableDateUpdateRequest.getId());
        // DTO'yu entity nesnesine dönüştürür
        AvailableDate updatedAvailableDate = this.converterAvailableDate.convertToUpdateAvailableDate(
                availableDateUpdateRequest);
        // Güncellenmiş tarihi kaydeder
        updatedAvailableDate.setId(availableDateUpdateRequest.getId());
        this.availableDateRepository.save(updatedAvailableDate);
        // Başarı mesajıyla birlikte döner
        return ResultInfo.success(this.converterAvailableDate.toAvailableDateResponse(updatedAvailableDate));
    }

    @Override
    // ID'ye göre mevcut tarihi bulur
    public ResultData<AvailableDateResponse> findAvailableDateById(Long id) {
        AvailableDate availableDate = this.availableDateRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("ID " + id + " olan mevcut tarih bulunamadı."));
        return ResultInfo.success(this.converterAvailableDate.toAvailableDateResponse(availableDate));
    }

    @Override
    // Tüm mevcut tarihleri listeler
    public ResultData<List<AvailableDateResponse>> findAllAvailableDates() {
        List<AvailableDate> allAvailableDate = this.availableDateRepository.findAll();
        List<AvailableDateResponse> availableDateResponses = allAvailableDate.stream()
                .map(this.converterAvailableDate::toAvailableDateResponse)
                .collect(Collectors.toList());
        return ResultInfo.success(availableDateResponses);
    }

    @Override
    // ID'ye göre mevcut tarihi siler
    public Result deleteAvailableDate(Long id) {
        AvailableDate availableDate = this.availableDateRepository.findById(id).orElseThrow(()
                -> new EntityNotFoundException("ID " + id + " olan mevcut tarih bulunamadı."));
        this.availableDateRepository.delete(availableDate);
        return ResultInfo.ok();
    }

    // Doktorun belirtilen tarihte mevcut olup olmadığını kontrol eder
    public boolean availableDoctor(Long doctorId, LocalDate availableDate) {
        boolean doctorAvailable = this.availableDateRepository
                .findByDoctorIdAndAvailableDate(doctorId, availableDate).isPresent();
        if (!doctorAvailable) {
            throw new IllegalArgumentException("Doktor seçilen tarihte mevcut değil.");
        }
        return true;
    }
}
