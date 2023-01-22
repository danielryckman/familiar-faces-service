package com.example.todo.repository;

import com.example.todo.entity.Familymember;
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


@RepositoryRestResource(path = "familymember", collectionResourceRel = "familymember")
public interface FamilymembersRepository extends JpaRepository<Familymember, Long>, JpaSpecificationExecutor<Familymember>, QuerydslPredicateExecutor<Familymember> {

    Page<Familymember> findByIdIn(@Param(value = "id") List<Long> eventid, Pageable pageable);

    Page<Familymember> findByLastnameIn(
            @Param("lastname") Collection<String> names, Pageable pageable);

    Page<Familymember> findByFirstnameIn(
            @Param("firstname") Collection<String> names, Pageable pageable);

    Page<Familymember> findAll(Pageable pageable);

    @Query(name="Familymember.findById", nativeQuery = true)
    @RestResource(exported = false)
    Optional<Familymember> findById(@Param("id") long id);

    @Query(name="Familymember.findByFirstname", nativeQuery = true)
    @RestResource(exported = false)
    Optional<Familymember> findByFirstname(@Param("firstname") String fname);
}

