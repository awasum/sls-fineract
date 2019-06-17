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

package org.apache.fineract.infrastructure.campaigns.externalservice.data;

import org.joda.time.LocalDate;

import java.math.BigDecimal;

public class LoanReportData {

	private Long loanId;
	private Long officerId;
	private BigDecimal loanAmount;
	private LocalDate submittedOnDate;
	private LocalDate approvedOnDate;
	private LocalDate disbursedOnDate;
	private String clientDisplayName;
	private String clientFirstName;
	private Float annualInterestRate;
	private String officerFirstName;
	private String officerDisplayName;

	public Long getLoanId() {
		return loanId;
	}

	public void setLoanId(Long loanId) {
		this.loanId = loanId;
	}

	public Long getOfficerId() {
		return officerId;
	}

	public void setOfficerId(Long officerId) {
		this.officerId = officerId;
	}

	public BigDecimal getLoanAmount() {
		return loanAmount;
	}

	public void setLoanAmount(BigDecimal loanAmount) {
		this.loanAmount = loanAmount;
	}

	public LocalDate getSubmittedOnDate() {
		return submittedOnDate;
	}

	public void setSubmittedOnDate(LocalDate submittedOnDate) {
		this.submittedOnDate = submittedOnDate;
	}

	public LocalDate getApprovedOnDate() {
		return approvedOnDate;
	}

	public LocalDate getDisbursedOnDate() {
		return disbursedOnDate;
	}

	public void setDisbursedOnDate(LocalDate disbursedOnDate) {
		this.disbursedOnDate = disbursedOnDate;
	}

	public void setApprovedOnDate(LocalDate approvedOnDate) {
		this.approvedOnDate = approvedOnDate;
	}

	public String getClientDisplayName() {
		return clientDisplayName;
	}

	public void setClientDisplayName(String clientDisplayName) {
		this.clientDisplayName = clientDisplayName;
	}

	public String getClientFirstName() {
		return clientFirstName;
	}

	public void setClientFirstName(String clientFirstName) {
		this.clientFirstName = clientFirstName;
	}

	public Float getAnnualInterestRate() {
		return annualInterestRate;
	}

	public void setAnnualInterestRate(Float annualInterestRate) {
		this.annualInterestRate = annualInterestRate;
	}

	public String getOfficerFirstName() {
		return officerFirstName;
	}

	public void setOfficerFirstName(String officerFirstName) {
		this.officerFirstName = officerFirstName;
	}

	public String getOfficerDisplayName() {
		return officerDisplayName;
	}

	public void setOfficerDisplayName(String officerDisplayName) {
		this.officerDisplayName = officerDisplayName;
	}
}
