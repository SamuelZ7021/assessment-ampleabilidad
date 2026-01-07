package com.riwi.assessment.infrastructure.adapter;

import com.riwi.assessment.domain.port.out.CurrentUserPort;
import com.riwi.assessment.infrastructure.adapter.out.persistence.entity.UserEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CurrentUserAdapter implements CurrentUserPort {

    @Override
    public UUID getCurrentUserId() {
        UserEntity user = (UserEntity) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
        return user.getId();
    }
}