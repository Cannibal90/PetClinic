package com.cannibal90.petclinic.WEB.service;

import com.cannibal90.petclinic.DAL.model.Owner;
import com.cannibal90.petclinic.DAL.repository.OwnerRepository;
import com.cannibal90.petclinic.WEB.exception.ExceptionConst;
import com.cannibal90.petclinic.WEB.exception.NoDataFoundException;
import com.cannibal90.petclinic.WEB.exception.WrongParameterException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class OwnerService {

    public final OwnerRepository ownerRepository;

    public Owner getOwnerById(Long id){
        if (id == null || id <= 0) {
            throw new WrongParameterException(ExceptionConst.WRONG_ID_PARAMETER);
        }

        return ownerRepository.findById(id).orElseThrow(() -> {
            throw new NoDataFoundException(ExceptionConst.OWNER_NOT_FOUND);
        });
    }
}
