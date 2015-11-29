
package com.advantage.online.store.user.dao;

import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.user.config.AppUserConfiguration;
import com.advantage.online.store.user.dto.AppUserResponseStatus;
import com.advantage.online.store.user.model.AppUser;
import com.advantage.online.store.user.util.ValidationHelper;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
@Component
@Qualifier("appUserRepository")
@Repository
public class DefaultAppUserRepository extends AbstractRepository implements AppUserRepository {

    private AppUserResponseStatus appUserResponseStatus;

    public static final int MAX_NUM_OF_APP_USER = 50;

    /*  Default application user configuration values - Begin   */
    //  3 failed login attempts will cause the user to be blocked for INTERVAL milliseconds.
    public static final int ENV_DEFAULT_NUMBER_OF_FAILED_LOGIN_ATTEMPTS_LIMIT = 3;

    //  Default 5 minutes
    public static final long ENV_DEFAULT_USER_LOGIN_ATTEMPTS_BLOCKED_INTERVAL = 300000;

    //  e-mail address is not mandatory in user details and does not take part in login/sign-in
    public static final String ENV_EMAIL_ADDRESS_IN_LOGIN = "NO";
    /*  Default application user configuration values - End     */


    /**
     * Create a new {@link AppUser} in the database.
     * 1. Verify all parameters are not <code>null</code> or empty. <br/>
     * 2. Verify <code>loginName</code> comply with AOS policy. <br/>
     * 3. Verify <code>password</code> comply with AOS policy. <br/>
     * 4. Get country-id by country-name. <br/>
     * 5. Verify <code>phoneNumber</code> comply with AOS policy.
     * 6. Verify <code>email</code> contains a valid e-mail address. <br/>
     * <p>
     * Two more fields are managed and set internally: <br/>
     * unsuccessfulLoginAttempts Number of unsuccessful login attempts in a row made by the user. <br/>
     * userBlockedFromLoginUntil After user reached the limit of unsuccessful login attempts, he will be blocked for a period of time (set in application configuration). <br/>
     *
     * @param appUserType User type: <b>10</b> = Administrator, <b>20</b> = User
     * @param lastName User's last name
     * @param firstName User's first name.
     * @param loginName User login name, compliance with AOS policy.
     * @param password User's password, compliance with AOS policy.
     * @param country country-id of user's country of residence.
     * @param phoneNumber Phone number including international country-code and area code.
     * @param stateProvince State/province/region of residence.
     * @param cityName City-name of residence.
     * @param address1 1st line of postal address.
     * @param address2 2nd line of new-user's postal address.
     * @param zipcode new-user's zip-code of postal address.
     * @param email New user's e-mail address.
     * @param agreeToReceiveOffersAndPromotions
     * @return {@link AppUserResponseStatus} when successful:
     * <br/>
     * <b>{@code success}</b> = true, <b>{@code reason}</b> = &quat;New user created successfully&quat; <b>{@code userId}</b> = user-id of newly created user.
     * <br/>
     * if failed <b>{@code success}</b> = false, <b>{@code reason}</b> = failure reason, <b>{@code userId}</b> = -1.
     * <br/>
     */
    @Override
    //public AppUserResponseStatus createAppUser(Integer appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String stateProvince, String cityName, String address1, String address2, String zipcode, String email, char agreeToReceiveOffersAndPromotions) {
    public AppUser createAppUser(Integer appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String stateProvince, String cityName, String address1, String address2, String zipcode, String email, char agreeToReceiveOffersAndPromotions) {

        //  Validate Numeric Arguments
        ArgumentValidationHelper.validateArgumentIsNotNull(appUserType, "application user type");
        ArgumentValidationHelper.validateArgumentIsNotNull(country, "country id");

        ArgumentValidationHelper.validateNumberArgumentIsPositive(appUserType, "application user type");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(country, "country id");

        //  Validate String Arguments - Mandatory columns
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(loginName, "login name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(password, "user password");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(email, "email");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(String.valueOf(agreeToReceiveOffersAndPromotions), "agree to receive offers and promotions");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(lastName, "last name");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(firstName, "first name");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(phoneNumber, "phone number");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(stateProvince, "state/provice/region");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(cityName, "city name");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(address1, "address line 1");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(address2, "address line 2");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(zipcode, "zipcode");

        if (ValidationHelper.isValidLogin(loginName)) {
            if (ValidationHelper.isValidPassword(password)) {
                if (validatePhoneNumberAndEmail(phoneNumber, email)) {
                    AppUser appUser = new AppUser(appUserType, lastName, firstName, loginName, password, country, phoneNumber, stateProvince, cityName, address1, address2, zipcode, email, agreeToReceiveOffersAndPromotions);
                    entityManager.persist(appUser);

                    appUserResponseStatus = new AppUserResponseStatus(true, "New user created successfully.", appUser.getId());

                    return appUser;
                } else {
                    //  appUserResponseStatus is already set with values.
                    return null;
                }
            }
            else {
                appUserResponseStatus = new AppUserResponseStatus(false, "Invalid login password.", -1);
                return null;
            }
        }
        else {
            appUserResponseStatus = new AppUserResponseStatus(false, "Invalid login user-name.", -1);
            return null;
        }

    }

    public AppUserResponseStatus create(Integer appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String stateProvince, String cityName, String address1, String address2, String zipcode, String email, char agreeToReceiveOffersAndPromotions) {
        AppUser appUser = createAppUser(appUserType, lastName, firstName, loginName, password, country, phoneNumber, stateProvince, cityName, address1, address2, zipcode, email, agreeToReceiveOffersAndPromotions);

        return new AppUserResponseStatus(appUserResponseStatus.isSuccess(),
                appUserResponseStatus.getReason(),
                appUserResponseStatus.getUserId());
    }

    @Override
    public int deleteAppUser(AppUser appUser) {
        ArgumentValidationHelper.validateArgumentIsNotNull(appUser, "application user");

        final Long userId = appUser.getId();

        final String hql = JPAQueryHelper.getDeleteByPkFieldQuery(AppUser.class,
                AppUser.FIELD_ID,
                userId);
        final Query query = entityManager.createQuery(hql);

        return query.executeUpdate();
    }

//    @Override
//    public int deleteAppUsersByEmails(Collection<String> emails) {
//    	ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(emails,
//    			                                                                "application users emails");
//    	final String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(AppUser.class,
//    			                                                   AppUser.FIELD_EMAIL,
//    			                                                   AppUser.PARAM_EMAIL);
//        final Query query = entityManager.createQuery(hql);
//        query.setParameter(AppUser.PARAM_EMAIL, emails);
//        return query.executeUpdate();
//    }

//    @Override
//    public int deleteAppUsersByLogins(Collection<String> logins) {
//    	ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(logins,
//    			                                                                "application users logins");
//    	final String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(AppUser.class,
//    			                                                   AppUser.FIELD_USER_LOGIN,
//    			                                                   AppUser.PARAM_USER_LOGIN);
//        final Query query = entityManager.createQuery(hql);
//        query.setParameter(AppUser.PARAM_USER_LOGIN, logins);
//
//        return query.executeUpdate();
//    }

    @Override
    public List<AppUser> getAllAppUsers() {
        List<AppUser> appUsers = entityManager.createNamedQuery(AppUser.QUERY_GET_ALL, AppUser.class)
                .setMaxResults(MAX_NUM_OF_APP_USER)
                .getResultList();

//        //  Encrypt the password before updating it into the database
//        String encryptedPassword = userPassword.decryptText(password);

        return appUsers.isEmpty() ? null : appUsers;
    }

    @Override
    public List<AppUser> getAppUsersByCountry(Integer countryId) {
        List<AppUser> appUsers = entityManager.createNamedQuery(AppUser.QUERY_GET_USERS_BY_COUNTRY, AppUser.class)
                .setParameter(AppUser.PARAM_COUNTRY, countryId)
                .setMaxResults(MAX_NUM_OF_APP_USER)
                .getResultList();

        return appUsers.isEmpty() ? null : appUsers;
    }

    @Override
    public AppUser getAppUserByLogin(String userLogin) {
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(userLogin, "user login name");

        final Query query = entityManager.createNamedQuery(AppUser.QUERY_GET_BY_USER_LOGIN);

        query.setParameter(AppUser.PARAM_USER_LOGIN, userLogin);

        @SuppressWarnings("unchecked")

        List<AppUser> appUsers = query.getResultList();

        final AppUser user;

        if (appUsers.isEmpty()) {

            user = null;
        } else {

            user = appUsers.get(0);
        }

        return user;

    }

    @Override
    public AppUserResponseStatus doLogin(String loginUser, String loginPassword, String email) {

//        //  Get Application User Configuration values from "AppUserConfiguration.properties" file
//        AppUserConfiguration appUserConfiguration = new AppUserConfiguration();
//        appUserConfiguration.getAppUserConfiguration();

        AppUser appUser = getAppUserByLogin(loginUser);

        if (appUser == null) {
            //  Invalid user login.
            return new AppUserResponseStatus(false, "Invalid user-name and password.", -1);
        }

        if (loginPassword != null) {
            if (appUser.getPassword().compareTo(loginPassword) != 0) {
                appUser = addUnsuccessfulLoginAttempt(appUser);
                return new AppUserResponseStatus(false, "Invalid user-name and password.", appUser.getId());
            }
        }
        else {
            //  password is empty
            appUser = addUnsuccessfulLoginAttempt(appUser);
            return new AppUserResponseStatus(false, "Invalid user-name and password.", appUser.getId());
        }


        if (email != null) {
            if (appUser.getEmail() != null) {
                if (appUser.getEmail().compareToIgnoreCase(email) != 0) {
                    //  email does not match the email set in user details
                    appUser = addUnsuccessfulLoginAttempt(appUser);
                    return new AppUserResponseStatus(false, "Invalid email address.", appUser.getId());
                }
            } else {
                //
                appUser = addUnsuccessfulLoginAttempt(appUser);
                return new AppUserResponseStatus(false, "No emails exists for user.", appUser.getId());
            }

//        } else {
//            return new AppUserResponseStatus(false, "Login email is empty.", appUser.getId());
        }

        //  Reset user-blocking
        appUser.setUnsuccessfulLoginAttempts(0);
        appUser.setUserBlockedFromLoginUntil("");

        //  Update changes
        updateAppUser(appUser);

        //  Return: Successful login attempt
        return new AppUserResponseStatus(true, "Login Successful", appUser.getId(), getTokenKey());
    }

    private boolean validatePhoneNumberAndEmail(final String phoneNumber, final String email) {
        //  Check phone number validation if not null
        if ((phoneNumber != null) && (phoneNumber.trim().length() > 0)) {
            if (! ValidationHelper.isValidPhoneNumber(phoneNumber)) {
                appUserResponseStatus = new AppUserResponseStatus(false, "Invalid phone number.", -1);

                return false;
            }
        }

        //  Check e-mail address validation if not null
        if (email != null) {
            if (! ValidationHelper.isValidEmail(email)) {
                appUserResponseStatus = new AppUserResponseStatus(false, "Invalid e-mail address.", -1);

                return false;
            }
        }

        return true;
    }

    @Override
    public AppUser addUnsuccessfulLoginAttempt(AppUser appUser) {
        //  Another unsuccessful (failed) login attempt
        appUser.setUnsuccessfulLoginAttempts(appUser.getUnsuccessfulLoginAttempts() + 1);

        //  Check the number of unsuccessful login attempts, block user if reached the limit
        if (appUser.getUnsuccessfulLoginAttempts() == ENV_DEFAULT_NUMBER_OF_FAILED_LOGIN_ATTEMPTS_LIMIT) {
            //  Update AppUser class with timestamp when user can attempt login again according to configuration interval
            appUser.setUserBlockedFromLoginUntil(AppUser.addMillisecondsIntervalToTimestamp(ENV_DEFAULT_USER_LOGIN_ATTEMPTS_BLOCKED_INTERVAL));
        }

        //  Update data changes made for application user into application users table
        appUser = updateAppUser(appUser);

        return appUser;
    }

    @Override
    public String getBlockedUntilTimestamp(long milliSeconds) {
        return AppUser.addMillisecondsIntervalToTimestamp(milliSeconds);
    }

    /**
     * Update table with data-changes made to application user detail.
     * @param appUser Application User to update changes.
     * @return Updated Application User class.
     */
    @Override
    public AppUser updateAppUser(AppUser appUser) {
        entityManager.persist(appUser);
        return appUser;
    }

    /**
     * Produce a random {@link UUID} string as {@code TokenKey}.
     * @return Random {@link UUID} string.
     */
    private String getTokenKey() {
        return UUID.randomUUID().toString();
    }
}
