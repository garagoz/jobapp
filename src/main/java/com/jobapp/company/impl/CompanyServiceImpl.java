package com.jobapp.company.impl;

import com.jobapp.company.Company;
import com.jobapp.company.CompanyDTO;
import com.jobapp.company.CompanyRepo;
import com.jobapp.company.CompanyService;
import com.jobapp.shared.exceptions.ResourceNotFoundException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepo companyRepo;
    private final ModelMapper modelMapper;

    @Autowired
    public CompanyServiceImpl(CompanyRepo companyRepo, ModelMapper modelMapper){
        this.companyRepo = companyRepo;
        this.modelMapper = modelMapper;
    }


    @Override
    public List<CompanyDTO> findAllCompanies() {
        List<Company> allCompanies = companyRepo.findAll();
        return allCompanies.stream().map(
                (company -> this.modelMapper.map(company, CompanyDTO.class))
        ).toList();
    }

    @Override
    public CompanyDTO findCompanyById(String companyId) {
        Company company = companyRepo.findById(companyId).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Company with ID " + companyId + " not found")
        );

        return this.modelMapper.map(company, CompanyDTO.class);
    }

    @Override
    public CompanyDTO createCompany(CompanyDTO company) {

        company.setCompanyId(UUID.randomUUID().toString());
        return this.modelMapper.map(companyRepo.save(this.modelMapper.map(
                company, Company.class
        )), CompanyDTO.class);
    }

    @Override
    public CompanyDTO updateCompany(CompanyDTO company, String companyId) {

        Company company1 = companyRepo.findById(companyId).orElseThrow(
                () -> new ResourceNotFoundException("Company with ID " + companyId + " not found")
        );

        company1.setName(company.getName());
        company1.setDescription(company.getDescription());
        return this.modelMapper.map(company1, CompanyDTO.class);
    }

    @Override
    public void deleteCompany(String companyId) {
        Company company = companyRepo.findById(companyId).orElseThrow(
                () -> new ResourceNotFoundException("Company with ID " + companyId + " not found")
        );

        companyRepo.delete(company);
    }
}
