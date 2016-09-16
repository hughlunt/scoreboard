# --- !Ups

create table competitor (
       id   SERIAL PRIMARY KEY,
       name text not null
);

create table competition (
       id    SERIAL PRIMARY KEY,
       name  text not null,
       date  timestamp not null
);

create table points (
       id       SERIAL PRIMARY KEY,
       label    text not null,
       position int not null,
       points   int not null
);

create table event (
       id    	   SERIAL PRIMARY KEY,
       name        text NOT NULL,
       points_id   int references points(id) NOT NULL,
       FOREIGN KEY (points_id) REFERENCES points(id)
);

create table result (
       id    	      SERIAL PRIMARY KEY,
       competition_id int references competition(id) NOT NULL,
       competitor_id  int references competitor(id) NOT NULL,
       event_id       int references event(id) NOT NULL,
       position	      int NOT NULL,
       FOREIGN KEY (competition_id) REFERENCES competition(id),
       FOREIGN KEY (competitor_id) REFERENCES competitor(id),
       FOREIGN KEY (event_id) REFERENCES event(id)
);

# --- !Downs
DROP TABLE result;
DROP TABLE points;
DROP TABLE event;
DROP TABLE competitor;
DROP TABLE competition;
