package org.example.dao;

import java.util.List;

import org.example.model.AbstractEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;

@Getter
public abstract class AbstractDAO<T extends AbstractEntity> {

    protected final SessionFactory sessionFactory;

    @Autowired
    protected AbstractDAO(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    protected AbstractEntity save(AbstractEntity entity) {
        Session session = sessionFactory.getCurrentSession();
        session.persist(entity);
        return entity;
    }

    protected AbstractEntity findById(Class<?> entityClass, long id) {
        Session session = sessionFactory.getCurrentSession();
        return (AbstractEntity) session.get(entityClass, id);
    }

    protected AbstractEntity update(AbstractEntity entity) {
        Session session = sessionFactory.getCurrentSession();
        return session.merge(entity);
    }

    protected boolean delete(AbstractEntity entity) {
        Session session = sessionFactory.getCurrentSession();
        try {
            session.remove(entity);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    protected List<T> findAll(Class<T> entityClass) {
        Session session = sessionFactory.getCurrentSession();
        String entityName = entityClass.getCanonicalName();
        String queryHQL = "FROM " + entityName;
        Query<T> query = session.createQuery(queryHQL, entityClass);
        return query.getResultList();
    }
}
