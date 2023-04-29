package com.mjc.school.service.implementation;

import com.mjc.school.repository.implementation.TagRepository;
import com.mjc.school.repository.model.TagModel;
import com.mjc.school.service.BaseService;
import com.mjc.school.service.annotation.Validate;
import com.mjc.school.service.dto.TagDtoRequest;
import com.mjc.school.service.dto.TagDtoResponse;
import com.mjc.school.service.exception.NotFoundException;
import com.mjc.school.service.interfaces.TagMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.mjc.school.service.constants.Constants.TAG_NOT_EXIST;

@Service
public class TagService implements BaseService<TagDtoRequest, TagDtoResponse, Long> {

    private final TagRepository tagRepository;

    @Autowired
    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }


    @Override
    public List<TagDtoResponse> readAll() {
        return TagMapper.INSTANCE.listTagToTagDtoResponse(tagRepository.readAll());
    }

    @Validate
    @Override
    public TagDtoResponse readById(Long id) {
        if (tagRepository.existById(id)) {
            return TagMapper.INSTANCE.tagToTagDtoResponse(tagRepository.readById(id).get());
        } else throw new NotFoundException(TAG_NOT_EXIST);
    }

    @Validate
    @Transactional
    @Override
    public TagDtoResponse create(TagDtoRequest createRequest) {
        return TagMapper.INSTANCE.tagToTagDtoResponse(tagRepository.create(TagMapper.INSTANCE.tagDtoRequestToTag(createRequest)));
    }

    @Validate
    @Transactional
    @Override
    public TagDtoResponse update(TagDtoRequest updateRequest) {
        if (tagRepository.existById(updateRequest.getId())) {
            TagModel tagModel = tagRepository.readById(updateRequest.getId()).get();
            tagModel.setName(updateRequest.getName());
            TagModel updatedTagModel = tagRepository.update(tagModel);
            return TagMapper.INSTANCE.tagToTagDtoResponse(updatedTagModel);
        } else throw new NotFoundException(TAG_NOT_EXIST);
    }

    @Validate
    @Transactional
    @Override
    public boolean deleteById(Long id) {
        if (tagRepository.existById(id)) {
            tagRepository.deleteById(id);
            return true;
        } else throw new NotFoundException(TAG_NOT_EXIST);
    }
}
