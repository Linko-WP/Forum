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
	topic_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) CHARACTER SET utf8
);

CREATE TABLE threads(
	thread_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	name VARCHAR(100) CHARACTER SET utf8,
	parent_topic_id INT NOT NULL,
	messages_no INT DEFAULT 0
);

CREATE TABLE messages(
	message_id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
	ts TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	content TEXT CHARACTER SET latin1,
	parent_thread_id INT NOT NULL,
	author_username VARCHAR(30) NOT NULL
);


INSERT INTO users values("ADMIN", "admin@gmail.com", "ad_pass", true);
INSERT INTO users values("Fran", "fran@gmail.com", "fran_pass", false);
INSERT INTO users values("Maria", "maria@gmail.com", "maria_pass", false);
INSERT INTO users values("Joham", "joham@gmail.com", "joham_pass", false);
INSERT INTO users values("Emma", "emma@gmail.com", "emma_pass", false);


INSERT INTO topics(name) values("University");
INSERT INTO topics(name) values("Movies");
INSERT INTO topics(name) values("Books");
INSERT INTO topics(name) values("Videogames");
INSERT INTO topics(name) values("Outdoors");
INSERT INTO topics(name) values("Politics");

INSERT INTO threads(name, parent_topic_id) values ("Courses to choose", 1);
INSERT INTO threads(name, parent_topic_id) values ("Erasmus", 1);
INSERT INTO threads(name, parent_topic_id) values ("Hunger games", 3);
INSERT INTO threads(name, parent_topic_id) values ("Life of Pi", 2);
INSERT INTO threads(name, parent_topic_id) values ("Batman", 2);

INSERT INTO messages(content, parent_thread_id, author_username) values ("You should choose TDDD24", 1, "Maria");
INSERT INTO messages(content, parent_thread_id, author_username) values ("Really? Why?", 1, "Joham");
INSERT INTO messages(content, parent_thread_id, author_username) values ("Great experience", 2, "Emma");
INSERT INTO messages(content, parent_thread_id, author_username) values ("Great Movie", 5, "Maria");
INSERT INTO messages(content, parent_thread_id, author_username) values ("Is not true! First one was the first", 5, "Fran");






