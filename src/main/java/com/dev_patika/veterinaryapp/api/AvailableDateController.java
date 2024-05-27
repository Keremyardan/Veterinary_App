package com.dev_patika.veterinaryapp.api;

import com.dev_patika.veterinaryapp.business.abstracts.IAvailableDateService;
import com.dev_patika.veterinaryapp.business.abstracts.IDoctorService;
import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.core.config.utilities.ResultHelper;
import com.dev_patika.veterinaryapp.core.modelMapper.IModelMapperService;
import com.dev_patika.veterinaryapp.dto.request.availabledate.AvailableDateSaveRequest;
import com.dev_patika.veterinaryapp.dto.request.availabledate.AvailableDateUpdateRequest;
import com.dev_patika.veterinaryapp.dto.response.CursorResponse;
import com.dev_patika.veterinaryapp.dto.response.availabledate.AvailableDateResponse;
import com.dev_patika.veterinaryapp.dto.response.customer.CustomerResponse;
import com.dev_patika.veterinaryapp.entities.AvailableDate;
import com.dev_patika.veterinaryapp.entities.Doctor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

// endpoint creation for available dates
@RestController
@RequestMapping("/v1/available-dates")
public class AvailableDateController {

    private final IAvailableDateService availableDateService;

    private final IModelMapperService modelMapperService;

    private final IDoctorService doctorService;

    // constructor with parameters
    public AvailableDateController(IAvailableDateService availableDateService, IModelMapperService modelMapperService, IDoctorService doctorService) {
        this.availableDateService = availableDateService;
        this.modelMapperService = modelMapperService;
        this.doctorService = doctorService;
    }

    // a code block for available date save function
    @PostMapping("/available-date")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AvailableDateResponse> save(@Valid @RequestBody AvailableDateSaveRequest availableDateSaveRequest) {
        // This method saves the available date.
        AvailableDate saveAvailableDate = this.modelMapperService.forRequest().map(availableDateSaveRequest, AvailableDate.class);
        this.availableDateService.save(saveAvailableDate);
        return ResultHelper.created(this.modelMapperService.forResponse().map(saveAvailableDate, AvailableDateResponse.class));
    }

    // code block for to get available dates by doctor id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<List<AvailableDate>> getAvailableDateByDoctorId(@PathVariable("id") Long id) {

       List<AvailableDate> availableDates = this.availableDateService.findAvailableDatesByDoctorId(id);
        List<AvailableDateResponse> availableDateResponses = availableDates.stream()
                .map(availableDate -> this.modelMapperService.forResponse().map(availableDate, AvailableDateResponse.class))
                .collect(Collectors.toList());
        System.out.println(availableDates);
        return ResultHelper.success(availableDates);
    }
    // this block provides a pagination system which controls large amount of
    // data returns and reformat them.
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AvailableDateResponse>> cursor(
            // This method returns the available dates with pagination.
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size)
    {
        Page<AvailableDate> availableDates = this.availableDateService.cursor(page, size);
        Page<AvailableDateResponse> availableDateResponsePage = availableDates
                .map(availableDate -> this.modelMapperService.forResponse().map(availableDate, AvailableDateResponse.class));

        return ResultHelper.cursor(availableDateResponsePage);
    }

    // code block for available date record update
    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AvailableDateResponse> update(@Valid @RequestBody AvailableDateUpdateRequest availableDateUpdateRequest) {
        // Map the request to an AvailableDate object
        AvailableDate availableDate = this.modelMapperService.forRequest().map(availableDateUpdateRequest, AvailableDate.class);

        // Call the update method in the AvailableDateManager class
        AvailableDate updatedAvailableDate = this.availableDateService.update(availableDate);

        // Map the updated available date to an AvailableDateResponse object
        AvailableDateResponse availableDateResponse = this.modelMapperService.forResponse().map(updatedAvailableDate, AvailableDateResponse.class);

        // Return the updated available date
        return ResultHelper.success(availableDateResponse);
    }
    // code block for available date record deletion
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
   public void deleteAvailableDateById(@PathVariable("id") Long id){
        this.availableDateService.delete(id);
    }
}
