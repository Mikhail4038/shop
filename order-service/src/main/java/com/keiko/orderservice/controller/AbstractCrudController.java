package com.keiko.orderservice.controller;

import com.keiko.orderservice.entity.BaseEntity;
import com.keiko.orderservice.service.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.keiko.orderservice.constants.WebResourceKeyConstants.*;
import static org.springframework.http.HttpStatus.CREATED;

public class AbstractCrudController<T extends BaseEntity> {

    @Autowired
    private AbstractCrudService<T> crudService;

    @PostMapping (value = SAVE)
    public ResponseEntity save (@RequestBody T entity) {
        crudService.save (entity);
        return ResponseEntity.status (CREATED).build ();
    }

    @DeleteMapping (value = DELETE)
    public ResponseEntity save (@RequestParam Long id) {
        crudService.delete (id);
        return ResponseEntity.ok ().build ();
    }

    @GetMapping (value = FETCH_BY)
    public ResponseEntity<T> fetchBy (@RequestParam Long id) {
        T entity = crudService.fetchBy (id);
        return ResponseEntity.ok (entity);
    }

    @GetMapping (value = FETCH_ALL)
    public ResponseEntity<List<T>> fetchAll () {
        List<T> entities = crudService.fetchAll ();
        return ResponseEntity.ok (entities);
    }
}
