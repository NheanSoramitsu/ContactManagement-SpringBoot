package com.soramitsukhmer.contactmanagement.service.validation

import com.soramitsukhmer.contactmanagement.repository.CompanyRepository
import org.springframework.stereotype.Service

@Service
class CompanyValidationService(
        val companyRepository: CompanyRepository
){
    fun validateUniquePhone(companyId: Long?, phone: String){
        companyId?.let {
            if(companyRepository.existsCompanyByPhoneAndIdIsNot(phone, it)){
                throw RuntimeException("[Phone:$phone] is already existed.")
            }
        }
        if(companyRepository.existsCompanyByPhone(phone)){
            throw RuntimeException("Phone must be unique")
        }
    }
}