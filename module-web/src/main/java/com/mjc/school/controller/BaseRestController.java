package com.mjc.school.controller;

import com.mjc.school.service.dto.NewsDtoResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.ResponseEntity;

public interface BaseRestController<T, R, K> {
    ResponseEntity<Page<NewsDtoResponse>> readAll(int size, int page, String sortBy, PagedResourcesAssembler pagedResourcesAssembler);

    ResponseEntity<R> readById(K id);

    ResponseEntity<R> create(T createRequest);

    ResponseEntity<R> update(K id, T updateRequest);

    boolean deleteById(K id);
}
