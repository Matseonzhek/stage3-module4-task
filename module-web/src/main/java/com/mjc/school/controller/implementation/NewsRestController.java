package com.mjc.school.controller.implementation;

import com.mjc.school.controller.BaseRestController;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.implementation.NewsService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.PagedModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping(value = "/news")
public class NewsRestController implements BaseRestController<NewsDtoRequest, NewsDtoResponse, Long> {


    private final NewsService newsService;


    public NewsRestController(NewsService newsService) {
        this.newsService = newsService;
    }


    @GetMapping
    @Override
    public ResponseEntity<CollectionModel<NewsDtoResponse>> findAll(
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam String sortBy) {
        Sort sort = Sort.by(sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);
        Page<NewsDtoResponse> newsDtoResponsePage = newsService.findAll(pageable);
        for (NewsDtoResponse newsDtoResponse : newsDtoResponsePage) {
            Long id = newsDtoResponse.getId();
            Link selfLink = linkTo(NewsRestController.class).slash(id).withSelfRel();
            newsDtoResponse.add(selfLink);
        }
        Link link = linkTo(NewsRestController.class).withSelfRel();
        return new ResponseEntity<>(PagedModel.of(newsDtoResponsePage, link), HttpStatus.OK);
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
    public ResponseEntity<Boolean> deleteById(@PathVariable Long id) {
        return new ResponseEntity<>(newsService.deleteById(id), HttpStatus.NO_CONTENT);
    }
}
