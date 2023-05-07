package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.CommentModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class CommentRepository implements BaseRepository<CommentModel, Long> {

    private final EntityManager entityManager;

    @Autowired
    public CommentRepository(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<CommentModel> readAll() {
        String jpql = " select comments from CommentModel comments order by comments.id ";
        TypedQuery<CommentModel> query = entityManager.createQuery(jpql, CommentModel.class);
        return query.getResultList();
    }

    @Override
    public Optional<CommentModel> readById(Long id) {
        CommentModel commentModel = entityManager.find(CommentModel.class, id);
        return Optional.ofNullable(commentModel);
    }

    @Override
    public CommentModel create(CommentModel entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public CommentModel update(CommentModel entity) {
        long id = entity.getId();
        if (existById(id)) {
            CommentModel updatedComment = readById(id).get();
            updatedComment.setContent(entity.getContent());
            entityManager.merge(updatedComment);
            return updatedComment;
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            entityManager.remove(readById(id));
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }
}
