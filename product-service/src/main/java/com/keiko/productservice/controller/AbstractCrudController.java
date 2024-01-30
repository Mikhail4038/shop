package com.keiko.productservice.controller;

import com.keiko.productservice.dto.model.BaseDto;
import com.keiko.productservice.entity.BaseEntity;
import com.keiko.productservice.service.AbstractCrudService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.function.Function;

import static com.keiko.productservice.constants.WebResourceKeyConstants.*;
import static java.util.stream.Collectors.toList;
import static org.springframework.http.HttpStatus.CREATED;

public class AbstractCrudController<E extends BaseEntity, D extends BaseDto> {

    @Autowired
    private AbstractCrudService<E> abstractCrudService;

    @Autowired
    private Function<E, D> toDtoConverter;

    @Autowired
    private Function<D, E> toEntityConverter;

    @PostMapping (value = SAVE)
    public ResponseEntity save (@RequestBody D dto) {
        E entity = toEntityConverter.apply (dto);
        abstractCrudService.save (entity);
        return ResponseEntity.status (CREATED).build ();
    }

    @GetMapping (value = FETCH_BY)
    public ResponseEntity<D> fetchBy (@RequestParam Long id) {
        E entity = abstractCrudService.fetchBy (id);
        D dto = toDtoConverter.apply (entity);
        return ResponseEntity.ok (dto);
    }

    @GetMapping (value = FETCH_ALL)
    public ResponseEntity<List<D>> fetchAll () {
        List<E> entities = abstractCrudService.fetchAll ();
        List<D> dto = entities.stream ()
                .map (toDtoConverter::apply)
                .collect (toList ());
        return ResponseEntity.ok (dto);
    }

    @DeleteMapping (value = DELETE)
    public ResponseEntity delete (@RequestParam Long id) {
        abstractCrudService.delete (id);
        return ResponseEntity.ok ().build ();
    }

    public Function<E, D> getToDtoConverter () {
        return toDtoConverter;
    }
}
