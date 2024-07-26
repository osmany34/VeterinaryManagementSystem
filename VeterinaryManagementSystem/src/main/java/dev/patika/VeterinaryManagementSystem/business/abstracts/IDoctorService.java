package dev.patika.VeterinaryManagementSystem.business.abstracts;

import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.DoctorResponse;
import dev.patika.VeterinaryManagementSystem.entities.Doctor;
import org.springframework.data.domain.Page;
import java.util.List;

public interface IDoctorService {
    ResultData<DoctorResponse> saveDoctor(DoctorSaveRequest doctorSaveRequest);

    ResultData<DoctorResponse> updateDoctor(DoctorUpdateRequest doctorUpdateRequest);

    ResultData<DoctorResponse> findDoctorById(Long id);

    Result deleteDoctor(Long id);

    ResultData<List<DoctorResponse>> findAllDoctors();
}
