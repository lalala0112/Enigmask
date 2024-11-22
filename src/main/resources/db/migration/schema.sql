CREATE TABLE chat_messages (
    id VARCHAR(255) PRIMARY KEY,
    sender_id VARCHAR(255) NOT NULL,
    recipient_id VARCHAR(255) NOT NULL,
    content TEXT NOT NULL,
    status VARCHAR(50),
    timestamp TIMESTAMP NOT NULL
);
