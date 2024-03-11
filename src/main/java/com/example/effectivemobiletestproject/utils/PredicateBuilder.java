package com.example.effectivemobiletestproject.utils;

public interface PredicateBuilder<R,T> {

    R build(T requestFilter);
}
