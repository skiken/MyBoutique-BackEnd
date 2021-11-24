package com.trainting.MyBoutique.apiSMS;


import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
@Component
public class SmsService {

    private final String ACCOUNT_SID ="ACc0ff86d7c60adb42d780ef3a30b921d1";

    private final String AUTH_TOKEN = "1b0bc96cbf34a25eeef2ddd997c24a68";

    private final String FROM_NUMBER = "+14704106254";

    public void send(SmsPojo sms) {
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);

        Message message = Message.creator(new PhoneNumber(sms.getTo()), new PhoneNumber(FROM_NUMBER), sms.getMessage())
                .create();
        System.out.println("here is my id:"+message.getSid());// Unique resource ID created to manage this transaction

    }

    public void receive(MultiValueMap<String, String> smscallback) {
    }

}
