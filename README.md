# Data Schema & Relationships

Below is the detailed **data schema** and their respective **relationships**. 
Tables: **players, games, matches, countries, locations, and player-boardGame outcomes**

---

## **Entities (Tables)**

### 1. **Countries**
Represents countries.

- **Attributes**:
    - `id` (UUID, Primary Key)
    - `name` (string)

---

### 2. **Locations**
Represents cities/locations linked to countries.

- **Attributes**:
    - `id` (UUID, Primary Key)
    - `city` (string)
    - `country_id` (UUID, Foreign Key referencing `Countries.id`)

---

### 3. **Players**
Represents players who may have different demographics and are assigned to specific locations.

- **Attributes**:
    - `id` (UUID, Primary Key)
    - `username` (string)
    - `first_name` (string)
    - `last_name` (string)
    - `birth_date` (date)
    - `gender` (string: Enum - 'MALE' or 'FEMALE')
    - `location_id` (UUID, Foreign Key referencing `Locations.id`)

---

### 4. **Games**
Represents games available in the system.

- **Attributes**:
    - `game_id` (UUID, Primary Key)
    - `game_name` (string)
    - `genre` (string: Enum - "STRATEGY", "CARD_GAME", "PUZZLE", etc.)
    - `difficulty_level` (string: Enum - "BEGINNER", "INTERMEDIATE", "ADVANCED")

---

### 5. **Matches**
Represents match data, linking players to games at specific times.

- **Attributes**:
    - `id` (UUID, Primary Key)
    - `game_id` (UUID, Foreign Key referencing `Games.game_id`)
    - `start_time` (datetime)
    - `end_time` (datetime)
    - `status` (string, Enum - e.g., "COMPLETED")

---

### 6. **PlayerMatches**
Represents player outcomes in matches. Indicates whether a player won or lost a specific match.

- **Attributes**:
    - `id` (UUID, Primary Key)
    - `player_id` (UUID, Foreign Key referencing `Players.id`)
    - `match_id` (UUID, Foreign Key referencing `Matches.id`)
    - `outcome` (string: Enum - "WIN", "LOSS")

---

## **Entity Relationships**

1. **Countries ↔ Locations**:
    - **Type**: One-to-Many
    - A single **Country** can have many **Locations**.
    - Relationship: `Locations.country_id` references `Countries.id`.

2. **Locations ↔ Players**:
    - **Type**: One-to-Many
    - A single **Location** can have many **Players** residing there.
    - Relationship: `Players.location_id` references `Locations.id`.

3. **Players ↔ PlayerMatches**:
    - **Type**: One-to-Many
    - A single **Player** can participate in many matches.
    - Relationship: `PlayerMatches.player_id` references `Players.id`.

4. **Games ↔ Matches**:
    - **Type**: One-to-Many
    - A single **Game** can have many **Matches**.
    - Relationship: `Matches.game_id` references `Games.game_id`.

5. **Matches ↔ PlayerMatches**:
    - **Type**: One-to-Many
    - A single **Match** can have multiple outcomes (one for each player involved).
    - Relationship: `PlayerMatches.match_id` references `Matches.id`.

---

## **Diagram Representation (Conceptual)**

```plaintext
Countries (1) ↔ (M) Locations
Locations (1) ↔ (M) Players
Players (1) ↔ (M) PlayerMatches
Games (1) ↔ (M) Matches
Matches (1) ↔ (M) PlayerMatches
```

## Adding a Game to the Platform

This guide will walk you through the steps required to add a new game to the platform and integrate the necessary backend components for its functionality.

---

### Step 1: Add the Game to the Platform

1. **Login**  
   Ensure you are logged in as either an **Administrator** or a **Game Developer**.

2. **Navigate to the Add Game Section**  
   Click on the **"Add Game"** button in the platform's admin interface.

3. **Fill in the Game Details**  
   Provide all the necessary information about the game, including its title, description, and any other required metadata.

4. **Submit the Game**  
   After filling in the details, click **"Submit"**.  
   Once submitted, the Administrator will review and finalize the game setup.

---

### Step 2: Implement Backend Game Development

For the game to function correctly on the platform, implement the following AMQP listeners and publishers.

#### AMQP Listener: `createNewMatch`

- **Purpose**: Listens for new match creation requests.
- **Queue**: `GAMENAME_QUEUE`
- **Parameter**: The listener receives a `StartGameCommand` object with the following structure:

  ```java
  StartGameCommand {
      UUID player1Id;
      UUID player2Id;
      UUID matchId;
  }
  ```
  
#### AMQP Publisher: 3 publishers

##### Match started fanout exhange

- **Purpose**: Publishes a message to the platform when a match is started.
- **Exchange**: `match_created_fanout_exchange`
- **Parameter**: The publisher sends a `Match` object with the following structure:

  ```java
  Match {
    private UUID id;
    private UUID player1Id;
    private UUID player2Id;
    private Map<UUID, Board> boards = new HashMap<>();
    private UUID currentPlayerId;
    private LocalDateTime startDateTime;
  }
  ```

##### Match ended fanout exhange

- **Purpose**: Publishes a message to the platform when a match has concluded.
- **Exchange**: `match_ended_fanout_exchange`
- **Parameter**: The publisher sends a `Match` object with the following structure:

  ```java
  Match {
    private UUID id;
    private UUID player1Id;
    private UUID player2Id;
    private Map<UUID, Board> boards = new HashMap<>();
    private UUID currentPlayerId;
    private LocalDateTime startDateTime;
  }
  ```

##### Player receives achievement 

- **Purpose**: Publishes a message to the platform when a user unlocks an achievement.
- **Queue**: `player_achievements_queue`
- **Parameter**: The publisher sends a `Match` object with the following structure:

  ```java
  playerAchievement {    
    private UUID playerId;
    private String name;
    private String description;
  }
  ```