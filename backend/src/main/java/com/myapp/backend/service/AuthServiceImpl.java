package com.myapp.backend.service;

import com.myapp.backend.domain.dto.AuthDto;
import com.twilio.Twilio;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Random;

@Service
public class AuthServiceImpl implements AuthService{
    //SMS전송키
    private final static String ACCOUNT_SID="ACab98c24f7f094b4e0405a590d3ad3bef";
    private final static String AUTH_TOKEN="f2195a37e709ffdc2b7f76e9df1c4298";

    private HashMap<String, String> authMap=new HashMap<>();

    @Override
    public boolean sendSms(AuthDto autoDto) {
        //랜덤코드생성
        Random random = new Random();
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
            //        Message msg = Message.creator(new PhoneNumber("+82"+autoDto.phoneNum.substring(1)),
            //                new PhoneNumber("+14695302245"),
            //                body)
            //                .create();

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
                System.out.println("auth!");
                return true;
            } else {
                System.out.println("not auth!");
                return false;
            }
        }catch (Exception e){
            e.printStackTrace();
            return false;

        }
    }

}
