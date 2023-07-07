package com.salsatechnology.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
public class FilterProductDTO {
    @NotBlank
    private String filterField;

    @NotNull
    private String filterValue;
}
