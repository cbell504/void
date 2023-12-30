package com.thevoid.api.models.db.account;

import com.thevoid.api.models.db.CryEntity;
import com.thevoid.api.models.db.VoidRoleEntity;
import com.thevoid.api.models.domain.account.AccountDetails;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "account", schema = "void_api")
public class AccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column(unique = true)
    private String username;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private VoidRoleEntity voidRoleEntity;

    @OneToMany(targetEntity = CryEntity.class, mappedBy = "accountEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<CryEntity> cries;

    @OneToOne(targetEntity = AccountDetailsEntity.class, mappedBy = "accountEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AccountDetailsEntity accountDetailsEntity;

    @OneToOne(targetEntity = AccountSecurityEntity.class, mappedBy = "accountEntity", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private AccountSecurityEntity accountSecurityEntity;

    //    private List<Account> followers;
//    private List<Account> following;
}
