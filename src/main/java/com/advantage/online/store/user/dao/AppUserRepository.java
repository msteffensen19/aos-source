package com.advantage.online.store.user.dao;

import com.advantage.online.store.user.model.AppUser;

import java.util.Collection;
import java.util.List;

/**
 * @author Binyamin Regev on 16/11/2015.
 */
public interface AppUserRepository {

    AppUser createAppUser(int appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String email);
    AppUser createAppUser(int appUserType, String lastName, String firstName, String loginName, String password, Integer country, String phoneNumber, String stateProvince, String cityName, String address1, String address2, String zipcode, String email);

    int deleteAppUser(AppUser appUser);
    int deleteAppUsersByEmails(Collection<String> emails);
    int deleteAppUsersByLogins(Collection<String> logins);

    List<AppUser> getAllAppUsers();
    List<AppUser> getAppUsersByEmail(Collection<String> email);
    List<AppUser> getAppUsersByLogin(Collection<String> login);

}
