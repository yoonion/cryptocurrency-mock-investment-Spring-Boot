package site.bitrun.cryptocurrency.global.api.upbit.dto;

import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import lombok.Getter;

@Getter
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class UpbitMarketDto {

    private String market;
    private String koreanName;
    private String englishName;

}
