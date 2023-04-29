package com.mjc.school.repository.implementation;

import com.mjc.school.repository.BaseRepository;
import com.mjc.school.repository.model.AuthorModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.TransactionTemplate;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;
import java.util.Optional;

@Repository
public class AuthorRepository implements BaseRepository<AuthorModel, Long> {


    @Autowired
    @Qualifier(value = "tx")
    private final TransactionTemplate transactionTemplate;
    EntityManager entityManager;

    @Autowired
    public AuthorRepository(EntityManager entityManager, TransactionTemplate transactionTemplate) {
        this.entityManager = entityManager;
        this.transactionTemplate = transactionTemplate;
    }

    @Override
    public List<AuthorModel> readAll() {
        String jpql = "select authors from AuthorModel authors order by authors.id";
        TypedQuery<AuthorModel> query = entityManager.createQuery(jpql, AuthorModel.class);
        return query.getResultList();
    }

    @Override
    public Optional<AuthorModel> readById(Long id) {
        AuthorModel authorModel = entityManager.find(AuthorModel.class, id);
        return Optional.ofNullable(authorModel);
    }

    @Override
    public AuthorModel create(AuthorModel entity) {
        transactionTemplate.executeWithoutResult(pop->entityManager.persist(entity));
//        entityManager.persist(entity);
        return entity;
    }

    @Override
    public AuthorModel update(AuthorModel entity) {
        long id = entity.getId();
        if (existById(id)) {
            AuthorModel authorModel = readById(id).get();
            authorModel.setName(entity.getName());
            entityManager.merge(authorModel);
            return authorModel;
        }
        return null;
    }

    @Override
    public boolean deleteById(Long id) {
        if (existById(id)) {
            AuthorModel authorModel = readById(id).get();
            entityManager.remove(authorModel);
            return true;
        }
        return false;
    }

    @Override
    public boolean existById(Long id) {
        return readById(id).isPresent();
    }
}
