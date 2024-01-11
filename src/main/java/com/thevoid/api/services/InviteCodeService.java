package com.thevoid.api.services;

import com.thevoid.api.configs.Constants;
import com.thevoid.api.exceptions.VoidAccountNotFoundException;
import com.thevoid.api.exceptions.VoidInvalidRequestException;
import com.thevoid.api.exceptions.VoidInvalidTokenException;
import com.thevoid.api.models.contracts.user.VoidRequest;
import com.thevoid.api.models.contracts.user.VoidResponse;
import com.thevoid.api.models.db.InviteCodeEntity;
import com.thevoid.api.models.domain.account.Account;
import com.thevoid.api.models.domain.global.Message;
import com.thevoid.api.services.messengers.AccountMessenger;
import com.thevoid.api.services.messengers.InviteCodeMessenger;
import com.thevoid.api.utils.ResponseUtil;
import com.thevoid.api.utils.ValidateUtil;
import com.thevoid.api.utils.mappers.MapStructMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Service
public class InviteCodeService {
    private final AccountMessenger accountMessenger;
    private final InviteCodeMessenger inviteCodeMessenger;
    private final MapStructMapper mapStructMapper;
    private final PermissionsService permissionsService;

    @Autowired
    public InviteCodeService(AccountMessenger accountMessenger,
                             InviteCodeMessenger inviteCodeMessenger,
                             MapStructMapper mapStructMapper,
                             PermissionsService permissionsService) {
        this.accountMessenger = accountMessenger;
        this.inviteCodeMessenger = inviteCodeMessenger;
        this.mapStructMapper = mapStructMapper;
        this.permissionsService = permissionsService;
    }

    public VoidResponse generateInviteCode(String clientId, String loginToken, VoidRequest voidRequest) throws VoidInvalidRequestException, VoidAccountNotFoundException, VoidInvalidTokenException {
        ValidateUtil.validateAccount(voidRequest);
        ValidateUtil.validateClientId(clientId);

        var account = Objects.requireNonNullElse(voidRequest.getAccount(), new Account());
        var accountId = Objects.requireNonNullElse(account.getId(), 0L);

        var isValidCreds = this.permissionsService.validateLoginToken(loginToken, accountId);
        var response = new VoidResponse();
        if (isValidCreds) {
            // Build a template success response
            response = ResponseUtil.buildSuccessfulResponse(HttpStatus.CREATED);
            var accountEntity = this.accountMessenger.getAccountEntityById(accountId);
            var inviteCodeEntity = new InviteCodeEntity();
            inviteCodeEntity.setAccountEntity(accountEntity);
            inviteCodeEntity.setCode(UUID.randomUUID().toString());
            // Save the invite Code
            this.inviteCodeMessenger.saveInviteCode(inviteCodeEntity);
            var inviteCode = this.mapStructMapper.mapToInviteCode(inviteCodeEntity);
            response.setInviteCode(inviteCode);
        } else {
            var message = new Message();
            message.setCode(Constants.ERROR_CODE_NOT_AUTHORIZED);
            message.setDescription("You aren't authorized for this!");
            response = ResponseUtil.buildFailureResponse(HttpStatus.UNAUTHORIZED, List.of(message));
        }
        return response;
    }
}
