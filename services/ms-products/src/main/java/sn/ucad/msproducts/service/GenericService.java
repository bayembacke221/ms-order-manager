package sn.ucad.msproducts.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.transaction.annotation.Transactional;
import sn.ucad.msproducts.exception.ResourceNotFoundException;
import sn.ucad.msproducts.repository.GenericRepository;

import java.io.Serializable;
import java.util.List;

public abstract class GenericService<T, ID extends Serializable> {

    protected final GenericRepository<T, ID> repository;

    public GenericService(GenericRepository<T, ID> repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
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
