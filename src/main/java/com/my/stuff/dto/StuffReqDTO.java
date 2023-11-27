package com.my.stuff.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter @Getter
@NoArgsConstructor @AllArgsConstructor
public class StuffReqDTO {
	private int stuffId;
	private int reqId;
	private int reqQuantity;
}
