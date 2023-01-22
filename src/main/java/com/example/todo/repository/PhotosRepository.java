package com.example.todo.repository;

import com.example.todo.entity.Photo;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.Collection;
import java.util.List;
import java.util.Optional;


@RepositoryRestResource(path = "photo", collectionResourceRel = "photo")
public interface PhotosRepository extends JpaRepository<Photo, Long>, JpaSpecificationExecutor<Photo>, QuerydslPredicateExecutor<Photo> {

    Page<Photo> findByIdIn(@Param(value = "id") List<Long> eventid, Pageable pageable);

    Page<Photo> findByNameIn(
            @Param("name") Collection<String> names, Pageable pageable);

    @Query(name="Question.findByDescription",
            nativeQuery = true)
    List<Photo> findByDescription(@Param("description") String description);

    @Query(name="Question.findByDatetoshow",
            nativeQuery = true)
    List<Photo> findByDatetoshow(@Param("datetoshow") long date);
    
    @Query(name="Question.findByPtype",
            nativeQuery = true)
    List<Photo> findByPtype(@Param("ptype") String type);

    Page<Photo> findAll(Pageable pageable);

    @Query(name="Photo.findById", nativeQuery = true)
    @RestResource(exported = false)
    Optional<Photo> findById(@Param("id") long id);

}

