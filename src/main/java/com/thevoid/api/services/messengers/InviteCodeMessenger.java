package com.thevoid.api.services.messengers;

import com.thevoid.api.models.db.InviteCodeEntity;
import com.thevoid.api.repositories.InviteCodeRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InviteCodeMessenger {
    private final InviteCodeRepository inviteCodeRepository;

    @Autowired
    public InviteCodeMessenger(InviteCodeRepository inviteCodeRepository) {
        this.inviteCodeRepository = inviteCodeRepository;
    }

    public void saveInviteCode(InviteCodeEntity inviteCodeEntity) {
       this.inviteCodeRepository.save(inviteCodeEntity);
    }
}
