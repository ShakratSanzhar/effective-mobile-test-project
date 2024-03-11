package com.example.effectivemobiletestproject.utils;

import com.example.effectivemobiletestproject.domain.entity.QUser;
import com.example.effectivemobiletestproject.web.dto.user.UserFilter;
import com.querydsl.core.types.Predicate;
import org.springframework.stereotype.Component;

@Component
public class UserPredicateBuilder implements PredicateBuilder<Predicate, UserFilter> {

    @Override
    public Predicate build(UserFilter requestFilter) {
        return QPredicates.builder()
                .add(requestFilter.getBirthday(),QUser.user.birthday::after)
                .add(requestFilter.getPhone(),QUser.user.phone::eq)
                .add(requestFilter.getEmail(),QUser.user.email::eq)
                .add(requestFilter.getFullName(),QUser.user.fullName::startsWithIgnoreCase)
                .buildAnd();
    }
}
