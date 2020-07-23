package com.soramitsukhmer.contactmanagement.service

import com.soramitsukhmer.contactmanagement.api.exception.IDNotFoundException
import com.soramitsukhmer.contactmanagement.api.request.CompanyDTO
import com.soramitsukhmer.contactmanagement.api.request.FilterParamsStaffDTO
import com.soramitsukhmer.contactmanagement.api.request.RequestCompanyDTO
import com.soramitsukhmer.contactmanagement.domain.model.Company
import com.soramitsukhmer.contactmanagement.repository.CompanyRepository
import com.soramitsukhmer.contactmanagement.service.validation.CompanyValidationService
import org.springframework.data.domain.Pageable
import org.springframework.stereotype.Service

@Service
class CompanyService(
        val companyRepository: CompanyRepository,
        val companyValidationService: CompanyValidationService
){
    fun listAllCompanies() : List<CompanyDTO>{
        return companyRepository.findAll().map { it.toDTO() }
    }

    fun getCompany(id: Long) : CompanyDTO{
        return companyRepository.findById(id).orElseThrow{
            throw RuntimeException("CompanyId[$id] is not found.")
        }.toDTO()
    }

    fun createCompany(reqCompanyDTO: RequestCompanyDTO) : CompanyDTO{
        val newCompany = Company.fromReqDTO(reqCompanyDTO)
        companyValidationService.validateUniquePhone(null, newCompany.phone)
        return companyRepository.save(newCompany).toDTO()
    }

    fun updateCompany(id: Long, reqCompanyDTO: RequestCompanyDTO) : CompanyDTO{
        val company = companyRepository.findById(id).orElseThrow{
            throw RuntimeException("CompanyId[$id] is not found.")
        }.updateCompany(reqCompanyDTO)
        companyValidationService.validateUniquePhone(company.id, company.phone)
        return companyRepository.save(company).toDTO()
    }

    fun deleteCompany(id: Long) : String{
        val company = companyRepository.findById(id).orElseThrow{
            throw IDNotFoundException("CompanyId[$id] is not found.")
        }
        companyRepository.delete(company)
        return "DELETED"
    }
}