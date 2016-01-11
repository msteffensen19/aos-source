package com.advantage.accountsoap.init;

import com.advantage.accountsoap.dto.YesNoReply;
import com.advantage.accountsoap.model.Account;
import com.advantage.accountsoap.model.Country;
import com.advantage.accountsoap.util.fs.FileSystemHelper;
import com.advantage.common.Constants;
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
import java.util.List;

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
        File coutriesCSV = filePathCSV.getFile();

        ObjectMapper objectMapper = new ObjectMapper().setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        transaction = session.beginTransaction();

        /* Countries */
        List<String> countries = FileSystemHelper.readFileCsv(coutriesCSV.getAbsolutePath());

        if (countries != null) {
            for (String str : countries) {
                String[] substrings = str.split(",");

                System.out.println("Country: " + substrings[1] +
                        Constants.SPACE + substrings[2] +
                        Constants.SPACE + substrings[3]);

                Country country = new Country(substrings[1], substrings[2], Integer.valueOf(substrings[3]));
                session.persist(country);
            }

            System.out.println("Total of " + countries.size() + " countries loaded");
        } else {
            System.out.println("Countries CSV file might be empty.");
        }

        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "Avinu", "Avraham", "avinu.avraham", "Avraham1", 12, "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "Avinu", "itshak", "avinu.itshak", "Itshak1", 12, "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "Avinu", "jakob", "avinu.jakob", "Israel7", 12, "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));

        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "imenu", "Sara", "sara.imenu", "Saramom2", 18, "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "imenu", "Rivka", "rivka.imenu", "Rivka2", 18, "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "imenu", "Lea", "lea.imenu", "Motherlea2", 18, "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "imenu", "Rachel", "rachel.imenu", "Rachel21", 18, "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));

        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "King", "David", "king.david", "DavidK1", 10, "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "King", "solomon", "king.solomon", "SolomonK2", 10, "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "Queen", "Sheeba", "queen.sheeba", "SheebaQ1", 10, "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));
        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "Queen", "Bat Sheva", "queen.bat-sheva", "BatShevaQ2", 10, "077-7654321", "Jerusalem Region", "Jerusalem", "address", "9876543", "a@b.com", YesNoReply.YES.getReplyTypeChar()));

        session.persist(new Account(AccountType.USER.getAccountTypeCode(), "Fiskin", "Evgeney", "fizpok", "ASas12", 10, "052-4898919", "Jerusalem Region", "Jerusalem", "address", "9876543", "evgeney.fiskin@hpe.com", YesNoReply.YES.getReplyTypeChar()));

        session.persist(new Account(AccountType.ADMIN.getAccountTypeCode(), "Adminov", "Admin", "admin", "adm1n", 10, "052-1234567", "Jerusalem Region", "Jerusalem", "address", "9876543", "admin@admin.ad", YesNoReply.YES.getReplyTypeChar()));
        transaction.commit();
    }
}
