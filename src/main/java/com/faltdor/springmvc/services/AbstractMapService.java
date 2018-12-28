package com.faltdor.springmvc.services;

import com.faltdor.springmvc.domain.DomainObject;

import javax.validation.ValidationException;
import java.util.*;

public abstract class AbstractMapService {

    protected Map<Long, DomainObject> domainObjectMap;


    public AbstractMapService() {
        domainObjectMap = new HashMap<>();
        loadDomainObjects();
    }

    public List<DomainObject> listAll() {
        return new ArrayList<>(domainObjectMap.values());
    }

    public DomainObject getById(Long id) {
        return domainObjectMap.get(id);
    }

    public DomainObject saveOrUpdate(DomainObject domainObject) {
        return Optional.ofNullable(domainObject)
                .filter(Objects::nonNull)
                .map(p -> {
                    if (p.getId() == null){
                        p.setId(getNextKey());
                    }
                    domainObjectMap.put(p.getId(), p);
                    return p;
                }).orElseThrow(ValidationException::new);
    }

    public void delete(Long id) {
        domainObjectMap.remove(id);
    }

    private synchronized Long getNextKey(){
        return Collections.max(domainObjectMap.keySet()) + 1;
    }

    protected abstract void loadDomainObjects();

}
