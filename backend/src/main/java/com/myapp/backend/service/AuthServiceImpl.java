package com.myapp.backend.service;

import com.myapp.backend.domain.dto.join.AuthDto;
import com.twilio.Twilio;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;

@Service
public class AuthServiceImpl implements AuthService{
    //SMS전송키

    @Value("${ACCOUNT_SID}")
    private String ACCOUNT_SID;

    @Value("${AUTH_TOKEN}")
    private String AUTH_TOKEN;

    @Value("${PHONE_NUMBER}")
    private String PHONE_NUMBER;

    private HashMap<String, String> authMap=new HashMap<>();

    @Override
    public boolean sendSms(AuthDto autoDto) {
        //랜덤코드생성
        int num = ((int)(Math.random()*100000))%10000;
        if(num<1000)num+=1000;
        String code = Integer.toString(num);

        //인증요청
        Twilio.init(ACCOUNT_SID,AUTH_TOKEN);
        //전송 메세지
        String body = "입력 코드는 ["+code+"]입니다.";

        try{
            //받을 번호 / 보낼 번호(고정-작성자 이현건의 twilio 발급 가상번호) / 메세지
            //유료임! 주석을 함부로 해제하지 마시오!
                    Message.creator(new PhoneNumber("+82"+autoDto.phoneNum.substring(1)),
                            new PhoneNumber(PHONE_NUMBER),
                            body)
                            .create();

            //인증용 map에 삽입
            authMap.put(autoDto.getPhoneNum(), code);

            return true;
        }catch (Exception e){
            e.printStackTrace();
            return false;
        }
    }


    @Override
    public boolean authSms(String phoneNum, String code) {
        //for test
        //String num = "01012345678";
        //authMap.put(num,1234);
        //http://localhost:8080/auth/sms?phoneNum=01012345678&code=1234

        //보낸 code와 입력 code동일한지 확인하기

        try {
            if (code.equals(authMap.get( phoneNum ))) {
                return true;
            } else {
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;

        }
    }

}
