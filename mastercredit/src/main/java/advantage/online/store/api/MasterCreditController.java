package advantage.online.store.api;

import advantage.online.store.Constants;
import advantage.online.store.dto.MasterCreditDto;
import advantage.online.store.dto.MasterCreditResponseStatus;
import advantage.online.store.dto.ResponseEnum;
import advantage.online.store.services.MasterCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * @author Binyamin Regev on 20/12/2015.
 */
@RestController
@RequestMapping(value = Constants.URI_API + "/v1")
public class MasterCreditController {

    @Autowired
    private MasterCreditService masterCreditService;

    /**
     *
     * @param masterCreditDto
     * @param request
     * @param response
     * @return {@link MasterCreditResponseStatus} <b>MasterCredit</b> server {@code response} for <i>Payment</i> {@code request}
     */
    @RequestMapping(value = "/payments/payment", method = RequestMethod.POST)
    public ResponseEntity<MasterCreditResponseStatus> doPayment(@RequestBody MasterCreditDto masterCreditDto,
                                                                HttpServletRequest request,
                                                                HttpServletResponse response) {

        MasterCreditResponseStatus responseStatus = masterCreditService.doPayment(masterCreditDto);
        //response.setHeader("sessionId", request.getSession().getId());
        //
        //if (appUserResponseStatus.isSuccess()) {
        //    HttpSession session = request.getSession();
        //    session.setAttribute(Constants.UserSession.TOKEN, appUserResponseStatus.getToken());
        //    session.setAttribute(Constants.UserSession.USER_ID, appUserResponseStatus.getUserId());
        //    session.setAttribute(Constants.UserSession.IS_SUCCESS, appUserResponseStatus.isSuccess());
        //
        //    //  Set SessionID to Response Entity
        //    //response.getHeader().
        //    appUserResponseStatus.setSessionId(session.getId());
        //
        //
        //    return new ResponseEntity<>(appUserResponseStatus, HttpStatus.OK);
        //} else {
        //    return new ResponseEntity<>(appUserResponseStatus, HttpStatus.NOT_FOUND);
        //}


        /*
        In case of an error return HttpStatus.CONFLICT
         */
        return new ResponseEntity<>(responseStatus, HttpStatus.OK);

    }

    /**
     * Sends <i>Refund</i> {@code request} to <b>MasterCredit</b> server and receives its {@code response}.
     * @see <a href="http://www.iana.org/assignments/http-status-codes">HTTP Status Code Registry</a>
     * @see <a href="http://en.wikipedia.org/wiki/List_of_HTTP_status_codes">List of HTTP status codes - Wikipedia</a>
     * @param paymentId {@code long}. <b>MasterCredit</b> unique payment identification.
     * @param masterCreditDto <b>MasterCredit</b> <i>Refund</i> {@code request} data.
     * @param request {@link HttpServletRequest}. Handled Internally.
     * @param response {@link HttpServletResponse}. Handled Internally.
     * @return {@link MasterCreditResponseStatus} <b>MasterCredit</b> server {@code response} for <i>Refund</i> {@code request}
     */
    @RequestMapping(value = "/payments/{payment_id}/refund", method = RequestMethod.POST)
    public ResponseEntity<MasterCreditResponseStatus> doRefund(@RequestBody MasterCreditDto masterCreditDto,
                                                               @PathVariable("payment_id") long paymentId,
                                                               HttpServletRequest request,
                                                               HttpServletResponse response) {

        MasterCreditResponseStatus responseStatus = masterCreditService.doRefund(paymentId, masterCreditDto);

        if (responseStatus.getMCResponse().equalsIgnoreCase(ResponseEnum.REJECTED.getStringCode())) {
            return new ResponseEntity<>(responseStatus, HttpStatus.CONFLICT);   //  Something went wrong
        } else {
            return new ResponseEntity<>(responseStatus, HttpStatus.OK);
        }

    }


}
