package com.advantage.online.store.user.dao;

import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.user.dto.AppUserResponseStatus;
import com.advantage.online.store.user.model.AppUser;
import com.advantage.online.store.user.util.UserPassword;
import com.advantage.util.ArgumentValidationHelper;
import com.advantage.util.JPAQueryHelper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
@Component
@Qualifier("appUserRepository")
@Repository
public class DefaultAppUserRepository extends AbstractRepository implements AppUserRepository {

    public static final int MAX_NUM_OF_APP_USER = 50;

    @Override
    public AppUser createAppUser(Integer appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String stateProvince, String cityName, String address1, String address2, String zipcode, String email, char agreeToReceiveOffersAndPromotions) {

        //  Validate Numeric Arguments
        ArgumentValidationHelper.validateArgumentIsNotNull(appUserType, "application user type");
        ArgumentValidationHelper.validateArgumentIsNotNull(country, "country id");

        ArgumentValidationHelper.validateNumberArgumentIsPositive(appUserType, "application user type");
        ArgumentValidationHelper.validateNumberArgumentIsPositive(country, "country id");

        //  Validate String Arguments
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(lastName, "last name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(firstName, "first name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(loginName, "login name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(password, "user password");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(phoneNumber, "phone number");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(stateProvince, "state/provice/region");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(cityName, "city name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(address1, "address line 1");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(address2, "address line 2");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(zipcode, "zipcode");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(String.valueOf(agreeToReceiveOffersAndPromotions), "agree to receive offers and promotions");

        AppUser appUser = new AppUser(appUserType, lastName, firstName, loginName, password, country, phoneNumber, stateProvince, cityName, address1, address2, zipcode, email, agreeToReceiveOffersAndPromotions);
        entityManager.persist(appUser);
        return appUser;
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

    @Override
    public int deleteAppUsersByEmails(Collection<String> emails) {
    	ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(emails,
    			                                                                "application users emails");
    	final String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(AppUser.class,
    			                                                   AppUser.FIELD_EMAIL,
    			                                                   AppUser.PARAM_EMAIL);
        final Query query = entityManager.createQuery(hql);
        query.setParameter(AppUser.PARAM_EMAIL, emails);
        return query.executeUpdate();
    }

    @Override
    public int deleteAppUsersByLogins(Collection<String> logins) {
    	ArgumentValidationHelper.validateCollectionArgumentIsNotNullAndNotEmpty(logins,
    			                                                                "application users logins");
    	final String hql = JPAQueryHelper.getDeleteByPkFieldsQuery(AppUser.class,
    			                                                   AppUser.FIELD_USER_LOGIN,
    			                                                   AppUser.PARAM_USER_LOGIN);
        final Query query = entityManager.createQuery(hql);
        query.setParameter(AppUser.PARAM_USER_LOGIN, logins);

        return query.executeUpdate();
    }

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

        AppUser appUser = getAppUserByLogin(loginUser);

        if (appUser == null) {
            return new AppUserResponseStatus(false, "Invalid user login.", -1);
        }

        if (loginPassword != null) {
            if (appUser.getPassword().compareTo(loginPassword) != 0) {
                return new AppUserResponseStatus(false, "Invalid user-name and password combination. Count not find user-name with this password.", appUser.getId());
            }
        }
        else
            return new AppUserResponseStatus(false, "Invalid login, password is empty.", appUser.getId());


        if (email != null) {
            if (appUser.getEmail() != null) {
                if (appUser.getEmail().compareToIgnoreCase(email) != 0) {
                    return new AppUserResponseStatus(false, "Login email does not match the email set in user details.", appUser.getId());
                }
            } else {
                return new AppUserResponseStatus(false, "No emails was set in user details.", appUser.getId());
            }

//        } else {
//            return new AppUserResponseStatus(false, "Login email is empty.", appUser.getId());
        }

            return new AppUserResponseStatus(true, "Login Successful", appUser.getId());
    }

}
