package com.example.todo.repository;

import com.example.todo.entity.Test;
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


@RepositoryRestResource(path = "test", collectionResourceRel = "test")
public interface TestsRepository extends JpaRepository<Test, Long>, JpaSpecificationExecutor<Test>, QuerydslPredicateExecutor<Test> {

    Page<Test> findByIdIn(@Param(value = "id") List<Long> eventid, Pageable pageable);

    Page<Test> findByNameIn(
            @Param("name") Collection<String> names, Pageable pageable);

    @Query(name="Test.findByName",
            nativeQuery = true)
    List<Test> findByName(@Param("name") String name);

    Page<Test> findAll(Pageable pageable);

    @Query(name="Test.findById", nativeQuery = true)
    @RestResource(exported = false)
    Optional<Test> findById(@Param("id") long id);

}

