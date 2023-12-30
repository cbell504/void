package com.thevoid.api.models.db;

import com.thevoid.api.models.db.account.AccountEntity;
import com.thevoid.api.models.db.account.AccountSecurityEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="void_role", schema ="void_api")
public class VoidRoleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    private String role;

    @OneToOne(targetEntity = AccountEntity.class, mappedBy = "voidRoleEntity", cascade = CascadeType.MERGE, fetch = FetchType.LAZY)
    private AccountEntity accountEntity;
}
