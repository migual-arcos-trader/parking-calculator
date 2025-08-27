package com.paymeter.parking_calculator.domain.service.model.constants;

public final class IntegrationTestConstants {

    public static final String PATH = "/tickets/calculate";

    public static final String PARKING_ID_P_000123_FROM_2024_02_27_T_09_00_00 = """
            {
                "parkingId": "P000123",
                "from": "2024-02-27T09:00:00"
            }
            """;

    public static final String PARKING_ID_INVALID_FROM_2024_02_27_T_09_00_00 = """
            {
                "parkingId": "INVALID",
                "from": "2024-02-27T09:00:00"
            }
            """;

    public static final String FROM_2024_02_27_T_09_00_00 = """
            {
                "from": "2024-02-27T09:00:00"
            }
            """;

    public static final String PRICE = "price";

}
