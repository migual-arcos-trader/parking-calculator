INSERT INTO parkings (id, hourly_rate, discount_name)
VALUES ('P000000', 1.00, 'NO_DISCOUNT')
ON CONFLICT (id) DO NOTHING;

INSERT INTO parkings (id, hourly_rate, discount_name)
VALUES ('P000123', 2.00, 'MAX_DAILY_15')
ON CONFLICT (id) DO NOTHING;

INSERT INTO parkings (id, hourly_rate, discount_name)
VALUES ('P000456', 3.00, 'MAX_12H_20_HOURS_FREE')
ON CONFLICT (id) DO NOTHING;