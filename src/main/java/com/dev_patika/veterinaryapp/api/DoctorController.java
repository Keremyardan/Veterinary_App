package com.dev_patika.veterinaryapp.api;

import com.dev_patika.veterinaryapp.business.abstracts.IDoctorService;
import com.dev_patika.veterinaryapp.core.config.result.Result;
import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.core.config.utilities.ResultHelper;
import com.dev_patika.veterinaryapp.core.modelMapper.IModelMapperService;
import com.dev_patika.veterinaryapp.dto.request.doctor.DoctorSaveRequest;
import com.dev_patika.veterinaryapp.dto.request.doctor.DoctorUpdateRequest;
import com.dev_patika.veterinaryapp.dto.response.CursorResponse;
import com.dev_patika.veterinaryapp.dto.response.doctor.DoctorResponse;
import com.dev_patika.veterinaryapp.entities.Doctor;
import lombok.Getter;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import javax.print.Doc;
import javax.validation.Valid;

// endpoint creation for doctor entity
@RestController
@RequestMapping("/v1/doctors")
public class DoctorController {
    private final IDoctorService doctorService;

    private final IModelMapperService modelMapperService;

    // constructor with parameters
    public DoctorController(IDoctorService doctorService, IModelMapperService modelMapperService) {
        this.doctorService = doctorService;
        this.modelMapperService = modelMapperService;
    }

    // code block for doctor save function
    @PostMapping("doctor")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<DoctorResponse> save(@Valid @RequestBody DoctorSaveRequest doctorSaveRequest) {
        Doctor saveDoctor = this.modelMapperService.forRequest().map(doctorSaveRequest, Doctor.class);
        ResultData<Doctor> result = this.doctorService.save(saveDoctor);

        if (!result.isSuccess()) {
            return new ResultData<>(false, result.getMessage(), "400", null);
        }
        return ResultHelper.created(this.modelMapperService.forResponse().map(result.getData(), DoctorResponse.class));
    }

    //code block for getting doctors by id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<DoctorResponse> get(@PathVariable("id") Long id) {
        Doctor doctor = this.doctorService.get(id);
        return ResultHelper.success(this.modelMapperService.forResponse().map(doctor, DoctorResponse.class));
    }

    @GetMapping("{id}/doctor")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<DoctorResponse>> cursor(
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size) {
        Page<Doctor> doctors = this.doctorService.cursor(page, size);
        Page<DoctorResponse> doctorResponsePage = doctors
                .map(doctor -> this.modelMapperService.forResponse().map(doctor, DoctorResponse.class));
        return ResultHelper.cursor(doctorResponsePage);
    }

    //update method for doctors
    @PutMapping("/update")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public  ResultData<DoctorResponse> update(@Valid @RequestBody DoctorUpdateRequest doctorUpdateRequest){
        Doctor updateDoctor = this.modelMapperService.forRequest().map(doctorUpdateRequest, Doctor.class);
        ResultData<Doctor> result = this.doctorService.update(updateDoctor);

        if(!result.isSuccess()) {
            return new ResultData<>(false,result.getMessage(),"404",null);
        }
        return ResultHelper.success(this.modelMapperService.forResponse().map(result.getData(),DoctorResponse.class));
    }

    // delete method by doctor id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
   public void deleteDoctorById(@PathVariable("id") Long id){
        this.doctorService.delete(id);
    }
}
