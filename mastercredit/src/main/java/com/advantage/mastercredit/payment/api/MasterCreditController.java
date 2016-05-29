package com.advantage.mastercredit.payment.api;

import com.advantage.mastercredit.payment.dto.MasterCreditDto;
import com.advantage.mastercredit.payment.dto.MasterCreditResponse;
import com.advantage.common.enums.ResponseEnum;
import com.advantage.mastercredit.payment.services.MasterCreditService;
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
public class MasterCreditController {

    @Autowired
    private MasterCreditService masterCreditService;

    @ModelAttribute
    public void setResponseHeaderForAllRequests(HttpServletResponse response) {
//        response.setHeader(com.google.common.net.HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN, "*");
        response.setHeader("Expires", "0");
        response.setHeader("Cache-control", "no-store");
    }

    /**
     * @param masterCreditDto
     * @param request
     * @param response
     * @return {@link MasterCreditResponse} <b>MasterCredit</b> server {@code response} for <i>Payment</i> {@code request}
     */
    @RequestMapping(value = "/payments/payment", method = RequestMethod.POST)
    public ResponseEntity<MasterCreditResponse> doPayment(@RequestBody MasterCreditDto masterCreditDto,
                                                          HttpServletRequest request,
                                                          HttpServletResponse response) {

        System.out.println("MasterCreditController.doPayment");
        MasterCreditResponse masterCreditResponse = masterCreditService.doPayment(masterCreditDto);
        //response.setHeader("sessionId", request.getSession().getId());
        //
        //if (appUserResponseStatus.isSuccess()) {
        //    HttpSession session = request.getSession();
        //    session.setAttribute(Constants_mc.UserSession.TOKEN, appUserResponseStatus.getToken());
        //    session.setAttribute(Constants_mc.UserSession.USER_ID, appUserResponseStatus.getUserId());
        //    session.setAttribute(Constants_mc.UserSession.IS_SUCCESS, appUserResponseStatus.isSuccess());
        //
        //    //  Set SessionID to Response Entity
        //    //response.getHeader().
        //    appUserResponseStatus.setSessionId(session.getId());
        //}

        if (masterCreditResponse.getResponseCode().equalsIgnoreCase(ResponseEnum.APPROVED.getStringCode())) {
            System.out.println("MasterCreditController.doPayment - HttpStatus.OK");
            //return new ResponseEntity<>(masterCreditResponse, HttpStatus.OK);
            return new ResponseEntity<>(masterCreditResponse, HttpStatus.CREATED);
        } else {
            System.out.println("MasterCreditController.doPayment - HttpStatus.CONFLICT");
            return new ResponseEntity<>(masterCreditResponse, HttpStatus.CONFLICT);
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
//     * @return {@link MasterCreditResponse} <b>MasterCredit</b> server {@code response} for <i>Refund</i> {@code request}
//     */
//    @RequestMapping(value = "/payments/{payment_id}/refund", method = RequestMethod.POST)
//    public ResponseEntity<MasterCreditResponse> doRefund(@RequestBody MasterCreditDto masterCreditDto,
//                                                         @PathVariable("payment_id") long paymentId,
//                                                         HttpServletRequest request,
//                                                         HttpServletResponse response) {
//
//        MasterCreditResponse masterCreditResponse = masterCreditService.doRefund(paymentId, masterCreditDto);
//
//        if (masterCreditResponse.getResponse().equalsIgnoreCase(ResponseEnum.APPROVED.getStringCode())) {
//            return new ResponseEntity<>(masterCreditResponse, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(masterCreditResponse, HttpStatus.CONFLICT);   //  Something went wrong
//        }
//
//    }

}
