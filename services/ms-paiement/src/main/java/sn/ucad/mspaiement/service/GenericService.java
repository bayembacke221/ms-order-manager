package sn.ucad.mspaiement.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import sn.ucad.mspaiement.exception.ResourceNotFoundException;
import sn.ucad.mspaiement.repository.GenericRepository;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class GenericService<T, ID extends Serializable> {

    protected final GenericRepository<T, ID> repository;

    public GenericService(GenericRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<T> findAll(boolean pageable, int page, int size) {
        if (!pageable){
            List<T> allItems = repository.findAll();
            return new PageImpl<>(allItems, Pageable.unpaged(), allItems.size());

        }else {
            Pageable paging = PageRequest.of(page - 1, size);
            return repository.findAll(paging);
        }
    }

    @Transactional(readOnly = true)
    public T findById(ID id) {
        return repository.findById(id).orElseThrow(() -> new ResourceNotFoundException("La ressource avec l'id " + id + " n'existe pas"));
    }

    @Transactional
    public T save(T entity) {
        return repository.save(entity);
    }

    @Transactional
    public void deleteById(ID id) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("La ressource avec l'id " + id + " n'existe pas");
        }
        repository.deleteById(id);
    }

    @Transactional
    public T update(ID id,T entity) {
        if (!repository.existsById(id)) {
            throw new ResourceNotFoundException("La ressource avec l'id " + id + " n'existe pas");
        }
        return repository.save(entity);
    }

}
