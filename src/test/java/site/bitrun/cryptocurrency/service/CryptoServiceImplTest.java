package site.bitrun.cryptocurrency.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import groovy.transform.ToString;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.junit.jupiter.api.Test;
import site.bitrun.cryptocurrency.dto.CryptoRankDto;

class CryptoServiceImplTest {

    @Test
    void jsonTest() throws JsonProcessingException {

        String jsonData = "{\n" +
                "\"id\": 1,\n" +
                "\"name\": \"Bitcoin\",\n" +
                "\"symbol\": \"BTC\",\n" +
                "\"slug\": \"bitcoin\",\n" +
                "\"num_market_pairs\": 10414,\n" +
                "\"date_added\": \"2010-07-13T00:00:00.000Z\",\n" +
                "\"max_supply\": 21000000,\n" +
                "\"circulating_supply\": 19468862,\n" +
                "\"total_supply\": 19468862,\n" +
                "\"infinite_supply\": false,\n" +
                "\"platform\": null,\n" +
                "\"cmc_rank\": 1,\n" +
                "\"self_reported_circulating_supply\": null,\n" +
                "\"self_reported_market_cap\": null,\n" +
                "\"tvl_ratio\": null,\n" +
                "\"last_updated\": \"2023-08-27T09:12:00.000Z\",\n" +
                "\"quote\": {\n" +
                "\"USD\": {\n" +
                "\"price\": 26044.34983394967,\n" +
                "\"volume_24h\": 5895289894.38051,\n" +
                "\"volume_change_24h\": -47.2243,\n" +
                "\"percent_change_1h\": 0.01623346,\n" +
                "\"percent_change_24h\": 0.13404781,\n" +
                "\"percent_change_7d\": -0.27272553,\n" +
                "\"percent_change_30d\": -10.60560749,\n" +
                "\"percent_change_60d\": -13.8945093,\n" +
                "\"percent_change_90d\": -6.63300539,\n" +
                "\"market_cap\": 507053852796.88904,\n" +
                "\"market_cap_dominance\": 48.2563,\n" +
                "\"fully_diluted_market_cap\": 546931346512.94,\n" +
                "\"tvl\": null,\n" +
                "\"last_updated\": \"2023-08-27T09:12:00.000Z\"\n" +
                "}\n" +
                "}\n" +
                "}";



        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // dto 필드로 선언한 데이터만 파싱
        objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true); // 대소문자를 구분하지 않음

        CryptoRankDtoTest cryptoRankDtos = objectMapper.readValue(jsonData, CryptoRankDtoTest.class);

        System.out.println(cryptoRankDtos.toString());

    }
    /**
     * TEST CLASS
     */
    @ToString
    @Getter
    @NoArgsConstructor
    public static class CryptoRankDtoTest {

        private String name;
        private String symbol;
        private InnerQuote quote;

        @ToString
        @Getter
        @NoArgsConstructor
        public static class InnerQuote {
            private InnerUsd usd;

            @ToString
            @Getter
            @NoArgsConstructor
            public static class InnerUsd {
                private Long price; // 1개당 가격

            }

        }
    }



/*

    @Test
    void jsonTest2() {
        String jsonData2 = "{\n" +
                "  \"name\": \"zooneon\",\n" +
                "  \"age\": 25,\n" +
                "  \"address\": \"seoul\",\n" +
                "  \"contact\": {\n" +
                "    \"phone_number\": \"0102222\",\n" +
                "    \"email\": \"foo@google.com\"\n" +
                "  },\n" +
                "  \"JOB\": {\n" +
                "    \"working\": true,\n" +
                "    \"workplace\": {\n" +
                "      \"name\": \"Sejong Univ.\",\n" +
                "      \"position\": \"student\"\n" +
                "    }\n" +
                "  }\n" +
                "}";



        try {
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
            objectMapper.configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true); // 대소문자를 구분하지 않음

            InnerClassPersonDto innerClassPersonDto = objectMapper.readValue(jsonData2, InnerClassPersonDto.class);
            System.out.println(innerClassPersonDto.getName());
            System.out.println(innerClassPersonDto.getContact().getPhoneNumber());
            System.out.println(innerClassPersonDto.getContact().getEmail());
            System.out.println(innerClassPersonDto.getJob().getWorkplace().getName());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }

    }

    @ToString
    @Getter
    @NoArgsConstructor
    public static class InnerClassPersonDto {

        private String name;
        private InnerContact contact;
        private InnerJob job;

        @Getter
        @NoArgsConstructor
        @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
        public static class InnerContact {

            private String phoneNumber;
            private String email;
        }

        @Getter
        @NoArgsConstructor
        public static class InnerJob {

            private boolean working;
            private InnerWorkplace workplace;

            @Getter
            @NoArgsConstructor
            public static class InnerWorkplace {

                private String name;
                private String position;
            }
        }
    }
*/


}
