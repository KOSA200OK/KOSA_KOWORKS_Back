package com.my.stuff.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class StuffDTO {
	private String id;
    private String name;
    private Long stock;
}
