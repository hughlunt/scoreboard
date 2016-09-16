ALTER USER scoreboard_app WITH PASSWORD 'scoreboard';

BEGIN;
	CREATE TABLE competitor (
	       id    SERIAL PRIMARY KEY,
	       name  TEXT   NOT NULL
	);

	GRANT ALL PRIVILEGES ON TABLE competitor TO scoreboard_app;
	GRANT USAGE, SELECT ON SEQUENCE competitor_id_seq TO scoreboard_app;


	CREATE TABLE competition (
	       id    		  SERIAL    PRIMARY KEY,
	       competition_name   TEXT      NOT NULL,
	       date		  TIMESTAMP NOT NULL
	);
	GRANT ALL PRIVILEGES ON TABLE competition TO scoreboard_app;
	GRANT USAGE, SELECT ON SEQUENCE competition_id_seq TO scoreboard_app;


	CREATE TABLE points (
	       id             SERIAL PRIMARY KEY,
	       label	      TEXT   NOT NULL,
	       position       INT    NOT NULL,
	       points 	      INT    NOT NULL
	);
	GRANT ALL PRIVILEGES ON TABLE points TO scoreboard_app;
	GRANT USAGE, SELECT ON SEQUENCE points_id_seq TO scoreboard_app;


	CREATE TABLE event (
	       id         SERIAL    PRIMARY KEY,
	       event_name TEXT      NOT NULL,
	       points_id  INT       references points(id) NOT NULL,
	       date	  TIMESTAMP
	);
	GRANT ALL PRIVILEGES ON TABLE event TO scoreboard_app;
	GRANT USAGE, SELECT ON SEQUENCE event_id_seq TO scoreboard_app;


	CREATE TABLE result (
	       id             SERIAL PRIMARY KEY,
	       competition_id INT    references competition(id) NOT NULL,	       
	       event_id	      INT    references event(id) NOT NULL,
	       position       INT    NOT NULL
	);
	GRANT ALL PRIVILEGES ON TABLE result TO scoreboard_app;
	GRANT USAGE, SELECT ON SEQUENCE result_id_seq TO scoreboard_app;

COMMIT;
