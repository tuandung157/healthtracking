# healthtracking

To comipe the project simply use
`mvn package`
or devote building responsibilities to maven in favourite IDE

HealthTrackingBackEnd uses PostgreSQL DBMS as database server and Mosquito as MQTT broker

###The server uses PostgreSQL database, so a few things need to be done before it can start:
####On Windows:
#####Database setup:
Create a database cluster with the following command:

`pg_ctl -D "database directory" initdb`

Start the database cluster:

`pg_ctl start -D "database directory"`

Connect to the database as default postgres user:

`psql postgres`

Create a user for the server:

`create user spring password 'oracle1';`

Grant the created user access to new tables and sequences automatically:
```
alter default privileges in schema public
grant all on tables to spring;
alter default privileges in schema public
grant all on sequences to spring;
```

Next we need to create a few tables and sequences in a database:
```
create table device(
	device_id bigint primary key,
	client_name text UNIQUE NOT NULL,
	ip text,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp 
);

create sequence device_seq
	increment by 1
	start with 1;

create table telemetry(
	telemetry_id bigint primary key,
	device_id bigint references device on delete cascade,
	temperature bigint,
	oxygen bigint,
	heartRate bigint,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp 
);

create sequence telemetry_seq
	increment by 1
	start with 1;

create table user_account(
	user_id bigint primary key,
	usersname text not null,
	password text not null,
	created_at timestamp default current_timestamp,
	updated_at timestamp default current_timestamp 
);

create sequence user_seq
	increment by 1
	start with 1;
```

if we want to remove all the tables and sequences we can do the following:
```
drop table if exists user;
drop table if exists telemetry;
drop table if exists device;

drop sequence id exists user_seq
drop sequence id exists telemetry_seq;
drop sequence id exists device_seq;
```

#####MQTT broker setup:
######Mosquitto configuration(create a file and put the lines there):
```
per_listener_settings true
# (optional) allows us to establish remote connectoins without a password
# use false to use require authenticatoin
allow_anonymous true
# allows us to set passwords (must be present if we want passwordless remote connections)
password_file passwds.txt
```

######To create passwds.txt:
Create a file and put the following line in there: 

`spring:oracle1`

add other username password pairs as desireble

run `mosquitto_passwd -U passwds.txt`

start Mosquitto with `mosquitto -c "path to configuration file"`
also use `-v` key for verbose output


