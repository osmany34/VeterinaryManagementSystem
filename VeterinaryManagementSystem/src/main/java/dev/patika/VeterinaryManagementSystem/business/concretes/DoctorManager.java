package dev.patika.VeterinaryManagementSystem.business.concretes;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.core.utilities.ResultInfo;
import dev.patika.VeterinaryManagementSystem.dao.DoctorRepo;
import dev.patika.VeterinaryManagementSystem.dto.converter.DoctorConverter;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.DoctorResponse;
import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DoctorManager implements IDoctorService {

    private final DoctorConverter doctorConverter;
    private final DoctorRepo doctorRepo;

    public DoctorManager(DoctorConverter converter, DoctorRepo doctorRepo) {
        this.doctorConverter = converter;
        this.doctorRepo = doctorRepo;
    }

    // Yeni bir doktor kaydeder
    @Override
    public ResultData<DoctorResponse> saveDoctor(DoctorSaveRequest doctorSaveRequest) {
        // Kaydedilecek doktor nesnesini oluşturur
        Doctor saveDoctor = this.doctorConverter.convertToDoctor(doctorSaveRequest);

        // Doktorun var olup olmadığını kontrol eder
        checkDoctorExistence(saveDoctor);

        // Doktoru veritabanına kaydeder
        this.doctorRepo.save(saveDoctor);

        // Başarıyla kaydedilen doktor bilgilerini döner
        return ResultInfo.created(this.doctorConverter.toDoctorResponse(saveDoctor));
    }

    @Override
    // Doktor bilgisini günceller
    public ResultData<DoctorResponse> updateDoctor(DoctorUpdateRequest doctorUpdateRequest) {
        // Güncellenecek doktorun var olup olmadığını kontrol eder
        findDoctorById(doctorUpdateRequest.getId());

        // Güncellenmiş doktor nesnesini oluşturur
        Doctor updatedDoctor = this.doctorConverter.convertToupdateDoctor(doctorUpdateRequest);
        updatedDoctor.setId(doctorUpdateRequest.getId());

        // Güncellenmiş doktoru veritabanına kaydeder
        this.doctorRepo.save(updatedDoctor);

        // Başarıyla güncellenen doktor bilgilerini döner
        return ResultInfo.success(this.doctorConverter.toDoctorResponse(updatedDoctor));
    }

    @Override
    // ID'ye göre doktoru bulur
    public ResultData<DoctorResponse> findDoctorById(Long id) {
        Doctor doctor = this.doctorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID " + id + " olan doktor bulunamadı."));

        // Bulunan doktoru yanıt DTO'suna çevirir
        return ResultInfo.success(this.doctorConverter.toDoctorResponse(doctor));
    }

    @Override
    // ID'ye göre doktoru siler
    public Result deleteDoctor(Long id) {
        Doctor doctor = this.doctorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID " + id + " olan doktor bulunamadı."));

        // Doktoru veritabanından siler
        this.doctorRepo.delete(doctor);

        // Silme işlemi başarılı yanıtı döner
        return ResultInfo.ok();
    }

    @Override
    // Tüm doktorları listeler
    public ResultData<List<DoctorResponse>> findAllDoctors() {
        List<Doctor> allDoctors = this.doctorRepo.findAll();

        List<DoctorResponse> doctorResponses = allDoctors.stream()
                .map(this.doctorConverter::toDoctorResponse)
                .collect(Collectors.toList());

        // Tüm doktor bilgilerini yanıt olarak döner
        return ResultInfo.success(doctorResponses);
    }

    // ID'ye göre doktoru bulur
    public Doctor findDoctorId(Long id) {
        return this.doctorRepo.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("ID " + id + " olan doktor bulunamadı."));
    }

    // Doktorun e-posta ve telefon numarasına göre var olup olmadığını kontrol eder
    private void checkDoctorExistence(Doctor doctor) {
        if (doctorRepo.findByMail(doctor.getMail()).isPresent()) {
            throw new IllegalArgumentException("Email adresi " + doctor.getMail() + " zaten kayıtlı.");
        }
        if (doctorRepo.findByPhone(doctor.getPhone()).isPresent()) {
            throw new IllegalArgumentException("Telefon numarası " + doctor.getPhone() + " zaten kayıtlı.");
        }
    }
}
