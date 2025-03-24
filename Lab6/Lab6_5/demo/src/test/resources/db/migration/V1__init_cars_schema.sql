CREATE TABLE IF NOT EXISTS car (
    id BIGSERIAL PRIMARY KEY,
    maker VARCHAR(255) NOT NULL,
    model VARCHAR(255) NOT NULL
);

INSERT INTO car (maker, model) VALUES 
    ('Tesla', 'Model S'),
    ('Toyota', 'Yaris'),
    ('Porsche', 'Taycan');
