package com.gmail.woosay333.onlinebookstore.repository;

public interface SpecificationProviderManager<T> {
    SpecificationProvider<T> getSpecificationProvider(String parameter);
}
