
package com.advantage.account.store.user.dao;

import com.advantage.account.store.dao.AbstractRepository;
import com.advantage.account.store.user.config.AppUserConfiguration;
import com.advantage.account.store.user.dto.AppUserResponseStatus;
import com.advantage.account.store.user.model.AppUser;
import com.advantage.account.util.ValidationHelper;
import com.advantage.account.util.ArgumentValidationHelper;
import com.advantage.account.util.JPAQueryHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.NoResultException;
import javax.persistence.Query;
import java.util.Date;
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
    private String failureMessage;

    /*  Default application user configuration values - Begin   */
    //  3 failed login attempts will cause the user to be blocked for INTERVAL milliseconds.
    public static final int ENV_DEFAULT_NUMBER_OF_FAILED_LOGIN_ATTEMPTS_LIMIT = 3;

    //  Default 5 minutes
    public static final long ENV_DEFAULT_USER_LOGIN_ATTEMPTS_BLOCKED_INTERVAL = 300000;

    //  e-mail address is not mandatory in user details and does not take part in login/sign-in
    public static final String ENV_EMAIL_ADDRESS_IN_LOGIN = "NO";
    /*  Default application user configuration values - End     */

    public String getFailureMessage() {
        return this.failureMessage;
    }

    /**
     * Create a new {@link AppUser} in the database.
     * 1. Verify all parameters are not <code>null</code> or empty. <br/>
     * 2. Verify {@code loginName} comply with AOS policy. <br/>
     * 3. Verify {@code password} comply with AOS policy. <br/>
     * 4. Get country-id by country-name. <br/>
     * 5. Verify {@code phoneNumber} comply with AOS policy.
     * 6. Verify {@code email} contains a valid e-mail address. <br/>
     * <p>
     * Two more fields are managed and set internally: <br/>
     * unsuccessfulLoginAttempts Number of unsuccessful login attempts in a row made by the user. <br/>
     * userBlockedFromLoginUntil After user reached the limit of unsuccessful login attempts, he will be blocked for a period of time (set in application configuration). <br/>
     *
     * @param appUserType          User type: <b>10</b> = Administrator, <b>20</b> = User
     * @param lastName             User's last name
     * @param firstName            User's first name.
     * @param loginName            User login name, compliance with AOS policy.
     * @param password             User's password, compliance with AOS policy.
     * @param country              country-id of user's country of residence.
     * @param phoneNumber          Phone number including international country-code and area code.
     * @param stateProvince        State/province/region of residence.
     * @param cityName             City-name of residence.
     * @param address              postal address.
     * @param zipcode              new-user's zip-code of postal address.
     * @param email                New user's e-mail address.
     * @param allowOffersPromotion
     * @return {@link AppUserResponseStatus} when successful:
     * <br/>
     * <b>{@code success}</b> = true, <b>{@code reason}</b> = &quat;New user created successfully&quat; <b>{@code userId}</b> = user-id of newly created user.
     * <br/>
     * if failed <b>{@code success}</b> = false, <b>{@code reason}</b> = failure reason, <b>{@code userId}</b> = -1.
     * <br/>
     */
    @Override
    //public AppUserResponseStatus createAppUser(Integer appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String stateProvince, String cityName, String address, String zipcode, String email, char agreeToReceiveOffersAndPromotions) {
    public AppUser createAppUser(Integer appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String stateProvince, String cityName, String address, String zipcode, String email, char allowOffersPromotion) {

        //  Validate Numeric Arguments
        ArgumentValidationHelper.validateArgumentIsNotNull(appUserType, "application user type");
        ArgumentValidationHelper.validateArgumentIsNotNull(country, "country id");

        ArgumentValidationHelper.validateNumberArgumentIsPositive(appUserType, "application user type");
        ArgumentValidationHelper.validateNumberArgumentIsPositiveOrZero(country, "country id");

        //  Validate String Arguments - Mandatory columns
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(loginName, "login name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(password, "user password");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(email, "email");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(String.valueOf(allowOffersPromotion), "agree to receive offers and promotions");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(lastName, "last name");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(firstName, "first name");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(phoneNumber, "phone number");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(stateProvince, "state/provice/region");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(cityName, "city name");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(address, "address");
        //ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(zipcode, "zipcode");

        if (ValidationHelper.isValidLogin(loginName)) {
            if (ValidationHelper.isValidPassword(password)) {
                if (validatePhoneNumberAndEmail(phoneNumber, email)) {
                    if (getAppUserByLogin(loginName) == null) {
                        AppUser appUser = new AppUser(appUserType, lastName, firstName, loginName, password, country, phoneNumber, stateProvince, cityName, address, zipcode, email, allowOffersPromotion);
                        entityManager.persist(appUser);

                        //  New user created successfully.
                        this.failureMessage = "New user created successfully.";
                        appUserResponseStatus = new AppUserResponseStatus(true, AppUser.MESSAGE_NEW_USER_CREATED_SUCCESSFULLY, appUser.getId());

                        return appUser;
                    } else {
                        //  User with this login already exists
                        this.failureMessage = "User with this login already exists.";
                        appUserResponseStatus = new AppUserResponseStatus(false, AppUser.MESSAGE_USER_LOGIN_FAILED, -1);
                        return null;

                    }
                } else {
                    //  appUserResponseStatus is already set with values.
                    return null;
                }
            } else {
                //  Invalid password
                this.failureMessage = "Invalid password.";
                appUserResponseStatus = new AppUserResponseStatus(false, AppUser.MESSAGE_USER_LOGIN_FAILED, -1);
                return null;
            }
        } else {
            //  Invalid login user-name.
            this.failureMessage = "Invalid login user-name.";
            appUserResponseStatus = new AppUserResponseStatus(false, AppUser.MESSAGE_USER_LOGIN_FAILED, -1);
            return null;
        }

    }

    public AppUserResponseStatus create(Integer appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String stateProvince, String cityName, String address, String zipcode, String email, char allowOffersPromotion) {
        AppUser appUser = createAppUser(appUserType, lastName, firstName, loginName, password, country, phoneNumber, stateProvince, cityName, address, zipcode, email, allowOffersPromotion);

        return new AppUserResponseStatus(appUserResponseStatus.isSuccess(),
                appUserResponseStatus.getReason(),
                appUserResponseStatus.getUserId());
    }

    @Override
    public int deleteAppUser(AppUser appUser) {
        System.out.println("int deleteAppUser(AppUser appUser) - Strat");

        ArgumentValidationHelper.validateArgumentIsNotNull(appUser, "application user");

        Long userId = appUser.getId();

        System.out.println("int deleteAppUser(AppUser appUser) - Building HQL");
        String hql = JPAQueryHelper.getDeleteByPkFieldQuery(AppUser.class,
                AppUser.FIELD_ID,
                userId);
        Query query = entityManager.createQuery(hql);

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
    public List<AppUser> getAppUsersByCountry(Integer countryId) {
        List<AppUser> appUsers = entityManager.createNamedQuery(AppUser.QUERY_GET_USERS_BY_COUNTRY, AppUser.class)
                .setParameter(AppUser.PARAM_COUNTRY, countryId)
                .setMaxResults(AppUser.MAX_NUM_OF_APP_USER)
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

        //  Check arguments: Not NULL and Not BLANK
        if ((loginUser == null) || (loginUser.length() == 0)) {
            return new AppUserResponseStatus(false, AppUser.MESSAGE_USER_LOGIN_FAILED, -1);
        }

        if ((loginPassword == null) || (loginPassword.length() == 0)) {
            return new AppUserResponseStatus(false, AppUser.MESSAGE_USER_LOGIN_FAILED, -1);
        }

        if ((email == null) || (email.length() == 0)) {
            return new AppUserResponseStatus(false, AppUser.MESSAGE_INVALID_EMAIL_ADDRESS, -1);
        }

        //  Try to get user details by login user-name
        AppUser appUser = getAppUserByLogin(loginUser);

        if (appUser == null) {
            //  Invalid user login.
            return new AppUserResponseStatus(false, AppUser.MESSAGE_USER_LOGIN_FAILED, -1);
        }

        final long currentTimestamp = new Date().getTime();
        if (appUser.getInternalUserBlockedFromLoginUntil() > 0) {

            if (appUser.getInternalUserBlockedFromLoginUntil() < currentTimestamp) {
                //  User is no longer blocked from attempting to login - Reset INTERNAL fields
                appUser.setInternalUnsuccessfulLoginAttempts(0);
                appUser.setInternalUserBlockedFromLoginUntil(0);
            }

            if (appUser.getInternalUserBlockedFromLoginUntil() >= currentTimestamp) {
                //  User is still blocked from login attempt
                return new AppUserResponseStatus(false, AppUser.MESSAGE_USER_IS_BLOCKED_FROM_LOGIN, -1);
            }
        }

        if ((!loginPassword.isEmpty()) && (loginPassword.trim().length() > 0)) {
            if (appUser.getPassword().compareTo(loginPassword) != 0) {
                appUser = addUnsuccessfulLoginAttempt(appUser);
                return new AppUserResponseStatus(false, AppUser.MESSAGE_USER_LOGIN_FAILED, appUser.getId());
            }
        } else {
            //  password is empty
            appUser = addUnsuccessfulLoginAttempt(appUser);
            return new AppUserResponseStatus(false, AppUser.MESSAGE_USER_LOGIN_FAILED, appUser.getId());
        }

        //  Check/Verify email address only if it is CONFIGURED to be shown in LOGIN
        if (AppUserConfiguration.EMAIL_ADDRESS_IN_LOGIN.toUpperCase().equalsIgnoreCase("Yes")) {
            if ((!email.isEmpty()) && (email.trim().length() > 0)) {
                if ((!appUser.getEmail().isEmpty()) && (appUser.getEmail().trim().length() > 0)) {
                    if (appUser.getEmail().compareToIgnoreCase(email) != 0) {
                        //  email does not match the email set in user details
                        appUser = addUnsuccessfulLoginAttempt(appUser);
                        return new AppUserResponseStatus(false, AppUser.MESSAGE_INVALID_EMAIL_ADDRESS, appUser.getId());
                    }
                } else {
                    //
                    appUser = addUnsuccessfulLoginAttempt(appUser);
                    return new AppUserResponseStatus(false, AppUser.MESSAGE_NO_EMAIL_EXISTS_FOR_USER, appUser.getId());
                }

            } else {
                return new AppUserResponseStatus(false, AppUser.MESSAGE_LOGIN_EMAIL_ADDRESS_IS_EMPTY, appUser.getId());
            }
        }

        //  Reset user-blocking
        appUser.setInternalUnsuccessfulLoginAttempts(0);
        appUser.setInternalUserBlockedFromLoginUntil(0);

        //  Update changes
        updateAppUser(appUser);

        //  Return: Successful login attempt
        return new AppUserResponseStatus(true, "Login Successful", appUser.getId(), getTokenKey());
    }

    private boolean validatePhoneNumberAndEmail(final String phoneNumber, final String email) {
        //  Check phone number validation if not null
        if ((phoneNumber != null) && (phoneNumber.trim().length() > 0)) {
            if (!ValidationHelper.isValidPhoneNumber(phoneNumber)) {
                appUserResponseStatus = new AppUserResponseStatus(false, "Invalid phone number.", -1);

                return false;
            }
        }

        //  Check e-mail address validation if not null
        if (email != null) {
            if (!ValidationHelper.isValidEmail(email)) {
                appUserResponseStatus = new AppUserResponseStatus(false, "Invalid e-mail address.", -1);

                return false;
            }
        }

        return true;
    }

    @Override
    public AppUser addUnsuccessfulLoginAttempt(AppUser appUser) {
        //  Another unsuccessful (failed) login attempt
        appUser.setInternalUnsuccessfulLoginAttempts(appUser.getInternalUnsuccessfulLoginAttempts() + 1);

        //  Check the number of unsuccessful login attempts, block user if reached the limit
        //if (appUser.getInternalUnsuccessfulLoginAttempts() == ENV_DEFAULT_NUMBER_OF_FAILED_LOGIN_ATTEMPTS_LIMIT) {
        if (appUser.getInternalUnsuccessfulLoginAttempts() == AppUserConfiguration.NUMBER_OF_FAILED_LOGIN_ATTEMPTS_BEFORE_BLOCKING) {

            //  Update AppUser class with timestamp when user can attempt login again according to configuration interval
            appUser.setInternalUserBlockedFromLoginUntil(AppUser.addMillisecondsIntervalToTimestamp(AppUserConfiguration.LOGIN_BLOCKING_INTERVAL_IN_MILLISECONDS));
        }

        //  Update data changes made for application user into application users table
        appUser = updateAppUser(appUser);

        return appUser;
    }

    @Override
    public String getBlockedUntilTimestamp(long milliSeconds) {
        //return AppUser.addMillisecondsIntervalToTimestamp(milliSeconds);
        return AppUser.convertMillisecondsDateToString(AppUser.addMillisecondsIntervalToTimestamp(milliSeconds));
    }

    /**
     * Update table with data-changes made to application user detail.
     *
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
     *
     * @return Random {@link UUID} string.
     */
    private String getTokenKey() {
        return UUID.randomUUID().toString();
    }

    @Override
    public int delete(AppUser... entities) {
        return 0;
    }

    @Override
    public List<AppUser> getAll() {
        List<AppUser> appUsers = entityManager.createNamedQuery(AppUser.QUERY_GET_ALL, AppUser.class)
                .setMaxResults(AppUser.MAX_NUM_OF_APP_USER)
                .getResultList();

        return appUsers.isEmpty() ? null : appUsers;
    }

    @Override
    public AppUser get(Long entityId) {
        ArgumentValidationHelper.validateArgumentIsNotNull(entityId, "user id");
        System.out.println("DefaultAppUserRepository.get(Long) -> entityId = " + entityId);

        String hql = JPAQueryHelper.getSelectByPkFieldQuery(AppUser.class, AppUser.FIELD_ID, entityId);

        Query query = entityManager.createQuery(hql);

        AppUser appUser = null;
        try {
            appUser = (AppUser) query.getSingleResult();
        } catch (NoResultException ex) {
            //  return null ==> No registered user found for userId.
            //ex.printStackTrace();
        } catch (Exception ex) {
            //  another exception was thrown
            ex.printStackTrace();
        }

        return appUser;
    }
}
