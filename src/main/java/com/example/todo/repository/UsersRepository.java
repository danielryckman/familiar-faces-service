package com.example.todo.repository;

import com.example.todo.entity.User;
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


@RepositoryRestResource(path = "user", collectionResourceRel = "user")
public interface UsersRepository extends JpaRepository<User, Long>, JpaSpecificationExecutor<User>, QuerydslPredicateExecutor<User> {

    Page<User> findByIdIn(@Param(value = "id") List<Long> eventid, Pageable pageable);

    Page<User> findByLastnameIn(
            @Param("lastname") Collection<String> names, Pageable pageable);

    Page<User> findByFirstnameIn(
            @Param("firstname") Collection<String> names, Pageable pageable);
   
    Page<User> findByNicknameIn(
            @Param("nickname") Collection<String> names, Pageable pageable);
    
    Page<User> findByEmailIn(
            @Param("email") Collection<String> email, Pageable pageable);
    
    Page<User> findAll(Pageable pageable);

    @Query(name="User.findById", nativeQuery = true)
    @RestResource(exported = false)
    Optional<User> findById(@Param("id") long qid);

    @Query(name="User.findByEmail", nativeQuery = true)
    @RestResource(exported = false)
    Optional<User> findByEmail(@Param("email") String email);

}

