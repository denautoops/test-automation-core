package api.business.usermanagement.data.factories;

import api.business.usermanagement.db.enums.AccountRole;
import api.business.usermanagement.db.models.UserAccount;
import core.utils.JsonUtils;
import org.apache.commons.lang3.RandomStringUtils;

import java.util.Arrays;
import java.util.List;

import static api.business.usermanagement.data.constants.UserManagementConstants.*;

/**
 * User Account Factory
 * <p>
 *
 * @author Denis.Martynov
 * Created on 8.04.21
 */
public final class UserAccountFactory {

    private static final String DEFAULT_USERS = "data/json/usermanagement/defaultUsers.json";
    private static final String DEFAULT_DRAFT_USERS = "data/json/usermanagement/defaultDraftUsers.json";
    private static final String DEFAULT_USER = "data/json/usermanagement/defaultCitizenUser.json";

    private UserAccountFactory() {
    }

    public static List<UserAccount> getDefaultUsers() {
        List<UserAccount> userAccountList = Arrays.asList(JsonUtils.serializeJsonFromResource(UserAccount[].class, DEFAULT_USERS));
        userAccountList
                .forEach(userAccount -> {
                    userAccount.setEmail(String.format(EMAIL_MASK, RandomStringUtils.randomAlphabetic(6)).toLowerCase());
                    userAccount.setPersonalId(getRandomPersonalId());
                    userAccount.setPhone(String.format(PHONE_MASK, RandomStringUtils.randomNumeric(7)));
                    if (userAccount.getRole().equals(AccountRole.NGO) || userAccount.getRole().equals(AccountRole.SOLE_TRADER)) {
                        userAccount.setUen(RandomStringUtils.randomNumeric(20));
                    }
                });
        return userAccountList;
    }

    public static List<UserAccount> getDefaultDraftUsers() {
        List<UserAccount> draftUsersList = Arrays.asList(JsonUtils.serializeJsonFromResource(UserAccount[].class, DEFAULT_DRAFT_USERS));
        draftUsersList
                .forEach(userAccount -> {
                    userAccount.setPersonalId(getRandomPersonalId());
                });
        return draftUsersList;
    }

    private static synchronized String getRandomPersonalId() {
        return String.format(
                PERSONAL_ID_MASK, RandomStringUtils.randomNumeric(5), RandomStringUtils.randomNumeric(5)
        ).toLowerCase();
    }

    public static UserAccount getDefaultCitizenUser(){
        UserAccount userAccount = JsonUtils.serializeJsonFromResource(UserAccount.class, DEFAULT_USER);
        userAccount.setEmail(String.format(EMAIL_MASK, RandomStringUtils.randomAlphabetic(6)).toLowerCase());
        userAccount.setPersonalId(getRandomPersonalId());
        userAccount.setPhone(String.format(PHONE_MASK, RandomStringUtils.randomNumeric(7)));
        return userAccount;
    }
}
