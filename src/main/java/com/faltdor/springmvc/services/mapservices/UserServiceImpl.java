package com.faltdor.springmvc.services.mapservices;

import com.faltdor.springmvc.domain.DomainObject;
import com.faltdor.springmvc.domain.User;
import com.faltdor.springmvc.services.UserService;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Profile("map")
public class UserServiceImpl extends AbstractMapService implements UserService {

    @Override
    public User getById(Long id) {
        return (User) super.getById(id);
    }

    @Override
    public List<DomainObject> listAll() {
        return super.listAll();
    }

    @Override
    public User saveOrUpdate(User domainObject) {
        return (User) super.saveOrUpdate(domainObject);
    }

    @Override
    public void delete(Long id) {
        super.delete(id);
    }

    @Override
    protected void loadDomainObjects() {

    }
}
