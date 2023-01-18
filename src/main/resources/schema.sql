create table if not exists routes (
    id INTEGER NOT NULL AUTO_INCREMENT,
    departure_city varchar(50) not null,
    arrival_city varchar(50) not null,
    departure_date DATE not null,
    departure_time TIME not null,
    cost DECIMAL not null,
    available_seats INTEGER not null,
    PRIMARY KEY (id)
);
create table if not exists tickets (
    id INTEGER NOT NULL AUTO_INCREMENT,
    first_name varchar(50) not null,
    last_name varchar(50) not null,
    patronymic varchar(50)not null,
    route_id INTEGER NOT NULL,
    payment_id INTEGER NOT NULL,
    PRIMARY KEY (id),
    FOREIGN KEY (route_id) REFERENCES routes(id)
);