package com.keiko.stockservice.controller;

import com.keiko.stockservice.service.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

import static com.keiko.stockservice.constants.WebResourceKeyConstants.*;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

public class AbstractCrudController<E, D> {

    @Autowired
    private AbstractCrudService<E> crudService;

    @Autowired
    private Function<E, D> toDtoConverter;

    @PostMapping (value = SAVE)
    public ResponseEntity save (@RequestBody E entity) {
        crudService.save (entity);
        return ResponseEntity.status (CREATED).build ();
    }

    @GetMapping (value = FETCH_BY)
    public ResponseEntity<D> fetchBy (@RequestParam Long id) {
        E entity = crudService.fetchById (id);
        D dto = toDtoConverter.apply (entity);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = FETCH_ALL)
    public ResponseEntity<List<D>> fetchAll () {
        List<E> entities = crudService.fetchAll ();
        List<D> dto = entities.stream ()
                .map (toDtoConverter::apply)
                .collect (toList ());
        return ResponseEntity.ok (dto);
    }

    @DeleteMapping (value = DELETE)
    public ResponseEntity delete (@RequestParam Long id) {
        crudService.delete (id);
        return ResponseEntity.ok ().build ();
    }
}
