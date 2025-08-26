CREATE TABLE IF NOT EXISTS parkings (
    id VARCHAR(20) PRIMARY KEY,
    hourly_rate NUMERIC(10, 2) NOT NULL,
    discount_name VARCHAR(50) NOT NULL
);

COMMENT ON COLUMN parkings.id IS 'Parking Id';
COMMENT ON COLUMN parkings.hourly_rate IS 'Hourly rate';
COMMENT ON COLUMN parkings.discount_name IS 'Name of the discount strategy to apply.';