package dev.christopherbell.thevoid.utils.mappers;

import dev.christopherbell.thevoid.models.db.InviteCodeEntity;
import dev.christopherbell.thevoid.models.db.VoidRoleEntity;
import dev.christopherbell.thevoid.models.db.account.AccountDetailsEntity;
import dev.christopherbell.thevoid.models.db.account.AccountEntity;
import dev.christopherbell.thevoid.models.db.CryEntity;
import dev.christopherbell.thevoid.models.db.account.AccountSecurityEntity;
import dev.christopherbell.thevoid.models.domain.InviteCode;
import dev.christopherbell.thevoid.models.domain.account.Account;
import dev.christopherbell.thevoid.models.domain.Cry;
import dev.christopherbell.thevoid.models.domain.account.AccountDetails;
import dev.christopherbell.thevoid.models.domain.account.AccountSecurity;
import dev.christopherbell.thevoid.models.domain.VoidRolesEnum;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface MapStructMapper {

  public AccountEntity mapToAccountEntity(Account account);

  public Account mapToAccount(AccountEntity accountEntity);

  public AccountDetailsEntity mapToAccountDetailsEntity(AccountDetails accountDetails);

  public AccountDetails mapToAccountDetails(AccountDetailsEntity accountDetailsEntity);

  public AccountSecurityEntity mapToAccountSecurityEntity(AccountSecurity accountSecurity);

  public AccountSecurity mapToAccountSecurity(AccountSecurityEntity accountSecurityEntity);

  public CryEntity mapToCryEntity(Cry cry);

  public Cry mapToCry(CryEntity cryEntity);

  public InviteCodeEntity mapToInviteCodeEntity(InviteCode inviteCode);

  public InviteCode mapToInviteCode(InviteCodeEntity inviteCodeEntity);

  public VoidRoleEntity mapToVoidRoleEntity(VoidRolesEnum voidRolesEnum);
  //public VoidRolesEnum mapToVoidRoleEnum(VoidRoleEntity voidRoleEntity);
}
