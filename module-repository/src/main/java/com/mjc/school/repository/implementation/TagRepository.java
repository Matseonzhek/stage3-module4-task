package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.TagModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class TagRepository implements BaseRepository<TagModel, Long> {

    private final EntityManager entityManager;

    @Autowired
    public TagRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<TagModel> readAll() {
        String jpql = "select tag from TagModel tag order by tag.id";
        TypedQuery<TagModel> query = entityManager.createQuery(jpql, TagModel.class);
        return query.getResultList();
    }

    @Override
    public Optional<TagModel> readById(Long id) {
        TagModel tagModel = entityManager.find(TagModel.class, id);
        return Optional.ofNullable(tagModel);
    }

    @Override
    public TagModel create(TagModel entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public TagModel update(TagModel entity) {
        if (existById(entity.getId())) {
            TagModel updatedTagModel = readById(entity.getId()).get();
            updatedTagModel.setName(entity.getName());
            entityManager.persist(updatedTagModel);
            return updatedTagModel;
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            TagModel tagModel = readById(id).get();
            entityManager.remove(tagModel);
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }
}
