package advantage.online.store.services;

import advantage.online.store.dto.MasterCreditDto;
import advantage.online.store.dto.MasterCreditResponseStatus;
import advantage.online.store.dto.ResponseEnum;
import advantage.online.store.dto.TransactionTypeEnum;
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
     * Payment is successful unless{@link MasterCreditDto#transactionDate}
     * did not occur yet (is in the future).
     * @param masterCreditDto {@link MasterCreditDto} with payment {@code request} data.
     * @return {@link MasterCreditResponseStatus} <b>MasterCredit</b> server {@code response} information.
     */
    @Transactional
    public MasterCreditResponseStatus doPayment(MasterCreditDto masterCreditDto) {
        MasterCreditResponseStatus responseStatus = new MasterCreditResponseStatus();

        responseStatus.setMCTransactionType(TransactionTypeEnum.REFUND.getStringCode());
        responseStatus.setMCResponse(ResponseEnum.APPROVED.getStringCode());
        responseStatus.setMCRefNumber(this.referenceNumberNextValue());
        responseStatus.setTransactionDate(new SimpleDateFormat("ddMMyyyy").format(new Date()));

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
