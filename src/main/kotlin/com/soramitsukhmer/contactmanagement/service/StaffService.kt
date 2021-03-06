package com.soramitsukhmer.contactmanagement.service

import com.soramitsukhmer.contactmanagement.api.request.FilterParamsStaffDTO
import com.soramitsukhmer.contactmanagement.api.request.RequestStaffDTO
import com.soramitsukhmer.contactmanagement.api.request.StaffDTO
import com.soramitsukhmer.contactmanagement.domain.model.Staff
import com.soramitsukhmer.contactmanagement.domain.spec.StaffSpec
import com.soramitsukhmer.contactmanagement.repository.CompanyRepository
import com.soramitsukhmer.contactmanagement.repository.StaffRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.Pageable
import org.springframework.data.jpa.domain.Specification
import org.springframework.stereotype.Service
import java.lang.RuntimeException

@Service
class StaffService(
        val staffRepository: StaffRepository,
        val companyRepository: CompanyRepository
){
    fun listAllStaffs(filterParamsStaffDTO: FilterParamsStaffDTO?, pageable: Pageable) : Page<StaffDTO> {
        val querySpec = filterParamsStaffDTO?.q?.let { StaffSpec.genSearchSpec(it.toLowerCase()) }
        val companySpec = filterParamsStaffDTO?.companyId?.let { StaffSpec.genFilterCompany(it) }
        return staffRepository.findAll(
                Specification.where(companySpec)?.and(querySpec),
                pageable
        ).map{ it.toDTO() }
    }

    fun getStaff(id : Long) : StaffDTO{
        return staffRepository.findById(id).orElseThrow {
            throw RuntimeException("StaffId[$id] is not found.")
        }.toDTO()
    }

    fun createStaff(reqStaffDTO: RequestStaffDTO) : StaffDTO{
        val company = companyRepository.findById(reqStaffDTO.company).orElseThrow {
            throw RuntimeException("CompanyId[${reqStaffDTO.company}] is not found.")
        }
        val newStaff = Staff.fromReqDTO(reqStaffDTO, company)
        return staffRepository.save(newStaff).toDTO()
    }

    fun updateStaff(id: Long, reqStaffDTO: RequestStaffDTO) : StaffDTO{
        val company = companyRepository.findById(reqStaffDTO.company).orElseThrow {
            throw RuntimeException("CompanyId[${reqStaffDTO.company}] is not found.")
        }
        val staff = staffRepository.findById(id).orElseThrow {
            throw RuntimeException("StaffId[$id] is not found.")
        }
        return staffRepository.save(staff.updateStaff(reqStaffDTO, company)).toDTO()
    }

    fun deleteStaff(id: Long) : String{
        val staff = staffRepository.findById(id).orElseThrow {
            throw RuntimeException("StaffId[$id] is not found.")
        }
        staffRepository.delete(staff)
        return "DELETED"
    }
}