package com.advantage.accountsoap.init;

import com.advantage.accountsoap.dto.YesNoReply;
import com.advantage.accountsoap.model.Account;
import com.advantage.accountsoap.model.Country;
import com.advantage.accountsoap.util.fs.FileSystemHelper;
import com.advantage.common.dto.AccountType;
import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManagerFactory;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class InitDataBase {
    @Autowired
    private EntityManagerFactory entityManagerFactory;

    public void init() throws Exception {

        SessionFactory sessionFactory = entityManagerFactory.unwrap(SessionFactory.class);
        Session session = sessionFactory.openSession();
        Transaction transaction;

        //  Get countries list in CSV (Comma Separated Values) file
        ClassPathResource filePathCSV = new ClassPathResource("countries_20150630.csv");
        File countriesCSV = filePathCSV.getFile();

        ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        transaction = session.beginTransaction();

        /* Countries */
        List<String> countries = FileSystemHelper.readFileCsv(countriesCSV.getAbsolutePath());
        Map<Long, Country> countryMap = new HashMap<>();

        for (String str : countries) {
            String[] substrings = str.split(",");
            Country country = new Country(substrings[1], substrings[2], Integer.valueOf(substrings[3]));
            session.persist(country);
            countryMap.put(country.getId(), country);
        }
        transaction.commit();
        transaction = session.beginTransaction();

        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "Avinu", "Avraham", "avinu.avraham", "Avraham1", countryMap.get(12l), "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "Avinu", "itshak", "avinu.itshak", "Itshak1", countryMap.get(12l), "077-7654321", "J Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "Avinu", "jakob", "avinu.jakob", "Israel7", countryMap.get(12l), "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));

        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "imenu", "Sara", "sara.imenu", "Saramom2", countryMap.get(18l), "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "imenu", "Rivka", "rivka.imenu", "Rivka2", countryMap.get(18l), "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "imenu", "Lea", "lea.imenu", "Motherlea2", countryMap.get(18l), "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "imenu", "Rachel", "rachel.imenu", "Rachel21", countryMap.get(18l), "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));

        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "King", "David", "king.david", "DavidK1", countryMap.get(10l), "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "King", "solomon", "king.solomon", "SolomonK2", countryMap.get(10l), "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "Queen", "Sheeba", "queen.sheeba", "SheebaQ1", countryMap.get(10l), "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "Queen", "Bat Sheva", "queen.bat-sheva", "BatShevaQ2", countryMap.get(10l), "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));

        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "Fiskin", "Evgeney", "fizpok", "ASas12", countryMap.get(10l), "052-4898919", "Jerusalem Region", "Jerusalem", "address", "9876543", "evgeney.fiskin@hpe.com", YesNoReply.YES.getReplyTypeChar()));

        transaction.commit();
    }
}
