package com.pp.cs.sales.catalog.dto;

import com.pp.cs.sales.catalog.common.enums.CountryCode;
import com.pp.cs.sales.catalog.common.enums.TeamCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
public class ProductReqDto {
    private CountryCode countryCode;

    private TeamCode teamCode;
}
