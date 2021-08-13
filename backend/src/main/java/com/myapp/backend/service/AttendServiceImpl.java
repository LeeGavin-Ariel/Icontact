package com.myapp.backend.service;

import com.myapp.backend.domain.dto.attend.AttendDto;
import com.myapp.backend.domain.entity.Attend;
import com.myapp.backend.domain.entity.User;
import com.myapp.backend.repository.AttendRepository;
import com.myapp.backend.repository.UserRepository;
import org.apache.tomcat.jni.Local;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.StringTokenizer;

@Service
public class AttendServiceImpl implements AttendService{

    @Autowired
    AttendRepository attendRepository;

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public int writeAttend(String userId, Date date) {
        //1 : 출석함 //0 : 결석함

        try {
            Attend result = attendRepository.findByUserIdAndDate(userId, date);
            result.setAttend(result.getAttend()^1);//현재 상태 스왑
            attendRepository.save(result);

            return result.getAttend();
        }catch (Exception e){
            e.printStackTrace();
            return -1;
        }
    }

    @Override
    public List<AttendDto> attendParents(String userId, Date date) {


        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM");
        SimpleDateFormat f2 = new SimpleDateFormat("yyyy-MM-dd");

        String dateStr = f.format(date);
        int year = Integer.parseInt(dateStr.substring(0,4));
        int month = Integer.parseInt(dateStr.substring(5,7));

        List<AttendDto> result=new ArrayList<>();
        try{
            List<Attend> attendList = attendRepository.findByUserIdAndDateGreaterThanAndDateLessThan(userId,
                    f2.parse(year+"-"+month+"-00"),
                    f2.parse(year+"-"+(month+1)+"-01"));

            for (Attend u : attendList){
                StringTokenizer st = new StringTokenizer(u.getDate().toString(), "-");
                LocalDate localDate = LocalDate.of(Integer.parseInt(st.nextToken())
                        ,Integer.parseInt(st.nextToken())
                        ,Integer.parseInt(st.nextToken()));

                int day = localDate.getDayOfWeek().getValue();

                AttendDto temp = new AttendDto();
                temp.setUserId(u.getUserId());
                temp.setDate(u.getDate());
                temp.setAttend(((day==6)||(day==7)) ? 9 : u.getAttend());
                result.add( temp );
            }

            return result;
        }catch (Exception e){
            e.printStackTrace();
            return null;
        }

    }

    @Override
    public List<AttendDto> attendTeacher(String userId, Date date) {

        List<AttendDto> result=new ArrayList<>();
        try{
            LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

            int day = localDate.getDayOfWeek().getValue();
            //주말
            if(day==6||day==7){
                return result;
            }

            String classCode = userRepository.findByUserId(userId).getClassCode();
            List<User> userList = userRepository.findByClassCodeStartsWith(classCode);
            for (User u : userList){

                if(u.getType()==2)continue;

                Attend attend = attendRepository.findByUserIdAndDate(u.getUserId(), date);

                if(attend==null)continue;

                result.add(
                        new AttendDto(u.getUserId(),
                                u.getKidName(),
                                u.getProfileImg(),
                                attend.getDate(),
                                attend.getAttend())
                );
            }

            return result;
        }catch (Exception e){
            e.printStackTrace();
            return new ArrayList<>();
        }
    }
}
