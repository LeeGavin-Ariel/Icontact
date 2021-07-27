package com.myapp.backend.service;

import com.myapp.backend.domain.dto.request.RequestDto;
import com.myapp.backend.domain.entity.Dosage;
import com.myapp.backend.domain.entity.Returnhome;
import com.myapp.backend.repository.DosageRepository;
import com.myapp.backend.repository.ReturnhomeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;



@Service
public class RequestServiceImpl implements RequestService{

    private static final int SUCCESS = 1;
    private static final int FAIL = -1;

    @Autowired
    DosageRepository dosageRepository;

    @Autowired
    ReturnhomeRepository returnhomeRepository;

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

}
