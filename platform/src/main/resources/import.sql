INSERT INTO platform.players(player_id, username, last_name, first_name, age, gender, location ) VALUES ('fff2deb7-7bf2-43d5-8897-4ddd8edc6ab1', 'player1', 'player1', 'player1', 20, 'FEMALE', 'location1');
INSERT INTO platform.players(player_id, username, last_name, first_name, age, gender, location ) VALUES ('e12bb6fd-8e23-40fd-95cc-f318abf3c38c', 'player2', 'player2', 'player2', 20, 'FEMALE', 'location2');


INSERT INTO platform.games(game_id, name ) VALUES ('05c99486-9f64-49d4-bbb4-22e40d649ada', "Battleship");
INSERT INTO platform.games(game_id, name) VALUES ('08c31cbf-7a55-4156-923d-05f398337108', "Chess");
INSERT INTO platform.games(game_id, name) VALUES ('2e674330-7898-41b1-859c-b9b2b4808329', "Checkers");
INSERT INTO platform.games(game_id, name) VALUES ('a3e45088-45eb-4607-b48c-eb6294db4674', "Connect 4");
INSERT INTO platform.games(game_id, name) VALUES ('b7cc1c5e-06fb-470c-878e-2e9b4acaeabc', "Azul");
INSERT INTO platform.games(game_id, name) VALUES ('a2094c51-c8c3-4580-ada0-b7944887eac5', "Othello");


INSERT INTO platform.friendship (friendship_id, player_id, friend_id) VALUES ('a1c99486-9f64-49d4-bbb4-22e40d649ada', 'fff2deb7-7bf2-43d5-8897-4ddd8edc6ab1', 'e12bb6fd-8e23-40fd-95cc-f318abf3c38c');