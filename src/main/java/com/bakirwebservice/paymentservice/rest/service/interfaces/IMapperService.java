package com.bakirwebservice.paymentservice.rest.service.interfaces;

import java.util.List;

public interface IMapperService {

    <T,D> List<D> modelMapper (List<T> source, Class<D> destination);

    <D> D map(Object source, Class<D> destinationType);

    void map(Object source, Object destination);
}
