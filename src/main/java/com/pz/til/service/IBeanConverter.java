package com.pz.til.service;

public interface IBeanConverter<T1, T2> {
    T2 convertFromDto(T1 dto);
    T1 convertFromModel(T2 model);
}
