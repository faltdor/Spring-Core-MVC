package com.faltdor.springmvc.services.jpaservices;

import com.faltdor.springmvc.domain.User;
import com.faltdor.springmvc.services.UserService;
import com.faltdor.springmvc.services.security.EncryptionService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import java.util.List;

@Service
@Profile("jpadao")
public class UserServiceJpaDaoImpl extends AbstractJpaDaoService implements UserService {

    private  final EncryptionService encryptionService;

    public UserServiceJpaDaoImpl(EncryptionService encryptionService) {
        this.encryptionService = encryptionService;
    }

    @Override
    public List<User> listAll() {
        EntityManager em = emf.createEntityManager();

        return em.createQuery("from User", User.class).getResultList();
    }

    @Override
    public User getById(Long id) {
        EntityManager em = emf.createEntityManager();

        return em.find(User.class, id);
    }

    @Override
    public User saveOrUpdate(User domainObject) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();

        if(domainObject.getPassword() != null){
            domainObject.setEncryptedPassword(encryptionService.encryptString(domainObject.getPassword()));
        }

        User saveduser = em.merge(domainObject);
        em.getTransaction().commit();

        return saveduser;
    }

    @Override
    public void delete(Long id) {
        EntityManager em = emf.createEntityManager();

        em.getTransaction().begin();
        em.remove(em.find(User.class, id));
        em.getTransaction().commit();
    }
}
