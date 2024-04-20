CREATE TABLE  IF NOT EXISTS users (
        username VARCHAR(50) PRIMARY KEY,
        password VARCHAR NOT NULL,
        role VARCHAR NOT NULL
);

CREATE TABLE IF NOT EXISTS urls (
        id INT PRIMARY KEY,
        short_url VARCHAR(2048) NOT NULL,
        long_url VARCHAR(2048) NOT NULL,
        score INT NOT NULL,
        is_active BOOLEAN NOT NULL,
        username VARCHAR(50) NOT NULL
);