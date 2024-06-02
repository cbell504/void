package com.thevoid.api.utils;

import com.thevoid.api.configs.Constants;
import com.thevoid.api.exceptions.VoidInvalidRequestException;
import com.thevoid.api.models.contracts.user.VoidRequest;
import com.thevoid.api.models.domain.account.Account;
import com.thevoid.api.models.domain.account.AccountSecurity;
import org.jsoup.Jsoup;
import org.jsoup.safety.Safelist;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public final class ValidateUtil {

    private static final ArrayList<String> ACCEPTED_CLIENT_IDs = new ArrayList<>(
            List.of("void-client")
    );

    public static String cleanEmailAddress(AccountSecurity accountSecurity) {
        var dirtyEmail = Objects.requireNonNullElse(accountSecurity.getEmail(), Constants.EMPTY_STRING);
        return Jsoup.clean(dirtyEmail, Safelist.basic());
    }

    public static String cleanPassword(AccountSecurity accountSecurity) {
        var dirtyPassword = Objects.requireNonNullElse(accountSecurity.getPassword(), Constants.EMPTY_STRING);
        return Jsoup.clean(dirtyPassword, Safelist.basic());
    }

    public static String cleanUsername(Account account) {
        var dirtyUsername = Objects.requireNonNullElse(account.getUsername(), Constants.EMPTY_STRING);
        return Jsoup.clean(dirtyUsername, Safelist.basic());
    }

    private static boolean isValidClientId(String clientId) {
        return ACCEPTED_CLIENT_IDs.contains(clientId);
    }

    public static void isValidUsername(String accountString) throws VoidInvalidRequestException {
        if(accountString.isBlank()) {
            throw new VoidInvalidRequestException("The given username is not valid");
        }
    }

    public static void isValidEmail(String accountString) throws VoidInvalidRequestException {
        if(accountString.isBlank()) {
            throw new VoidInvalidRequestException("The given email is not valid");
        }
    }

    public static void isValidPassword(String accountString) throws VoidInvalidRequestException {
        if(accountString.isBlank()) {
            throw new VoidInvalidRequestException("The given password is not valid");
        }
    }

    public static void validateAccount(VoidRequest voidRequest) throws VoidInvalidRequestException {
        if(Objects.isNull(voidRequest)) {
            throw new VoidInvalidRequestException("The request is null");
        }
        if(Objects.isNull(voidRequest.getAccount())){
            throw new VoidInvalidRequestException("The request contains no account information");
        }
    }

    public static void validateClientId(String clientId) throws VoidInvalidRequestException {
        if(!ValidateUtil.isValidClientId(clientId)){
            throw new VoidInvalidRequestException("The Client's ID is not valid.");
        }
    }
}