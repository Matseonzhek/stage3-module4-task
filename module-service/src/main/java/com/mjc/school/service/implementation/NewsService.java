package com.mjc.school.service.implementation;


import com.mjc.school.repository.implementation.AuthorRepository;
import com.mjc.school.repository.implementation.NewsRepository;
import com.mjc.school.repository.implementation.TagRepository;
import com.mjc.school.repository.model.AuthorModel;
import com.mjc.school.repository.model.NewsModel;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.annotation.Validate;
import com.mjc.school.service.dto.AuthorDtoResponse;
import com.mjc.school.service.dto.NewsDtoRequest;
import com.mjc.school.service.dto.NewsDtoResponse;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.interfaces.AuthorMapper;
import com.mjc.school.service.interfaces.NewsMapper;
import com.mjc.school.service.interfaces.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static com.mjc.school.service.constants.Constants.*;

@Service
public class
NewsService implements BaseService<NewsDtoRequest, NewsDtoResponse, Long> {

    private final NewsRepository newsRepository;
    private final AuthorRepository authorRepository;
    private final TagRepository tagRepository;

    @Autowired
    public NewsService(NewsRepository newsRepository, AuthorRepository authorRepository, TagRepository tagRepository) {
        this.newsRepository = newsRepository;
        this.authorRepository = authorRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<NewsDtoResponse> readAll() {
        return NewsMapper.INSTANCE.listNewsToNewsDtoResponse(newsRepository.readAll());
    }

    @Validate(value = "checkNewsId")
    @Override
    public NewsDtoResponse readById(Long id) {
        if (newsRepository.existById(id)) {
            Optional<NewsModel> optionalNewsModel = newsRepository.readById(id);
            return NewsMapper.INSTANCE.newsToNewsDtoResponse(optionalNewsModel.get());
        } else {
            throw new NotFoundException(NEWS_NOT_EXIST);
        }
    }

    @Override
    @Transactional
    @Validate(value = "checkNews")
    public NewsDtoResponse create(NewsDtoRequest createRequest) {
        if (authorRepository.existById(createRequest.getAuthorId())) {
            NewsModel newsModel = NewsMapper.INSTANCE.newsDtoRequestToNews(createRequest);
            if (tagRepository.existById(createRequest.getTagId())) {
                TagModel tagModel = tagRepository.readById(createRequest.getTagId()).get();
                newsModel.addTag(tagModel);
            } else {
                throw new NotFoundException(TAG_NOT_EXIST);
            }
            AuthorModel authorModel = authorRepository.readById(createRequest.getAuthorId()).get();
            newsModel.setAuthorModel(authorModel);
            NewsModel createdNewsModel = newsRepository.create(newsModel);
            return NewsMapper.INSTANCE.newsToNewsDtoResponse(createdNewsModel);
        } else throw new NotFoundException(AUTHOR_NOT_EXIST);

    }

    @Transactional
    @Validate(value = "checkNews")
    @Override
    public NewsDtoResponse update(NewsDtoRequest updateRequest) {
        if (newsRepository.existById(updateRequest.getId())) {
            NewsModel newsModel = newsRepository.readById(updateRequest.getId()).get();
            if (authorRepository.existById(updateRequest.getAuthorId())) {
                AuthorModel authorModel = authorRepository.readById(updateRequest.getAuthorId()).get();
                newsModel.setTitle(updateRequest.getTitle());
                newsModel.setContent(updateRequest.getContent());
                newsModel.setAuthorModel(authorModel);
                if (tagRepository.existById(updateRequest.getTagId())) {
                    TagModel tagModel = tagRepository.readById(updateRequest.getTagId()).get();
                    newsModel.addTag(tagModel);
                } else {
                    throw new NotFoundException(TAG_NOT_EXIST);
                }
                NewsModel updatedNewsModel = newsRepository.update(newsModel);
                return NewsMapper.INSTANCE.newsToNewsDtoResponse(updatedNewsModel);
            } else throw new NotFoundException(AUTHOR_NOT_EXIST);
        } else {
            throw new NotFoundException(NEWS_NOT_EXIST);
        }
    }

    @Transactional
    @Validate(value = "checkNewsId")
    @Override
    public boolean deleteById(Long id) {
        if (newsRepository.existById(id)) {
            newsRepository.deleteById(id);
            return true;
        } else {
            throw new NotFoundException(NEWS_NOT_EXIST);
        }
    }


    public AuthorDtoResponse getAuthorByNewsId(Long id) {
        if (newsRepository.existById(id)) {
            return AuthorMapper.INSTANCE.authorModelToAuthorDtoResponse(newsRepository.getAuthorByNewsId(id));
        } else throw new NotFoundException(NEWS_NOT_EXIST);
    }

    public List<TagDtoResponse> getTagByNewsId(Long id) {
        if (newsRepository.existById(id)) {
            return TagMapper.INSTANCE.listTagToTagDtoResponse(newsRepository.getTagByNewsId(id));
        } else throw new NotFoundException(NEWS_NOT_EXIST);
    }

    public List<NewsDtoResponse> getNewsByOption(String tagName, Long tagId, String authorName, String title, String content) {
        return NewsMapper.INSTANCE.listNewsToNewsDtoResponse
                (newsRepository.getNewsByOption(tagName, tagId, authorName, title, content));
    }
}
