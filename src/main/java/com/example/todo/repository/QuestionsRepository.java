package com.example.todo.repository;

import com.example.todo.entity.Question;
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
public interface QuestionsRepository extends JpaRepository<Question, Long>, JpaSpecificationExecutor<Question>, QuerydslPredicateExecutor<Question> {

    Page<Question> findByQidIn(@Param(value = "qid") List<Long> eventid, Pageable pageable);

    Page<Question> findByTestnameIn(
            @Param("testname") Collection<String> names, Pageable pageable);

    @Query(name="Question.findByCategory",
            nativeQuery = true)
    List<Question> findByCategory(@Param("category") String category);

    Page<Question> findAll(Pageable pageable);

    @Query(name="Question.findByQid", nativeQuery = true)
    @RestResource(exported = false)
    Optional<Question> findByQid(@Param("qid") long qid);

}

