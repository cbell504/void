package com.thevoid.api.utils.mappers;

import com.thevoid.api.models.db.VoidRoleEntity;
import com.thevoid.api.models.db.account.AccountDetailsEntity;
import com.thevoid.api.models.db.account.AccountEntity;
import com.thevoid.api.models.db.CryEntity;
import com.thevoid.api.models.db.account.AccountSecurityEntity;
import com.thevoid.api.models.domain.account.Account;
import com.thevoid.api.models.domain.Cry;
import com.thevoid.api.models.domain.account.AccountDetails;
import com.thevoid.api.models.domain.account.AccountSecurity;
import com.thevoid.api.models.domain.VoidRolesEnum;
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

    public VoidRoleEntity mapToVoidRoleEntity(VoidRolesEnum voidRolesEnum);
    //public VoidRolesEnum mapToVoidRoleEnum(VoidRoleEntity voidRoleEntity);
}
