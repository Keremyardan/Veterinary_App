package com.dev_patika.veterinaryapp.api;

import com.dev_patika.veterinaryapp.business.abstracts.IAnimalService;
import com.dev_patika.veterinaryapp.business.abstracts.IVaccineService;
import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.core.config.utilities.ResultHelper;
import com.dev_patika.veterinaryapp.core.modelMapper.IModelMapperService;
import com.dev_patika.veterinaryapp.dto.request.customer.CustomerUpdateRequest;
import com.dev_patika.veterinaryapp.dto.request.vaccine.VaccinationRequest;
import com.dev_patika.veterinaryapp.dto.request.vaccine.VaccineSaveRequest;
import com.dev_patika.veterinaryapp.dto.request.vaccine.VaccineUpdateRequest;
import com.dev_patika.veterinaryapp.dto.response.animal.AnimalResponse;
import com.dev_patika.veterinaryapp.dto.response.customer.CustomerResponse;
import com.dev_patika.veterinaryapp.dto.response.vaccine.VaccineResponse;
import com.dev_patika.veterinaryapp.entities.Animal;
import com.dev_patika.veterinaryapp.entities.Customer;
import com.dev_patika.veterinaryapp.entities.Vaccine;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController()
@RequestMapping("/v1/vaccines")
public class VaccineController {
    private final IVaccineService vaccineService;

    private final IAnimalService animalService;

    private  final IModelMapperService modelMapperService;

    public VaccineController(IVaccineService vaccineService, IAnimalService animalService, IModelMapperService modelMapperService) {
        this.vaccineService = vaccineService;
        this.animalService = animalService;
        this.modelMapperService = modelMapperService;
    }

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

    @PostMapping("/vaccinate")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AnimalResponse> vaccinate(@Valid @RequestBody VaccinationRequest vaccinationRequest) {
        Animal animal = this.animalService.getById(vaccinationRequest.getAnimalID());
        Vaccine newVaccine = this.vaccineService.get(vaccinationRequest.getVaccineID());

        // Check if the animal has a vaccine and if its protection end date is after the current date
        if (animal.getVaccine() != null && animal.getVaccine().getProtectionEndDate().isAfter(LocalDate.now())) {
            return ResultHelper.vaccineProtectionDateNotArrived();
        }

        animal.setVaccine(newVaccine);
        Animal updatedAnimal = this.animalService.update(animal.getId(), animal);
        return ResultHelper.success(this.modelMapperService.forResponse().map(updatedAnimal, AnimalResponse.class));
    }

    @GetMapping("/animal/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public List<VaccineResponse> getByAnimalId(@PathVariable("animalId") Long animalId) {

        List<Vaccine> vaccines = this.vaccineService.findByAnimalId(animalId);
        return vaccines.stream()
                .map(vaccine -> this.modelMapperService.forResponse().map(vaccine, VaccineResponse.class))
                .collect(Collectors.toList());
    }
}
