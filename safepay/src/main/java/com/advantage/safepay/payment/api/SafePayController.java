package com.advantage.safepay.payment.api;

import com.advantage.safepay.payment.dto.SafePayDto;
import com.advantage.safepay.payment.dto.SafePayResponse;
import com.advantage.safepay.payment.dto.ResponseEnum;
import com.advantage.safepay.payment.services.SafePayService;
import com.advantage.common.Constants;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author Binyamin Regev on 20/12/2015.
 */
@RestController
@RequestMapping(value = Constants.URI_API + "/v1")
public class SafePayController {

    @Autowired
    private SafePayService safePayService;

    @ModelAttribute
    public void setResponseHeaderForAllRequests(HttpServletResponse response) {
        response.addHeader(com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
    }

    /**
     * @param safePayDto
     * @param request
     * @param response
     * @return {@link SafePayResponse} <b>MasterCredit</b> server {@code response} for <i>Payment</i> {@code request}
     */
    @RequestMapping(value = "/payments/payment", method = RequestMethod.POST)
    public ResponseEntity<SafePayResponse> doPayment(@RequestBody SafePayDto safePayDto,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) {

        SafePayResponse safePayResponse = safePayService.doPayment(safePayDto);
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
        //}

        if (safePayResponse.getResponseCode().equalsIgnoreCase(ResponseEnum.APPROVED.getStringCode())) {
            return new ResponseEntity<>(safePayResponse, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(safePayResponse, HttpStatus.CONFLICT);
        }

    }

//    /**
//     * Sends <i>Refund</i> {@code request} to <b>MasterCredit</b> server and receives its {@code response}.
//     * @see <a href="http://www.iana.org/assignments/http-status-codes">HTTP Status Code Registry</a>
//     * @see <a href="http://en.wikipedia.org/wiki/List_of_HTTP_status_codes">List of HTTP status codes - Wikipedia</a>
//     * @param paymentId {@code long}. <b>MasterCredit</b> unique payment identification.
//     * @param masterCreditDto <b>MasterCredit</b> <i>Refund</i> {@code request} data.
//     * @param request {@link HttpServletRequest}. Handled Internally.
//     * @param response {@link HttpServletResponse}. Handled Internally.
//     * @return {@link SafePayResponse} <b>MasterCredit</b> server {@code response} for <i>Refund</i> {@code request}
//     */
//    @RequestMapping(value = "/payments/{payment_id}/refund", method = RequestMethod.POST)
//    public ResponseEntity<SafePayResponse> doRefund(@RequestBody SafePayDto masterCreditDto,
//                                                         @PathVariable("payment_id") long paymentId,
//                                                         HttpServletRequest request,
//                                                         HttpServletResponse response) {
//
//        SafePayResponse masterCreditResponse = masterCreditService.doRefund(paymentId, masterCreditDto);
//
//        if (masterCreditResponse.getResponse().equalsIgnoreCase(ResponseEnum.APPROVED.getStringCode())) {
//            return new ResponseEntity<>(masterCreditResponse, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(masterCreditResponse, HttpStatus.CONFLICT);   //  Something went wrong
//        }
//
//    }

}
