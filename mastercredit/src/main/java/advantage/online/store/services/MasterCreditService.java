package advantage.online.store.services;

import advantage.online.store.dto.MasterCreditDto;
import advantage.online.store.dto.MasterCreditResponseStatus;
import advantage.online.store.dto.ResponseEnum;
import advantage.online.store.dto.TransactionTypeEnum;
import advantage.util.ArgumentValidationHelper;
import advantage.util.ValidationHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicLong;

/**
 * <b>MasterCredit</b> MOCK server service. <br/>
 * The {@link MasterCreditResponseStatus#MCRefNumber} is set from {@code static}
 * {@link AtomicLong} type property.
 * @see AtomicLong
 * @author Binyamin Regev on 21/12/2015.
 */
@Service
@Transactional
public class MasterCreditService {

    private static AtomicLong masterCreditRefNumber = new AtomicLong(new Date().getTime());

    /**
     * {@link AtomicLong#getAndIncrement()} increments the value by 1 and
     * returns the previous value, before the incrementation.
     * @return Value of {@code masterCreditRefNumber} before incrementation.
     */
    public static long referenceNumberNextValue() {
        return masterCreditRefNumber.getAndIncrement();
    }

    /**
     * Do <i>MOCK</i> <b>MasterCredit</b> payment. <br/>
     * Payment is successful unless{@link MasterCreditDto} {@code transactionDate}
     * did not occur yet (is in the future).
     * @param masterCreditDto {@link MasterCreditDto} with payment {@code request} data.
     * @return {@link MasterCreditResponseStatus} <b>MasterCredit</b> server {@code response} information.
     */
    @Transactional
    public MasterCreditResponseStatus doPayment(MasterCreditDto masterCreditDto) {

        ArgumentValidationHelper.validateArgumentIsNotNull(masterCreditDto, "MasterCredit request data");
        ArgumentValidationHelper.validateLongArgumentIsPositive(masterCreditDto.getMCCardNumber(), "MasterCredit card number");

        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(masterCreditDto.getMCTransactionType(), "MasterCredit transaction type");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(masterCreditDto.getMCExpirationdate(), "MasterCredit expiration date");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(masterCreditDto.getMCCustomerName(), "MasterCredit customer name");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(masterCreditDto.getCustomerphone(), "MasterCredit customer phone");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(masterCreditDto.getMCCVVNumber(), "MasterCredit CVV number");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(masterCreditDto.getTransactionDate(), "MasterCredit transaction date");
        ArgumentValidationHelper.validateStringArgumentIsNotNullAndNotBlank(masterCreditDto.getAccountNumber(), "MasterCredit receiving card account number");

        boolean isValid = ValidationHelper.isValidDate(masterCreditDto.getTransactionDate().substring(1, 2) + "." +
                                                        masterCreditDto.getTransactionDate().substring(4, 5) + "." +
                                                        masterCreditDto.getTransactionDate().substring(6, 8));

        isValid = isValid && ValidationHelper.isValidMasterCreditCVVNumber(masterCreditDto.getMCCVVNumber());

        isValid = isValid && ValidationHelper.isValidDate("01." +
                                    masterCreditDto.getMCExpirationdate().substring(1, 2) + "." +
                                    masterCreditDto.getMCExpirationdate().substring(3, 6));

        isValid = isValid && ValidationHelper.isValidMasterCreditCVVNumber(masterCreditDto.getMCCVVNumber());
        isValid = isValid && ValidationHelper.isValidFullName(masterCreditDto.getMCCustomerName());

        MasterCreditResponseStatus responseStatus = new MasterCreditResponseStatus();

        responseStatus.setMCTransactionType(TransactionTypeEnum.PAYMENT.getStringCode());
        responseStatus.setMCRefNumber(this.referenceNumberNextValue());
        //responseStatus.setTransactionDate(new SimpleDateFormat("ddMMyyyy").format(new Date()));
        responseStatus.setTransactionDate(masterCreditDto.getTransactionDate());

        if (isValid) {
            responseStatus.setMCResponse(ResponseEnum.APPROVED.getStringCode());
        } else {
            responseStatus.setMCResponse(ResponseEnum.REJECTED.getStringCode());
        }

        return responseStatus;
    }

    /**
     * Do <i>Refund</i> {@code request} received and return {@code response}
     * @param paymentId {@code long}. <b>MasterCredit</b> unique payment identification.
     * @param dto {@code request} data.
     * @return {@link MasterCreditResponseStatus} <b>MasterCredit</b> server {@code response} to {@code request} received.
     */
    @Transactional
    public MasterCreditResponseStatus doRefund(long paymentId, MasterCreditDto dto) {
        MasterCreditResponseStatus responseStatus = new MasterCreditResponseStatus();

        responseStatus.setMCTransactionType(TransactionTypeEnum.REFUND.getStringCode());
        responseStatus.setMCResponse(ResponseEnum.APPROVED.getStringCode());
        responseStatus.setMCRefNumber(this.referenceNumberNextValue());
        responseStatus.setTransactionDate(new SimpleDateFormat("ddMMyyyy").format(new Date()));

        return responseStatus;
    }

}
