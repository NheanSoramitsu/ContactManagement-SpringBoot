package com.soramitsukhmer.contactmanagement.service

import com.soramitsukhmer.contactmanagement.api.request.RequestCompanyDTO

object CompanyServiceTestHelper {
    val validCompanyReqDTO = RequestCompanyDTO(
        name = "SoraTest",
        phone = "069232005",
        webUrl = "https://facebook.com"
    )
}