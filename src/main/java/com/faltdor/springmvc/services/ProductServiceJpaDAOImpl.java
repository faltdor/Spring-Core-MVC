package com.faltdor.springmvc.services;

import com.faltdor.springmvc.domain.Product;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import java.util.List;

@Service
@Profile("jpadao")
public class ProductServiceJpaDAOImpl implements ProductService {

    private final EntityManagerFactory entityManagerFactory;

    public ProductServiceJpaDAOImpl(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    @Override
    public Product getById(Long id) {
        EntityManager em = getEntityManager();
        return em.find(Product.class,id);
    }

    @Override
    public List<?> listAll() {
        EntityManager em = getEntityManager();
        return em.createQuery("from Product", Product.class).getResultList();
    }

    @Override
    public Product saveOrUpdate(Product domainObject) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        Product saved = em.merge(domainObject);
        em.getTransaction().commit();
        return saved;
    }

    @Override
    public void delete(Long id) {
        EntityManager em = getEntityManager();
        em.getTransaction().begin();
        em.remove(em.find(Product.class, id));
        em.getTransaction().commit();
    }

    public EntityManager getEntityManager() {
        return entityManagerFactory.createEntityManager();
    }
}