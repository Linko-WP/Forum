DROP TABLE IF EXISTS threads_to_messages;
DROP TABLE IF EXISTS topics_to_threads;
DROP TABLE IF EXISTS messages_to_users;
DROP TABLE IF EXISTS users;
DROP TABLE IF EXISTS topics;
DROP TABLE IF EXISTS threads;
DROP TABLE IF EXISTS messages;

CREATE TABLE users(
	username VARCHAR(30) CHARACTER SET utf8 NOT NULL PRIMARY KEY,
	email VARCHAR(60) CHARACTER SET utf8,
	password VARCHAR(20) CHARACTER SET utf8,
       is_admin BOOLEAN 
);

CREATE TABLE topics(
	topic_id INT NOT NULL PRIMARY KEY,
	name VARCHAR(100) CHARACTER SET utf8
);

CREATE TABLE threads(
	thread_id INT NOT NULL PRIMARY KEY,
	name VARCHAR(100) CHARACTER SET utf8,
	messages_no INT
);

CREATE TABLE messages(
	message_id INT NOT NULL PRIMARY KEY,
	date DATETIME,
	content TEXT CHARACTER SET latin1 
);

-- Estas tablas relacionan las anteriores entre si
-- Hay que cambiar la clave primaria y hacer 
-- que sea la composicion de las claves externas

CREATE TABLE threads_to_messages(
	id INT PRIMARY KEY,
	thread_id INT REFERENCES threads(thread_id),
	message_id INT REFERENCES messages(message_id)
);

CREATE TABLE topics_to_threads(
	id INT PRIMARY KEY,
	topic_id INT REFERENCES topics(topic_id),
	thread_id INT REFERENCES threads(thread_id)
);

CREATE TABLE messages_to_users(
	id INT PRIMARY KEY,
	message_id INT REFERENCES messages(message_id),
	user_id INT REFERENCES users(user_id)
);