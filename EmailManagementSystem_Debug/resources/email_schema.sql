CREATE TABLE emails (
    id INT AUTO_INCREMENT PRIMARY KEY,
    sender VARCHAR(100),
    recipient VARCHAR(100),
    subject VARCHAR(150),
    body TEXT,
    timestamp DATETIME DEFAULT CURRENT_TIMESTAMP
)