package com.myapp.backend.service;

import com.myapp.backend.dao.DosageDao;
import com.myapp.backend.dao.ReturnhomeDao;
import com.myapp.backend.domain.dto.request.DosageResultDto;
import com.myapp.backend.domain.dto.request.RequestDto;
import com.myapp.backend.domain.dto.request.ReturnhomeResultDto;
import com.myapp.backend.domain.entity.Dosage;
import com.myapp.backend.domain.entity.Returnhome;
import com.myapp.backend.repository.DosageRepository;
import com.myapp.backend.repository.ReturnhomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class RequestServiceImpl implements RequestService{

    private static final int SUCCESS = 1;
    private static final int FAIL = -1;

    @Autowired
    DosageRepository dosageRepository;

    @Autowired
    ReturnhomeRepository returnhomeRepository;

    @Autowired
    DosageDao dosageDao;

    @Autowired
    ReturnhomeDao returnhomeDao;

    @Override
    public int dosageInsert(RequestDto requestDto) {
        Dosage dosage = new Dosage();

        dosage.setUserId(requestDto.getUserId());
        dosage.setSymptom(requestDto.getSymptom());
        dosage.setMedicineType(requestDto.getMedicineType());
        dosage.setDosageVol(requestDto.getDosageVol());
        dosage.setDosageCnt(requestDto.getDosageCnt());
        dosage.setDosageTime(requestDto.getDosageTime());
        dosage.setStorage(requestDto.getStorage());
        dosage.setSpecialNote(requestDto.getSpecialNote());
        System.out.println(dosage.getUserId());
        if(dosageRepository.save(dosage) != null){
            return SUCCESS;
        }
        return FAIL;
    }

    @Override
    public int returnhomeInsert(RequestDto requestDto) {

        Returnhome returnhome = new Returnhome();
        returnhome.setUserId(requestDto.getUserId());
        returnhome.setRhDate(requestDto.getRhDate());
        returnhome.setRhTime(requestDto.getRhTime());
        returnhome.setGuardian(requestDto.getGuardian());
        returnhome.setGuardianTel(requestDto.getGuardianTel());
        returnhome.setEmergency(requestDto.getEmergency());
        returnhome.setEmergencyTel(requestDto.getEmergencyTel());

        if(returnhomeRepository.save(returnhome) != null){
            return SUCCESS;
        }
        return FAIL;
    }


    @Override
    public int dosageDelete(int id) {
        try{
            dosageRepository.deleteByDosageId(id);
        } catch (EmptyResultDataAccessException e){
            return FAIL;
        }
        return SUCCESS;
    }

    @Override
    public int returnhomeDelete(int id) {
        try{
            returnhomeRepository.deleteByRhId(id);
        } catch (EmptyResultDataAccessException e){
            return FAIL;
        }
        return SUCCESS;
    }

    @Override
    public int dosageUpdate(RequestDto requestDto) {
        Dosage dosage = dosageRepository.findByDosageId(requestDto.getId());
        if(dosage != null){
            dosage.setSymptom(requestDto.getSymptom());
            dosage.setMedicineType(requestDto.getMedicineType());
            dosage.setDosageVol(requestDto.getDosageVol());
            dosage.setDosageCnt(requestDto.getDosageCnt());
            dosage.setDosageTime(requestDto.getDosageTime());
            dosage.setStorage(requestDto.getStorage());
            dosage.setSpecialNote(requestDto.getSpecialNote());
            if(dosageRepository.save(dosage) != null){
                return SUCCESS;
            }
        }
        return FAIL;
    }

    @Override
    public int returnhomeUpdate(RequestDto requestDto) {
        Returnhome returnhome = returnhomeRepository.findByRhId(requestDto.getId());
        if(returnhome != null){
            returnhome.setRhDate(requestDto.getRhDate());
            returnhome.setRhTime(requestDto.getRhTime());
            returnhome.setGuardian(requestDto.getGuardian());
            returnhome.setGuardianTel(requestDto.getGuardianTel());
            returnhome.setEmergency(requestDto.getEmergency());
            returnhome.setEmergencyTel(requestDto.getEmergencyTel());

            if(returnhomeRepository.save(returnhome) != null){
                return SUCCESS;
            }
        }
        return FAIL;
    }

    @Override
    public List<DosageResultDto> teacherDosageList(String userId) {
        List<DosageResultDto> dosageList = null;
        dosageList = dosageDao.teacherDosageList(userId);
        return dosageList;
    }

    @Override
    public List<ReturnhomeResultDto> teacherReturnhomeList(String userId) {
        List<ReturnhomeResultDto> returnhomeList = null;
        returnhomeList = returnhomeDao.teacherReturnhomeList(userId);
        return returnhomeList;
    }

    @Override
    public List<DosageResultDto> parentDosageList(String userId) {
        List<DosageResultDto> dosageList = null;
        dosageList = dosageDao.parentDosageList(userId);
        return dosageList;
    }

    @Override
    public List<ReturnhomeResultDto> parentReturnhomeList(String userId) {
        List<ReturnhomeResultDto> returnhomeList = null;
        returnhomeList = returnhomeDao.parentReturnhomeList(userId);
        return returnhomeList;
    }

    @Override
    public DosageResultDto dosageDetail(int id) {
        DosageResultDto dosageResultDto = null;
        dosageResultDto = dosageDao.dosageDetail(id);
        return dosageResultDto;
    }

    @Override
    public ReturnhomeResultDto returnhomeDetail(int id) {
        ReturnhomeResultDto returnhomeResultDto = null;
        returnhomeResultDto = returnhomeDao.returnhomeDetail(id);
        return returnhomeResultDto;
    }


}
