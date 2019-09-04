	
SET time_zone='+00:00';
delete from movie;
delete from room;
delete from seat;
delete from movie_screening;
delete from seat_reservation;
delete from ticket_type;
delete from reservation;

insert into movie (title) values ('The Green Mile');
insert into movie (title) values ('The Shawshank Redemption');
insert into movie (title) values ('Forrest Gump');
insert into movie (title) values ('Requiem for a Dream');
insert into movie (title) values ('The Matrix');
insert into movie (title) values ('Zażółć gęślą jaźń');

insert into room (room_name) values ('ROOM 1');
insert into room (room_name) values ('ROOM 2');
insert into room (room_name) values ('ROOM 3');


insert into seat (seat_row, seat_number,fk_room_id) values (1,1,1);
insert into seat (seat_row, seat_number,fk_room_id) values (1,2,1);
insert into seat (seat_row, seat_number,fk_room_id) values (1,3,1);
insert into seat (seat_row, seat_number,fk_room_id) values (1,4,1);
insert into seat (seat_row, seat_number,fk_room_id) values (1,5,1);
insert into seat (seat_row, seat_number,fk_room_id) values (1,6,1);
insert into seat (seat_row, seat_number,fk_room_id) values (1,7,1);
insert into seat (seat_row, seat_number,fk_room_id) values (1,8,1);

insert into seat (seat_row, seat_number,fk_room_id) values (2,1,1);
insert into seat (seat_row, seat_number,fk_room_id) values (2,2,1);
insert into seat (seat_row, seat_number,fk_room_id) values (2,3,1);
insert into seat (seat_row, seat_number,fk_room_id) values (2,4,1);
insert into seat (seat_row, seat_number,fk_room_id) values (2,5,1);
insert into seat (seat_row, seat_number,fk_room_id) values (2,6,1);
insert into seat (seat_row, seat_number,fk_room_id) values (2,7,1);
insert into seat (seat_row, seat_number,fk_room_id) values (2,8,1);
insert into seat (seat_row, seat_number,fk_room_id) values (2,9,1);

insert into seat (seat_row, seat_number,fk_room_id) values (3,1,1);
insert into seat (seat_row, seat_number,fk_room_id) values (3,2,1);
insert into seat (seat_row, seat_number,fk_room_id) values (3,3,1);
insert into seat (seat_row, seat_number,fk_room_id) values (3,4,1);
insert into seat (seat_row, seat_number,fk_room_id) values (3,5,1);
insert into seat (seat_row, seat_number,fk_room_id) values (3,6,1);
insert into seat (seat_row, seat_number,fk_room_id) values (3,7,1);
insert into seat (seat_row, seat_number,fk_room_id) values (3,8,1);
insert into seat (seat_row, seat_number,fk_room_id) values (2,9,1);
insert into seat (seat_row, seat_number,fk_room_id) values (2,10,1);

insert into seat (seat_row, seat_number,fk_room_id) select seat_row, seat_number, 2 as fk_room_id from seat where fk_room_id = 1;
insert into seat (seat_row, seat_number,fk_room_id) select seat_row, seat_number, 3 as fk_room_id from seat where fk_room_id = 1;


insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T10:30:00',1,1);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T12:30:00',1,1);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T14:30:00',1,1);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T10:30:00',2,2);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T12:30:00',2,2);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T14:30:00',2,2);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T10:30:00',3,3);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T12:30:00',3,3);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T14:30:00',3,3);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T16:30:00',4,1);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T18:30:00',4,1);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T20:30:00',4,1);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T16:30:00',5,2);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T18:30:00',5,2);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T20:00:00',5,2);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T16:00:00',6,3);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T18:00:00',6,3);
insert into movie_screening (screening_time, fk_movie_id, fk_room_id) values('2019-12-04T20:00:00',6,3);

insert into ticket_type(ticket_type_name, price) values('adult', 25);
insert into ticket_type(ticket_type_name, price) values('student', 18);
insert into ticket_type(ticket_type_name, price) values('child', 12.5);

/*
insert into reservation(expiration_time, name, surname) values('2019-07-03T10:00:00', 'Michał','Sadowski');
insert into reservation(expiration_time, name, surname) values('2019-07-03T10:00:00', 'Jacek','Kozłowski');
insert into reservation(expiration_time, name, surname) values('2019-07-03T10:00:00', 'Daniel','Maszczyk');
insert into reservation(expiration_time, name, surname) values('2019-07-03T10:00:00', 'Rafał','Broniewski');
insert into reservation(expiration_time, name, surname) values('2019-07-03T10:00:00', 'Ewa','Sokołowska');
insert into reservation(expiration_time, name, surname) values('2019-07-03T10:00:00', 'Natalia','Dera');


insert into seat_reservation(fk_movie_screening_id, fk_seat_id, fk_reservation_id, fk_ticket_type_id) values(1,3,1,1);
insert into seat_reservation(fk_movie_screening_id, fk_seat_id, fk_reservation_id, fk_ticket_type_id) values(1,4,1,1);
insert into seat_reservation(fk_movie_screening_id, fk_seat_id, fk_reservation_id, fk_ticket_type_id) values(1,10,2,2);
insert into seat_reservation(fk_movie_screening_id, fk_seat_id, fk_reservation_id, fk_ticket_type_id) values(1,11,2,2);
insert into seat_reservation(fk_movie_screening_id, fk_seat_id, fk_reservation_id, fk_ticket_type_id) values(1,12,2,3);
insert into seat_reservation(fk_movie_screening_id, fk_seat_id, fk_reservation_id, fk_ticket_type_id) values(1,16,3,3);
*/




