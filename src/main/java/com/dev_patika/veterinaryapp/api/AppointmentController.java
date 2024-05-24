package com.dev_patika.veterinaryapp.api;

import com.dev_patika.veterinaryapp.business.abstracts.IAnimalService;
import com.dev_patika.veterinaryapp.business.abstracts.IAppointmentService;
import com.dev_patika.veterinaryapp.business.abstracts.IAvailableDateService;
import com.dev_patika.veterinaryapp.core.config.result.Result;
import com.dev_patika.veterinaryapp.core.config.result.ResultData;
import com.dev_patika.veterinaryapp.core.config.utilities.ResultHelper;
import com.dev_patika.veterinaryapp.core.modelMapper.IModelMapperService;
import com.dev_patika.veterinaryapp.dto.request.appointment.AppointmentSaveRequest;
import com.dev_patika.veterinaryapp.dto.request.appointment.AppointmentUpdateRequest;
import com.dev_patika.veterinaryapp.dto.response.CursorResponse;
import com.dev_patika.veterinaryapp.dto.response.appointment.AppointmentResponse;
import com.dev_patika.veterinaryapp.entities.Animal;
import com.dev_patika.veterinaryapp.entities.Appointment;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/appointments")
public class AppointmentController { // This class contains methods that control the Appointment endpoints.
    private final IAppointmentService appointmentService;
    private final IAvailableDateService availableDateService;
    private final IAnimalService animalService;
    private final IModelMapperService modelMapper;

    public AppointmentController(IAppointmentService appointmentService, IAvailableDateService availableDateService, IAnimalService animalService, IModelMapperService modelMapper) {
        this.appointmentService = appointmentService;
        this.availableDateService = availableDateService;
        this.animalService = animalService;
        this.modelMapper = modelMapper;
    }

    @PostMapping("/appointment")
    @ResponseStatus(HttpStatus.CREATED)
    public ResultData<AppointmentResponse> create(@Valid @RequestBody AppointmentSaveRequest appointmentSaveRequest) {
        // This method saves the appointment.
        Appointment appointment = this.modelMapper.forRequest().map(appointmentSaveRequest, Appointment.class);

        // Call the save method in the AppointmentManager class and return the result
        ResultData<Appointment> resultData = this.appointmentService.create(appointment, appointmentSaveRequest.getDate_time());
        if (!resultData.isSuccess()) {
            return new ResultData<>(false, resultData.getMessage(), "400", null);
        }
        AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(resultData.getData(), AppointmentResponse.class);
        return ResultHelper.success(appointmentResponse);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> get(@PathVariable("id") Long id) {
        // This method gets the appointment by id.
        Appointment appointment = this.appointmentService.get(id);
        return ResultHelper.success(this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    public ResultData<CursorResponse<AppointmentResponse>> cursor(
            // This method returns the appointments with pagination.
            @RequestParam(value = "page", required = false, defaultValue = "0") int page,
            @RequestParam(value = "size", required = false, defaultValue = "10") int size)
    {
        Page<Appointment> appointments = this.appointmentService.cursor(page, size);
        Page<AppointmentResponse> appointmentResponsePage = appointments
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class));

        return ResultHelper.cursor(appointmentResponsePage);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ResultData<AppointmentResponse> update(@Valid @RequestBody AppointmentUpdateRequest appointmentUpdateRequest) {
        // Map the request to an Appointment object
        Appointment appointment = this.modelMapper.forRequest().map(appointmentUpdateRequest, Appointment.class);

        // Call the update method in the AppointmentManager class
        ResultData<Appointment> resultData = this.appointmentService.update(appointmentUpdateRequest.getId(), appointment);

        // If the update was not successful, return the error message
        if (!resultData.isSuccess()) {
            return new ResultData<>(false, resultData.getMessage(), "400", null);
        }

        // Map the updated appointment to an AppointmentResponse object
        AppointmentResponse appointmentResponse = this.modelMapper.forResponse().map(resultData.getData(), AppointmentResponse.class);

        // Return the updated appointment
        return ResultHelper.success(appointmentResponse);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Result delete(@PathVariable("id") Long id) {
        // This method deletes the appointment by id.
        this.appointmentService.delete(id);
        return ResultHelper.ok();
    }

    @GetMapping("/doctor/{doctorId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentResponse> getByDoctorIdAndAppointmentDateBetween(
            // This method returns the appointments by doctor id.
            @PathVariable("doctorId") Long doctorId,
            @RequestParam("start_date_time") LocalDateTime startDateTime,
            @RequestParam("end_date_time") LocalDateTime endDateTime) {
        List<Appointment> appointments = this.appointmentService.findByDoctorIdAndAppointmentDateBetween(doctorId, startDateTime, endDateTime);
        return appointments.stream()
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
    }

    @GetMapping("/animal/{animalId}")
    @ResponseStatus(HttpStatus.OK)
    public List<AppointmentResponse> getByAnimalIdAndAppointmentDateBetween(
            // This method returns the appointments by animal id.
            @PathVariable("animalId") Long animalId,
            @RequestParam("start_date_time") LocalDateTime startDateTime,
            @RequestParam("end_date_time") LocalDateTime endDateTime) {
        Animal animal = this.animalService.getById(animalId); // Assuming you have a method to get Animal by id
        List<Appointment> appointments = this.appointmentService.findByAppointmentDateBetweenAndAnimal(startDateTime, endDateTime, animal);
        return appointments.stream()
                .map(appointment -> this.modelMapper.forResponse().map(appointment, AppointmentResponse.class))
                .collect(Collectors.toList());
    }
    }
