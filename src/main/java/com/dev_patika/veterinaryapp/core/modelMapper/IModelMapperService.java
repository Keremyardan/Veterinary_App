package com.dev_patika.veterinaryapp.core.modelMapper;

import org.modelmapper.ModelMapper;

public interface IModelMapperService { // This interface is used for ModelMapperService class.
    ModelMapper forRequest();
    ModelMapper forResponse();
}
