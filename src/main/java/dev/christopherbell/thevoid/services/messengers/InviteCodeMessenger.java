package dev.christopherbell.thevoid.services.messengers;

import dev.christopherbell.thevoid.models.db.InviteCodeEntity;
import dev.christopherbell.thevoid.repositories.InviteCodeRepository;
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
