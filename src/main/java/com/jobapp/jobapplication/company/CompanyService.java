package com.jobapp.jobapplication.company;

import java.util.List;

public interface CompanyService {

    List<CompanyDTO> findAllCompanies();
    CompanyDTO findCompanyById(String companyId);
    CompanyDTO createCompany(CompanyDTO company);
    CompanyDTO updateCompany(CompanyDTO company, String companyId);
    void deleteCompany(String companyId);
}
