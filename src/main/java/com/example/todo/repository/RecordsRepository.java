package com.example.todo.repository;

import com.example.todo.entity.Record;
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


@RepositoryRestResource(path = "question", collectionResourceRel = "question")
public interface RecordsRepository extends JpaRepository<Record, Long>, JpaSpecificationExecutor<Record>, QuerydslPredicateExecutor<Record> {

    Page<Record> findByIdIn(@Param(value = "id") List<Long> eventid, Pageable pageable);

    @Query(name="Record.findByRdate",
            nativeQuery = true)
    List<Record> findByRdate(@Param("date") String rdate);

    Page<Record> findAll(Pageable pageable);

    @Query(name="Record.findById", nativeQuery = true)
    @RestResource(exported = false)
    Optional<Record> findById(@Param("id") long id);

}

