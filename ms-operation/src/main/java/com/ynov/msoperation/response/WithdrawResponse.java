package com.ynov.msoperation.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Builder(setterPrefix = "with")
@Getter
public class WithdrawResponse {
	@JsonProperty("amount")
	private Float amount;

	@JsonProperty("accountId")
	private Long accountId;
}
