package com.example.backend.repository.userList;

import com.example.backend.entity.maria.QUser;
import com.example.backend.entity.maria.User;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@PersistenceContext
@Log4j2
public class UserListImpl extends QuerydslRepositorySupport implements UserList {

    private final EntityManager entityManager;


    public UserListImpl(EntityManager entityManager) {
        super(User.class);
        this.entityManager = entityManager;
    }


    @Override
    public Optional<List<User>> findAllByEmailOrderByField(List<String> emails) {


        QUser user = QUser.user;
        JPAQueryFactory query = new JPAQueryFactory(entityManager);

        log.info(emails);

        String emailIn = emails.stream().map(value -> "\'" + value+ "\'").collect(Collectors.joining(", "));

        log.info(emailIn);

        List<User> users =  query.from(user)
                .where(user.email.in(emails))
                .select(user)
                .fetch();

        users.stream().forEach((u) -> log.info("test: " + u.getName()));

        return Optional.of(
            users
        );
    }
}
