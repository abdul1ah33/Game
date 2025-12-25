package com.example.game;

import java.util.ArrayList;
import java.util.List;

public class Schema {

    public record ColumnDef(String name, String type, boolean isPrimary, boolean isAutoIncrement, String fkTable, String fkIdCol, String fkDisplayCol) {
        public ColumnDef(String name, String type, boolean isPrimary, boolean isAutoIncrement) {
            this(name, type, isPrimary, isAutoIncrement, null, null, null);
        }
    }

    public record TableDef(String tableName, String displayName, List<ColumnDef> columns) {}

    public static List<TableDef> getTables() {
        List<TableDef> tables = new ArrayList<>();


        List<ColumnDef> armors = new ArrayList<>();
        armors.add(new ColumnDef("ArmorID", "INT", true, true));
        armors.add(new ColumnDef("ArmorName", "VARCHAR", false, false));
        armors.add(new ColumnDef("ArmorDamageNegation", "INT", false, false));
        armors.add(new ColumnDef("ArmorRarity", "VARCHAR", false, false));
        armors.add(new ColumnDef("ArmorSpecialEffect", "VARCHAR", false, false));
        armors.add(new ColumnDef("Weight", "DECIMAL", false, false));
        tables.add(new TableDef("armors", "Armors", armors));


        List<ColumnDef> consumables = new ArrayList<>();
        consumables.add(new ColumnDef("ConsumablesID", "INT", true, true));
        consumables.add(new ColumnDef("ConsumableName", "VARCHAR", false, false));
        consumables.add(new ColumnDef("EffectDuration", "INT", false, false));
        consumables.add(new ColumnDef("Rarity", "VARCHAR", false, false));
        consumables.add(new ColumnDef("ConsumableEffect", "VARCHAR", false, false));
        tables.add(new TableDef("consumables", "Consumables", consumables));


        List<ColumnDef> enemies = new ArrayList<>();
        enemies.add(new ColumnDef("EnemyID", "INT", true, true));
        enemies.add(new ColumnDef("EnemyName", "VARCHAR", false, false));
        enemies.add(new ColumnDef("Health", "INT", false, false));
        enemies.add(new ColumnDef("Damage", "INT", false, false));
        enemies.add(new ColumnDef("EnemyType", "VARCHAR", false, false));
        enemies.add(new ColumnDef("Location", "VARCHAR", false, false));
        tables.add(new TableDef("enemies", "Enemies", enemies));


        List<ColumnDef> levels = new ArrayList<>();
        levels.add(new ColumnDef("LevelID", "INT", true, false));
        levels.add(new ColumnDef("Health", "INT", false, false));
        levels.add(new ColumnDef("Stamina", "INT", false, false));
        levels.add(new ColumnDef("MaxLoad", "INT", false, false));
        levels.add(new ColumnDef("BaseAttack", "INT", false, false));
        tables.add(new TableDef("levels", "Levels", levels));


        List<ColumnDef> npcs = new ArrayList<>();
        npcs.add(new ColumnDef("NPCID", "INT", true, true));
        npcs.add(new ColumnDef("NPCName", "VARCHAR", false, false));
        npcs.add(new ColumnDef("Location", "VARCHAR", false, false));
        npcs.add(new ColumnDef("DialogueLineNumber", "INT", false, false));
        tables.add(new TableDef("npcs", "NPCs", npcs));


        List<ColumnDef> players = new ArrayList<>();
        players.add(new ColumnDef("PlayerID", "INT", true, true));
        players.add(new ColumnDef("Name", "VARCHAR", false, false));
        players.add(new ColumnDef("ModelType", "VARCHAR", false, false));
        players.add(new ColumnDef("Voicetype", "VARCHAR", false, false));
        players.add(new ColumnDef("CurrentLevel", "INT", false, false));

        players.add(new ColumnDef("ArmorID", "INT", false, false, "armors", "ArmorID", "ArmorName"));
        tables.add(new TableDef("players", "Players", players));


        List<ColumnDef> quests = new ArrayList<>();
        quests.add(new ColumnDef("QuestID", "INT", true, true));
        quests.add(new ColumnDef("QuestName", "VARCHAR", false, false));
        quests.add(new ColumnDef("NPCID", "INT", false, false, "npcs", "NPCID", "NPCName"));
        quests.add(new ColumnDef("QuestDescription", "TEXT", false, false));
        tables.add(new TableDef("quests", "Quests", quests));


        List<ColumnDef> weapons = new ArrayList<>();
        weapons.add(new ColumnDef("WeaponID", "INT", true, true));
        weapons.add(new ColumnDef("WeaponName", "VARCHAR", false, false));
        weapons.add(new ColumnDef("WeaponType", "VARCHAR", false, false));
        weapons.add(new ColumnDef("Weight", "DECIMAL", false, false));
        weapons.add(new ColumnDef("Damage", "INT", false, false));
        weapons.add(new ColumnDef("WeaponEffect", "VARCHAR", false, false));
        tables.add(new TableDef("weapons", "Weapons", weapons));


        List<ColumnDef> pWeapons = new ArrayList<>();
        pWeapons.add(new ColumnDef("PlayerID", "INT", true, false, "players", "PlayerID", "Name"));
        pWeapons.add(new ColumnDef("WeaponID", "INT", true, false, "weapons", "WeaponID", "WeaponName"));
        tables.add(new TableDef("player_weapons", "Player Weapons", pWeapons));


        List<ColumnDef> pConsumables = new ArrayList<>();
        pConsumables.add(new ColumnDef("PlayerID", "INT", true, false, "players", "PlayerID", "Name"));
        pConsumables.add(new ColumnDef("ConsumablesID", "INT", true, false, "consumables", "ConsumablesID", "ConsumableName"));
        tables.add(new TableDef("player_consumables", "Player Consumables", pConsumables));


        List<ColumnDef> qEnemies = new ArrayList<>();
        qEnemies.add(new ColumnDef("QuestID", "INT", true, false, "quests", "QuestID", "QuestName"));
        qEnemies.add(new ColumnDef("EnemyID", "INT", true, false, "enemies", "EnemyID", "EnemyName"));
        tables.add(new TableDef("quests_enemies", "Quest Enemies", qEnemies));

        return tables;
    }
}
