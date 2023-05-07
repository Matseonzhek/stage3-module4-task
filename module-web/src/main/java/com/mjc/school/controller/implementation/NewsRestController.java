package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.implementation.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.web.PagedResourcesAssembler;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/news")
public class NewsRestController implements BaseRestController<NewsDtoRequest, NewsDtoResponse, Long> {


    private final NewsService newsService;


    public NewsRestController(NewsService newsService) {
        this.newsService = newsService;
    }


    @Override
    public ResponseEntity<Page<NewsDtoResponse>> readAll(int size, int page, String sortBy, PagedResourcesAssembler pagedResourcesAssembler) {
        return null;
    }

    @GetMapping(value = "{id}")
    @Override
    public ResponseEntity<NewsDtoResponse> readById(@PathVariable Long id) {
        NewsDtoResponse newsDtoResponse = newsService.readById(id);
        return new ResponseEntity<>(newsDtoResponse, HttpStatus.OK);
    }

    @PostMapping(consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    @Override
    public ResponseEntity<NewsDtoResponse> create(@RequestBody NewsDtoRequest createRequest) {
        NewsDtoResponse newsDtoResponse = newsService.create(createRequest);
        return new ResponseEntity<>(newsDtoResponse, HttpStatus.CREATED);
    }

    @PutMapping(value = "/{id}", consumes = {"application/JSON"}, produces = {"application/JSON", "application/XML"})
    @Override
    public ResponseEntity<NewsDtoResponse> update(@PathVariable Long id, NewsDtoRequest updateRequest) {
        NewsDtoResponse updatedNews = newsService.update(updateRequest);
        return new ResponseEntity<>(updatedNews, HttpStatus.OK);
    }


    @DeleteMapping(value = "/{id}")
    @Override
    public boolean deleteById(@PathVariable Long id) {
        return newsService.deleteById(id);
    }
}
