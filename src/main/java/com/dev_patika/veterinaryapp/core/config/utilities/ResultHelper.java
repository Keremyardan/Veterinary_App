package com.dev_patika.veterinaryapp.core.config.utilities;

/*
public class ResultHelper { // This class contains helper methods for Result objects.

    public static <T> ResultData<T> created(T data){
        return new ResultData<>(true, Msg.CREATED, "201", data);
    }

    public static <T> ResultData<T> validateError(T data){
        return new ResultData<>(false, Msg.VALIDATE_ERROR, "400", data);
    }

    public static <T> ResultData<T> success(T data){
        return new ResultData<>(true, Msg.OK, "200", data);
    }

    public static Result ok(){
        return new Result(true, Msg.OK, "200");
    }

    public static Result notFoundError(String message){
        return new Result(false, message, "404");
    }

    public static <T> ResultData<CursorResponse<T>> cursor(Page<T> pageData){
        CursorResponse<T> cursor = new CursorResponse<>();
        cursor.setItems(pageData.getContent());
        cursor.setPage(pageData.getNumber());
        cursor.setSize(pageData.getSize());
        cursor.setTotalElements(pageData.getTotalElements());
        return ResultHelper.success(cursor);
    }

    public static <T> ResultData<T> vaccineProtectionDateNotArrived() {
        return new ResultData<>(false, Msg.VACCINE_PROTECTION_DATE_NOT_ARRIVED, "422", null);
    }

    public static <T> ResultData<T> doctorNotAvailable() {
        return new ResultData<>(false, Msg.DOCTOR_NOT_AVAILABLE, "422", null);
    }

    public static <T> ResultData <T> animalNotFoundError() {
        return new ResultData<>(false, Msg.ANIMAL_NOT_FOUND, "404", null);
    }


    public static <T> ResultData<T> EmailExists() {
        return new ResultData<>(false, Msg.SAME_EMAIL, "400", null);
    }

    public static <T> ResultData<T> PhoneExists() {
        return new ResultData<>(false, Msg.SAME_PHONE, "400", null);
    }

    public static <T> ResultData<T> vaccineNameAndCodeExists() {
        return new ResultData<>(false, Msg.SAME_VACCINE_NAME_AND_CODE, "400", null);
    }

    public static <T>ResultData<T> appointmentAlreadyExists() {
        return new ResultData<>(false, Msg.APPOINTMENT_ALREADY_EXISTS, "400", null);
    }
}

 */