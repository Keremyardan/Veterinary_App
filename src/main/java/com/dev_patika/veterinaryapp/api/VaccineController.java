package com.dev_patika.veterinaryapp.api;

import com.dev_patika.veterinaryapp.business.abstracts.IAnimalService;
import com.dev_patika.veterinaryapp.business.abstracts.IVaccineService;
import com.dev_patika.veterinaryapp.core.config.result.Result;
import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.core.config.utilities.ResultHelper;
import com.dev_patika.veterinaryapp.core.exceptions.ProtectionDateIsNotExpiredException;
import com.dev_patika.veterinaryapp.core.modelMapper.IModelMapperService;
import com.dev_patika.veterinaryapp.dto.request.customer.CustomerUpdateRequest;
import com.dev_patika.veterinaryapp.dto.request.vaccine.VaccinationRequest;
import com.dev_patika.veterinaryapp.dto.request.vaccine.VaccineSaveRequest;
import com.dev_patika.veterinaryapp.dto.request.vaccine.VaccineUpdateRequest;
import com.dev_patika.veterinaryapp.dto.response.CursorResponse;
import com.dev_patika.veterinaryapp.dto.response.animal.AnimalResponse;
import com.dev_patika.veterinaryapp.dto.response.customer.CustomerResponse;
import com.dev_patika.veterinaryapp.dto.response.vaccine.VaccineResponse;
import com.dev_patika.veterinaryapp.entities.Animal;
import com.dev_patika.veterinaryapp.entities.Customer;
import com.dev_patika.veterinaryapp.entities.Vaccine;
import org.springframework.data.domain.Page;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

//endpoint for vaccine entity
@RestController()
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;

    private final IAnimalService animalService;

    private  final IModelMapperService modelMapperService;
//constructor with parameters
    public VaccineController(IVaccineService vaccineService, IAnimalService animalService, IModelMapperService modelMapperService) {
        this.vaccineService = vaccineService;
        this.animalService = animalService;
        this.modelMapperService = modelMapperService;
    }

    // save method for vaccines
    @PostMapping("/vaccine")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> save (@Valid @RequestBody VaccineSaveRequest vaccineSaveRequest) {
        // This method saves the vaccine.
        Vaccine saveVaccine = this.modelMapperService.forRequest().map(vaccineSaveRequest, Vaccine.class);
        ResultData<Vaccine> result = this.vaccineService.save(saveVaccine);

        // Check if a vaccine with the same name or code already exists
        if (!result.isSuccess()) {
            return new ResultData<>(false, result.getMessage(), "400", null);
        }

        return ResultHelper.created(this.modelMapperService.forResponse().map(result.getData(), VaccineResponse.class));
    }

    //update method for vaccines
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> update(@Valid @RequestBody VaccineUpdateRequest vaccineUpdateRequest) {

        Vaccine vaccine = this.modelMapperService.forRequest().map(vaccineUpdateRequest, Vaccine.class);


        ResultData<Vaccine> resultData = this.vaccineService.update(vaccineUpdateRequest.getId(), vaccine);


        if (!resultData.isSuccess()) {
            return new ResultData<>(false, resultData.getMessage(), "400", null);
        }


        VaccineResponse vaccineResponse = this.modelMapperService.forResponse().map(resultData.getData(), VaccineResponse.class);

        return ResultHelper.success(vaccineResponse);
    }

    //code block for vaccination process
    @PostMapping("/vaccinate")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> vaccinate(@Valid @RequestBody VaccinationRequest vaccinationRequest) {
        Animal animal = this.animalService.getById(vaccinationRequest.getAnimalID());
        Vaccine newVaccine = this.vaccineService.get(vaccinationRequest.getVaccineID());
        Vaccine vaccine = this.vaccineService.findVaccineByNameAndCode(newVaccine.getName(),newVaccine.getCode());
        if (vaccine != null) {
            LocalDate now = LocalDate.now();
            if (!vaccine.getProtectionEndDate().isAfter(now)) {
                throw new ProtectionDateIsNotExpiredException("Expiration date is not expired yet");
            }
        }
        // Check if the animal has a vaccine and if its protection end date is after the current date
        if (animal.getVaccine() != null && animal.getVaccine().getProtectionEndDate().isAfter(LocalDate.now())) {
            return ResultHelper.vaccineProtectionDateNotArrived();
        }

        animal.setVaccine(newVaccine);
        Animal updatedAnimal = this.animalService.update(animal.getId(), animal);
        return ResultHelper.success(this.modelMapperService.forResponse().map(updatedAnimal, AnimalResponse.class));
    }

    //get method by vaccine id
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<VaccineResponse> get(@PathVariable("id") Long id) {
        // This method gets the vaccine by id.
        Vaccine vaccine = this.vaccineService.get(id);
        return ResultHelper.success(this.modelMapperService.forResponse().map(vaccine, VaccineResponse.class));
    }

    // this block provides a pagination system which controls large amount of
    // data returns and reformat them.
    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<VaccineResponse>> cursor(
            // This method returns the vaccines with pagination.
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size)
    {
        Page<Vaccine> vaccines = this.vaccineService.cursor(page, size);
        Page<VaccineResponse> vaccineResponsePage = vaccines
                .map(vaccine -> this.modelMapperService.forResponse().map(vaccine, VaccineResponse.class));

        return ResultHelper.cursor(vaccineResponsePage);
    }

    // vaccine getter by animal id
    @GetMapping("/animal/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public List<VaccineResponse> getByAnimalId(@PathVariable("animalId") Long animalId) {

        List<Vaccine> vaccines = this.vaccineService.findByAnimalId(animalId);
        return vaccines.stream()
                .map(vaccine -> this.modelMapperService.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
    }

    // delete method for vaccine
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Long id) {
        this.vaccineService.delete(id);
    }

    //protection date getters
    @GetMapping("/protection-dates")
    @ResponseStatus(HttpStatus.OK)
    public List<VaccineResponse> getByProtectionEndDateBetween(
            @RequestParam("start_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam("end_date") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        // This method returns the vaccines by protection end date between the given dates.
        List<Vaccine> vaccines = this.vaccineService.findByProtectionEndDateBetween(startDate, endDate);
        return vaccines.stream()
                .map(vaccine -> this.modelMapperService.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
    }
}
