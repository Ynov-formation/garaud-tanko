package com.ynov.msoperation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder(setterPrefix = "with")
@Getter
public class TransferResponse {

	@JsonProperty("balance")
	private Float balance;

	@JsonProperty("sourceAccountId")
	private Long sourceAccountId;

	@JsonProperty("destinationAccountId")
	private Long destinationAccountId;
}
