package com.dev_patika.veterinaryapp.core.modelMapper;

import org.modelmapper.ModelMapper;

public interface IModelMapperService { // this interface is used for ModelMapperService class.
    ModelMapper forRequest();
    ModelMapper forResponse();
}
