package com.soramitsukhmer.contactmanagement.service

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.util.AssertionErrors.*

@SpringBootTest
class CompanyServiceTest(
){
    @Autowired
    lateinit var companyService: CompanyService

    @Test
    fun testCreateCompany(){
        val createdCompany = companyService.createCompany(CompanyServiceTestHelper.validCompanyReqDTO)
//        assertTrue("Created Company Is Not Null", createdCompany.id != null)
//        assertEquals("")
//        companyService.deleteCompany(createdCompany.id)
    }

    @Test
    fun testUpdateCompany(){
        val createdCompany = companyService.createCompany(CompanyServiceTestHelper.validCompanyReqDTO)
        val newUpdateRequest = CompanyServiceTestHelper.validCompanyReqDTO
        newUpdateRequest.name == "New Update"
        val updatedCompany = companyService.updateCompany(createdCompany.id, newUpdateRequest)
        assertNotEquals("Updated Company Name Not Equals with Old Name", createdCompany.name, updatedCompany.name)
    }
}