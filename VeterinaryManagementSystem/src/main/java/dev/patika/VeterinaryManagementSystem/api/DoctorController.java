package dev.patika.VeterinaryManagementSystem.api;

import dev.patika.VeterinaryManagementSystem.business.abstracts.IDoctorService;
import dev.patika.VeterinaryManagementSystem.core.result.Result;
import dev.patika.VeterinaryManagementSystem.core.result.ResultData;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorSaveRequest;
import dev.patika.VeterinaryManagementSystem.dto.request.doctor.DoctorUpdateRequest;
import dev.patika.VeterinaryManagementSystem.dto.response.DoctorResponse;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/doctor")
public class DoctorController {

    private final IDoctorService doctorService;

    public DoctorController(IDoctorService doctorService) {
        this.doctorService = doctorService;
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> saveDoctor(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest) {
        return this.doctorService.saveDoctor(doctorSaveRequest);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> findDoctorById(@PathVariable("id") Long id) {
        return this.doctorService.findDoctorById(id);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<DoctorResponse>> findAllDoctors() {
        return this.doctorService.findAllDoctors();
    }

    @PutMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> updateDoctor(@Valid @RequestBody DoctorUpdateRequest doctorUpdateRequest) {
        return this.doctorService.updateDoctor(doctorUpdateRequest);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result deleteDoctor(@PathVariable("id") Long id) {
        return this.doctorService.deleteDoctor(id);
    }
}
