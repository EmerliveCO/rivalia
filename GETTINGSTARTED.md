# GETTING STARTED

Rivalia is a project where we can create and manage table tennis tournaments. We can also view player statistics, such
as tournaments played, the number of tournaments won, and other statistics related to table tennis.

## Development Environment Installation

### Requirements

- Postgres
- [PgAdmin](https://www.pgadmin.org/download/)
- [Podman](https://podman.io/)

### Postgres Installation

Once you install [Podman](https://podman.io/) and [PgAdmin](https://www.pgadmin.org/download/) you can create a 
PostgreSQL container. (you can also use [Docker](https://www.docker.com/) instead of [Podman](https://podman.io/)

Primero debemos hacer pull a la imagen de Postgres
First, we need to pull the postgres image
```bash
podman pull postgres:latest
```

Once the postgres image is downloaded, we'll create a volume to persist the data
```bash
podman volume create pgdata
```

and then we create the container, you can use your own username and password variables
```bash
podman run --name rivalia-postgres -e POSTGRES_USER=emerlive -e POSTGRES_PASSWORD=37a71925 -e POSTGRES_DB=tournament -p 5432:5432 -v pgdata:/var/lib/postgresql/data -d postgres
```

We already check if the container was created with

```bash
podman ps
```

### Create DataBase Models

Create the database if it is not created when the container is initialized
```bash
CREATE DATABASE tournament;

```
Create Users Table
```bash
CREATE TABLE users (
    id SERIAL PRIMARY KEY,
    id_auth VARCHAR(24) NOT NULL,
    name VARCHAR(100) NOT NULL,
    last_name VARCHAR(100) NOT NULL,
    time_stamp DATE NOT NULL,
    created_at DATE NOT NULL,
    status VARCHAR(20) DEFAULT 'Active',
    city VARCHAR(100),
    province VARCHAR(100),
    country VARCHAR(100),
    club_name VARCHAR(100),
    league VARCHAR(100),
    born_date DATE NOT NULL,
    document_type VARCHAR(10) NOT NULL,
    document_number VARCHAR(20) NOT NULL,
    address VARCHAR(100),
    email VARCHAR(100) NOT NULL,
    gender VARCHAR(20) NOT NULL,
    isDeleted BOOLEAN DEFAULT FALSE
);
```
Create users UNIQUES INDEX
```bash
CREATE UNIQUE INDEX ux_users_email_not_deleted 
ON users(email) 
WHERE isDeleted = false;

CREATE UNIQUE INDEX ux_users_document_type_not_deleted 
ON users(document_type) 
WHERE isDeleted = false;

CREATE UNIQUE INDEX ux_users_document_not_deleted 
ON users(document_number) 
WHERE isDeleted = false;

CREATE UNIQUE INDEX ux_users_document_document_type_email_not_deleted 
ON users(document_number, document_type, email) 
WHERE isDeleted = false;
```

Create Sports Table
```bash
CREATE TABLE sport (
    id SERIAL PRIMARY KEY,
    name VARCHAR(50) NOT NULL,
    description TEXT NOT NULL
);
```

Create Money Information Table
```bash
CREATE TABLE money_information (
    id SERIAL PRIMARY KEY,
    tournament_id INT NOT NULL,
    number_of_players INT DEFAULT 0,
    expected_money INT DEFAULT 0,
    actual_money INT DEFAULT 0
);
```

Create Tournament Table
```bash
CREATE TABLE tournament (
    id SERIAL PRIMARY KEY,
    tournament_name VARCHAR(100) NOT NULL,
    owner_id INT NOT NULL,
    description TEXT NOT NULL,
    time_stamp DATE NOT NULL,
    created_at DATE NOT NULL,
    tournament_day DATE NOT NULL,
    tournament_hour TIME NOT NULL,
    status VARCHAR(20) NOT NULL,
    inscriptions VARCHAR(20) NOT NULL,
    price INT NOT NULL,
    type_tournament VARCHAR(20) NOT NULL,
    is_national BOOLEAN DEFAULT FALSE,
    contact_number_indicative VARCHAR(10) NOT NULL,
    contact_number VARCHAR(20) NOT NULL,
    require_password BOOLEAN DEFAULT FALSE,
    password VARCHAR(10) NOT NULL,
    sport_id INT,
    money_information_id INT,
    tournament_country VARCHAR(100),
    
    CONSTRAINT fk_tournament_owner FOREIGN KEY (owner_id) REFERENCES users(id),
    CONSTRAINT fk_tournament_sport FOREIGN KEY (sport_id) REFERENCES sport(id),
    CONSTRAINT fk_tournament_money FOREIGN KEY (money_information_id) REFERENCES money_information(id)
);
```

Create Payment Method Table
```bash
CREATE TABLE payment_method (
    id SERIAL PRIMARY KEY,
    tournament_id INT NOT NULL,
    bank_name VARCHAR(30) NOT NULL,
    account_number VARCHAR(20) NOT NULL,
    document_type VARCHAR(10),
    document_number VARCHAR(20),
    
    CONSTRAINT fk_tournament_id FOREIGN KEY (tournament_id) REFERENCES tournament(id)
);
```