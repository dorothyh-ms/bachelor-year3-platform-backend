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

