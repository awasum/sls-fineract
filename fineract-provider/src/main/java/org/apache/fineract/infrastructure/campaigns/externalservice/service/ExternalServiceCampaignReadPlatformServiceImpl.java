/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package org.apache.fineract.infrastructure.campaigns.externalservice.service;

import org.apache.fineract.infrastructure.campaigns.email.domain.ExternalServiceCampaignApiKeyRepository;
import org.apache.fineract.infrastructure.campaigns.email.domain.ExternalServiceCampaignLogRepository;
import org.apache.fineract.infrastructure.campaigns.email.domain.ExternalServiceCampaignRepository;
import org.apache.fineract.infrastructure.campaigns.email.service.EmailCampaignReadPlatformService;
import org.apache.fineract.infrastructure.campaigns.externalservice.data.ExternalServiceCampaignApiKeyData;
import org.apache.fineract.infrastructure.campaigns.externalservice.data.ExternalServiceCampaignData;
import org.apache.fineract.infrastructure.campaigns.externalservice.data.ExternalServiceCampaignLogData;
import org.apache.fineract.infrastructure.campaigns.externalservice.domain.ExternalServiceCampaign;
import org.apache.fineract.infrastructure.campaigns.externalservice.domain.ExternalServiceCampaignLog;
import org.apache.fineract.infrastructure.core.service.Page;
import org.apache.fineract.infrastructure.core.service.SearchParameters;
import org.apache.fineract.portfolio.loanproduct.service.LoanProductReadPlatformService;
import org.apache.fineract.portfolio.savings.service.SavingsProductReadPlatformService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExternalServiceCampaignReadPlatformServiceImpl implements ExternalServiceCampaignReadPlatformService {

	private LoanProductReadPlatformService loanProductReadPlatformService;
	private SavingsProductReadPlatformService savingsProductReadPlatformService;
	private EmailCampaignReadPlatformService emailCampaignReadPlatformService;
	private ExternalServiceCampaignRepository externalServiceCampaignRepository;
	private ExternalServiceCampaignLogRepository externalServiceCampaignLogRepository;
	private ExternalServiceCampaignApiKeyRepository externalServiceCampaignApiKeyRepository;

	@Autowired
	public ExternalServiceCampaignReadPlatformServiceImpl(LoanProductReadPlatformService loanProductReadPlatformService,
														  SavingsProductReadPlatformService savingsProductReadPlatformService,
														  EmailCampaignReadPlatformService emailCampaignReadPlatformService,
														  ExternalServiceCampaignRepository externalServiceCampaignRepository,
														  ExternalServiceCampaignLogRepository externalServiceCampaignLogRepository,
														  ExternalServiceCampaignApiKeyRepository externalServiceCampaignApiKeyRepository) {
		this.loanProductReadPlatformService = loanProductReadPlatformService;
		this.savingsProductReadPlatformService = savingsProductReadPlatformService;
		this.emailCampaignReadPlatformService = emailCampaignReadPlatformService;
		this.externalServiceCampaignRepository = externalServiceCampaignRepository;
		this.externalServiceCampaignLogRepository = externalServiceCampaignLogRepository;
		this.externalServiceCampaignApiKeyRepository = externalServiceCampaignApiKeyRepository;
	}

	@Override
	public List<ExternalServiceCampaignData> retrieveAll() {
		List<ExternalServiceCampaign> campaigns = this.externalServiceCampaignRepository.findAll();
		return campaigns.stream().map(c -> new ExternalServiceCampaignData(c)).collect(Collectors.toList());
	}

	@Override
	public ExternalServiceCampaignData retrieveOne(Long id) {
		return new ExternalServiceCampaignData(this.externalServiceCampaignRepository.findOne(id));
	}

	@Override
	public ExternalServiceCampaignData retrieveTemplate() {
		ExternalServiceCampaignData campaign = new ExternalServiceCampaignData();
		this.appendTemplate(campaign);
		return campaign;
	}

	@Override
	public List<ExternalServiceCampaignApiKeyData> retrieveApiKeys() {
		return this.externalServiceCampaignApiKeyRepository.findAll().stream().map(c -> new ExternalServiceCampaignApiKeyData(c)).collect(Collectors.toList());
	}

	@Override
	public ExternalServiceCampaignApiKeyData retrieveApiKeyById(Long id) {
		return new ExternalServiceCampaignApiKeyData(this.externalServiceCampaignApiKeyRepository.findOne(id));
	}

	@Override
	public Page<ExternalServiceCampaignLogData> retrieveLogs(SearchParameters searchParameters) {
		Page<ExternalServiceCampaignLogData> pageItems;
		List<ExternalServiceCampaignLog> logs = this.externalServiceCampaignLogRepository.findAllLogs();
		int totalRecords = logs.size();
		if (searchParameters != null) {
			List<ExternalServiceCampaignLogData> filteredLogs = new ArrayList<>();
			if (searchParameters.getOffset() < totalRecords) {
				int limit = searchParameters.getLimit() + searchParameters.getOffset() <= totalRecords ? searchParameters.getLimit() + searchParameters.getOffset() : totalRecords;
				for (int i = searchParameters.getOffset(); i < limit; i++) {
					filteredLogs.add(new ExternalServiceCampaignLogData(logs.get(i)));
				}
			}
			pageItems = new Page<>(filteredLogs, totalRecords);
		} else {
			pageItems = new Page<>(logs.stream().map(log -> new ExternalServiceCampaignLogData(log)).collect(Collectors.toList()), totalRecords);
		}
		return pageItems;
	}

	private void appendTemplate(ExternalServiceCampaignData externalServiceCampaign) {
		externalServiceCampaign.setApiKeys(this.retrieveApiKeys());
		externalServiceCampaign.setSavingsProducts(this.savingsProductReadPlatformService.retrieveAll());
		externalServiceCampaign.setLoanProducts(this.loanProductReadPlatformService.retrieveAllLoanProducts());
		externalServiceCampaign.setBusinessRulesOptions(this.emailCampaignReadPlatformService.retrieveAllBySearchType("API"));
	}
}
