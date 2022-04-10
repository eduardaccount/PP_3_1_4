package ru.kata.spring.boot_security.demo.dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import ru.kata.spring.boot_security.demo.model.Role;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

@Repository
public class RoleDaoImpl {

    @PersistenceContext
    private final EntityManager entityManager;

    @Autowired
    public RoleDaoImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public Role getRoleByName(String auth) {
        TypedQuery<Role> query = entityManager.createQuery(
                "select a from Role a where a.name = :auth ", Role.class);
        query.setParameter("auth", auth);
        return query.getSingleResult();
    }
}
