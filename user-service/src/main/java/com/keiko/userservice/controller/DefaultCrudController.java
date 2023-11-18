package com.keiko.userservice.controller;

import com.keiko.userservice.service.AbstractCrudService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

import static com.keiko.userservice.constants.WebResourceKeyConstants.*;
import static org.springframework.http.HttpStatus.CREATED;
import static org.springframework.http.HttpStatus.OK;

public class DefaultCrudController<E, D> {

    @Autowired
    private AbstractCrudService<E> crudService;

    @Autowired
    private Function<E, D> toDtoConverter;

    @Autowired
    private Function<D, E> toEntityConverter;

    @PostMapping (value = SAVE)
    public ResponseEntity save (@RequestBody @Valid D dto) {
        E entity = toEntityConverter.apply (dto);
        crudService.save (entity);
        return ResponseEntity.status (CREATED).build ();
    }

    @GetMapping (value = FETCH_BY)
    public ResponseEntity<D> fetchBy (@RequestParam Long id) {
        E entity = crudService.fetchBy (id);
        D dto = toDtoConverter.apply (entity);
        return ResponseEntity.status (OK).body (dto);
    }

    @GetMapping (value = FETCH_ALL)
    public ResponseEntity<List<D>> fetchAll () {
        List<E> entities = crudService.fetchAll ();
        List<D> dto = entities.stream ()
                .map (toDtoConverter::apply)
                .toList ();
        return ResponseEntity.status (OK).body (dto);
    }

    @DeleteMapping (value = DELETE)
    public ResponseEntity delete (@RequestParam Long id) {
        crudService.delete (id);
        return ResponseEntity.status (OK).build ();
    }

    public Function<E, D> getToDtoConverter () {
        return toDtoConverter;
    }
}
