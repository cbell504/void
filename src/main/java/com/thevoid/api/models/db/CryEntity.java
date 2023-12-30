package com.thevoid.api.models.db;

import com.thevoid.api.models.db.account.AccountEntity;
import jakarta.persistence.*;
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
@Table(name="cry", schema ="void_api")
public class CryEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column
    private Long id;
    @Column
    private String creationDate;
    @Column
    private String expirationDate;
    @Column
    private boolean isRootCry;
    @Column
    private String lastAmplifiedDate;
    @Column
    private String text;

//    @Column
//    private Long parentCryId;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="account_id", referencedColumnName = "id", nullable = false)
    private AccountEntity accountEntity;

}