package com.advantage.mastercredit.payment.services;

import com.advantage.mastercredit.payment.dto.MasterCreditDto;
import com.advantage.mastercredit.payment.dto.MasterCreditResponse;
import com.advantage.mastercredit.payment.dto.ResponseEnum;
import com.advantage.mastercredit.payment.dto.TransactionTypeEnum;
import com.advantage.mastercredit.util.StringHelper;
import com.advantage.mastercredit.util.ValidationHelper;
import com.advantage.mastercredit.util.ArgumentValidationHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <b>MasterCredit</b> MOCK server service. <br/>
 * The {@link MasterCreditResponse#referenceNumber} is set from {@code static}
 * {@link AtomicLong} type property.
 *
 * @author Binyamin Regev on 21/12/2015.
 * @see AtomicLong
 */
@Service
@Transactional
public class MasterCreditService {

    private static AtomicLong masterCreditRefNumber;

    public MasterCreditService() {

        long result = new Date().getTime();

        //  If more than 10 digits than take the 10 right-most digits
        if (result > 9999999999L) {
            result %= 10000000000L;
        }

        //  10 - 10 = 0 => Math.pow(10, 0) = 1 => result *= 1 = result
        int power = 10 - String.valueOf(result).length();
        result *= Math.pow(10, power);

        masterCreditRefNumber = new AtomicLong(result);
    }

    /**
     * {@link AtomicLong#getAndIncrement()} increments the value by 1 and
     * returns the previous value, before the incrementation.
     *
     * @return Value of {@code masterCreditRefNumber} before incrementation.
     */
    public static long referenceNumberNextValue() {
        return masterCreditRefNumber.getAndIncrement();
    }

    /**
     * Do <i>MOCK</i> <b>MasterCredit</b> payment. <br/>
     * Payment is successful unless{@link MasterCreditDto} {@code transactionDate}
     * did not occur yet (is in the future).
     *
     * @param masterCreditDto {@link MasterCreditDto} with payment {@code request} data.
     * @return {@link MasterCreditResponse} <b>MasterCredit</b> server {@code response} information.
     */
    @Transactional
    public MasterCreditResponse doPayment(MasterCreditDto masterCreditDto) {

        ArgumentValidationHelper.validateArgumentIsNotNull(masterCreditDto, "MasterCredit request data");
        ArgumentValidationHelper.validateLongArgumentIsPositive(masterCreditDto.getCardNumber(), "MasterCredit card number");

        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(masterCreditDto.getTransactionType(), "MasterCredit transaction type");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(masterCreditDto.getExpirationDate(), "MasterCredit expiration date");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(masterCreditDto.getCustomerName(), "MasterCredit customer name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(masterCreditDto.getCustomerPhone(), "MasterCredit customer phone");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(String.valueOf(masterCreditDto.getCvvNumber()), "MasterCredit CVV number");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(masterCreditDto.getTransactionDate(), "MasterCredit transaction date");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(String.valueOf(masterCreditDto.getAccountNumber()), "MasterCredit receiving card account number");

        String transactionDate = masterCreditDto.getTransactionDate().substring(0, 2) + "." +
                masterCreditDto.getTransactionDate().substring(2, 4) + "." +
                masterCreditDto.getTransactionDate().substring(4, 8);
        boolean isValid = ValidationHelper.isValidDate(transactionDate);

        isValid = isValid && ValidationHelper.isValidMasterCreditCVVNumber(String.valueOf(masterCreditDto.getCvvNumber()));

        isValid = isValid && ValidationHelper.isValidDate("01." +
                masterCreditDto.getExpirationDate().substring(0, 2) + "." +
                masterCreditDto.getExpirationDate().substring(2, 6));

        isValid = isValid && ValidationHelper.isValidMasterCreditCVVNumber(String.valueOf(masterCreditDto.getCvvNumber()));
        isValid = isValid && ValidationHelper.isValidFullName(masterCreditDto.getCustomerName());

        MasterCreditResponse responseStatus = new MasterCreditResponse();

        responseStatus.setTransactionType(TransactionTypeEnum.PAYMENT.getStringCode());
        responseStatus.setReferenceNumber(this.referenceNumberNextValue());
        //responseStatus.setTransactionDate(new SimpleDateFormat("ddMMyyyy").format(new Date()));   //  Transaction date from current date
        responseStatus.setTransactionDate(masterCreditDto.getTransactionDate());

        //  Check if Transaction Date to determine if transaction APPROVED or REJECTED
        Date date = StringHelper.convertStringToDate(transactionDate, "dd.MM.yyyy");
        if (date.getTime() > new Date().getTime()) {
            responseStatus.setResponse(ResponseEnum.REJECTED.getStringCode());
        } else {
            responseStatus.setResponse(ResponseEnum.APPROVED.getStringCode());
        }

        return responseStatus;
    }

    /**
     * Do <i>Refund</i> {@code request} received and return {@code response}
     *
     * @param paymentId {@code long}. <b>MasterCredit</b> unique payment identification.
     * @param dto       {@code request} data.
     * @return {@link MasterCreditResponse} <b>MasterCredit</b> server {@code response} to {@code request} received.
     */
    @Transactional
    public MasterCreditResponse doRefund(long paymentId, MasterCreditDto dto) {
        MasterCreditResponse responseStatus = new MasterCreditResponse();

        responseStatus.setTransactionType(TransactionTypeEnum.REFUND.getStringCode());
        responseStatus.setResponse(ResponseEnum.APPROVED.getStringCode());
        responseStatus.setReferenceNumber(this.referenceNumberNextValue());
        responseStatus.setTransactionDate(new SimpleDateFormat("ddMMyyyy").format(new Date()));

        return responseStatus;
    }

}
