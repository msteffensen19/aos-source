package com.advantage.online.store.user.dao;

import com.advantage.online.store.dao.AbstractRepository;
import com.advantage.online.store.user.model.AppUser;
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
    public int returnSomething() {
        return 777;
    }

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
    			                                                   AppUser.FIELD_LOGIN,
    			                                                   AppUser.PARAM_LOGIN);
        final Query query = entityManager.createQuery(hql);
        query.setParameter(AppUser.PARAM_LOGIN, logins);

        return query.executeUpdate();
    }

    @Override
    public List<AppUser> getAllAppUsers() {
        List<AppUser> appUsers = entityManager.createNamedQuery(AppUser.QUERY_GET_ALL, AppUser.class)
                .setMaxResults(MAX_NUM_OF_APP_USER)
                .getResultList();

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
    public List<AppUser> getAppUsersByEmail(Collection<String> email) {
        return null;
    }

    @Override
    public List<AppUser> getAppUsersByLogin(Collection<String> login) {
        return null;
    }
}
