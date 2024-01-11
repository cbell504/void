package com.thevoid.api.repositories;

import com.thevoid.api.models.db.InviteCodeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface InviteCodeRepository extends JpaRepository<InviteCodeEntity, Long> {
}
