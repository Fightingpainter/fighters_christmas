package net.fightingpainter.christmas;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.nbt.StringNbtReader;
import net.minecraft.registry.Registries;
import net.minecraft.server.MinecraftServer;
import net.minecraft.util.Identifier;
import net.minecraft.util.WorldSavePath;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.nio.file.Path;
import java.nio.file.Files;

public class CalenderDataHandler {

    private static Path worldPath; //world directory path
    private static Path dayPath; //day directory path
    private static Path playerPath; //items per day file path
    
    public static void loadFiles(MinecraftServer server) {

        worldPath = server.getSavePath(WorldSavePath.ROOT); // Get the world directory
        dayPath = worldPath.resolve("data/"+Main.MOD_ID+"/days"); // Get the day directory
        playerPath = worldPath.resolve("data/"+Main.MOD_ID+"/players"); // Get the player directory

        //days
        for (int i = 1; i <= 24; i++) {
            File file = new File(dayPath+"/"+i+".txt");
            try {
                if (!file.exists()) {
                    file.getParentFile().mkdirs(); // Create directories if they don't exist
                    Files.write(file.toPath(), defaultItemsPerDay()); // Write the default content to the file
                }
            } catch (Exception e) { e.printStackTrace(); }
        }

        //players (since we don't have players yet just the folders because)
        File file = new File(playerPath+"/"+"thisDoesLitterallynotMatter.idk");
        try {

            file.getParentFile().mkdirs(); // Create directories if they don't exist

        } catch (Exception e) { e.printStackTrace(); }

    }

    public static int getDay() {
        //TODO: implement geting day logic later.
        
        //scuffed date setter so I can test the calender with different dates without having to restart everytime just to change the return value of this method
        String content = "";
        try {
            File file = worldPath.resolve("data/"+Main.MOD_ID+"/scuffedDateSetter.txt").toFile();
            if(!file.exists()){
                file.getParentFile().mkdirs(); // Create directories if they don't exist
                Files.write(file.toPath(), "1".getBytes()); // Write the default content to the file
            }

            content = Files.readString(file.toPath());
        } catch (Exception e) { e.printStackTrace(); }

        //parse content
        int result = Integer.parseInt(content);

        return result;
    }


    public static List<Integer> getMissedDays(String uuid) {
        
        List<Integer> missedDays = new ArrayList<>();

        //open file (get content)
        //Files.readString(dayPath.resolve(day+".txt"));

        String content = "";
        try {
            File file = playerPath.resolve(uuid+".txt").toFile();
            if(!file.exists()){
                file.getParentFile().mkdirs(); // Create directories if they don't exist
                Files.write(file.toPath(), defaultPlayerDays()); // Write the default content to the file
            }

            content = Files.readString(file.toPath());
        } catch (Exception e) { e.printStackTrace(); }

        //parse content
        String[] lines = content.split("\n");
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];
            String[] parts = line.split("-", 2); //split at the first dash
            int day = Integer.parseInt(parts[0]); //get the day
            boolean collected = Boolean.parseBoolean(parts[1]); //get the collected boolean
            
            if (day > getDay()) break; // break out of loop if day is in the future

            if(!collected) missedDays.add(day); // add day to missed days if it wasn't collected

        }

        return missedDays;
    }


    public static boolean getPlayerDay(String uuid, int day) {

        //open file (get content)
        //Files.readString(dayPath.resolve(day+".txt"));

        String content = "";
        try {
            File file = playerPath.resolve(uuid+".txt").toFile();
            if(!file.exists()){
                file.getParentFile().mkdirs(); // Create directories if they don't exist
                Files.write(file.toPath(), defaultPlayerDays()); // Write the default content to the file
            }

            content = Files.readString(file.toPath());
        } catch (Exception e) { e.printStackTrace(); }

        //parse content
        String[] lines = content.split("\n");
        boolean result = false;
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            String[] parts = line.split("-", 2);
            int dayNumber = Integer.parseInt(parts[0]);
            boolean collected = Boolean.parseBoolean(parts[1]);

            if(dayNumber == day){
                result = collected;
            }
        }

        return result;

    }

    public static void setPlayerDay(String uuid, int day, boolean collected) {

        //open file (get content)
        //Files.readString(dayPath.resolve(day+".txt"));

        String content = "";
        try {
            File file = playerPath.resolve(uuid+".txt").toFile();
            if(!file.exists()){
                file.getParentFile().mkdirs(); // Create directories if they don't exist
                Files.write(file.toPath(), defaultPlayerDays()); // Write the default content to the file
            }

            content = Files.readString(file.toPath());
        } catch (Exception e) { e.printStackTrace(); }

        //parse content
        String[] lines = content.split("\n");
        String newContent = "";
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            String[] parts = line.split("-", 2);
            int dayNumber = Integer.parseInt(parts[0]);
            boolean collectedOld = Boolean.parseBoolean(parts[1]);

            if(dayNumber == day){
                collectedOld = collected;
            }

            String newLine = dayNumber+"-"+collectedOld;
            newContent += newLine+"\n";
        }

        //write to file
        try {
            File file = playerPath.resolve(uuid+".txt").toFile();
            if(!file.exists()){
                file.getParentFile().mkdirs(); // Create directories if they don't exist
                Files.write(file.toPath(), defaultPlayerDays()); // Write the default content to the file
            }

            Files.write(file.toPath(), newContent.getBytes());
        } catch (Exception e) { e.printStackTrace(); }

    }


    public static void setItemsforDay(int day, List<ItemStack> items) {

        //open file (get content)
        //Files.readString(dayPath.resolve(day+".txt"));
        Main.LOGGER.info("setting items for day "+day+" to "+items.toString());

        String content = "";
        for (int i = 0; i < items.size(); i++) {
            ItemStack item = items.get(i);

            String ammount = String.valueOf(item.getCount());
            String itemId = item.getRegistryEntry().getKey().get().getValue().toString();
            String nbt = "";
            if(item.hasNbt()){
                nbt = ", "+item.getNbt().toString();
            }

            String line = ammount+", "+itemId +nbt;

            content += line+"\n";
        }

        //write to file
        try {
            Files.write(dayPath.resolve(day+".txt"), content.getBytes());
        } catch (Exception e) { e.printStackTrace(); }
    }

    public static List<ItemStack> getItemsforDay(int day) {

        //open file (get content)
        //Files.readString(dayPath.resolve(day+".txt"));
        Main.LOGGER.info("getting items for day "+day);

        String content = "";
        try {
            content = Files.readString(dayPath.resolve(day+".txt"));
        } catch (Exception e) { e.printStackTrace(); }

        //parse content
        String[] lines = content.split("\n");
        List<ItemStack> items = new ArrayList<>();
        for (int i = 0; i < lines.length; i++) {
            String line = lines[i];

            String[] parts = line.split(", ", 3);
            int ammount = Integer.parseInt(parts[0]);
            String mod = parts[1].split(":")[0];
            String itemName = parts[1].split(":")[1];
            

            String nbt = "";
            if(parts.length > 2){
                nbt = parts[2];
            }

            Item item = Registries.ITEM.get(new Identifier(mod, itemName));
            ItemStack itemStack = new ItemStack(item, ammount);

            if(!nbt.equals("")){
                try {
                    // Parse the NBT string
                    NbtCompound nbtData = StringNbtReader.parse(nbt);

                    // Apply the NBT data to the ItemStack
                    itemStack.setNbt(nbtData);

                } catch (Exception e) { e.printStackTrace(); }
            }

            items.add(itemStack);
        }

        return items;
    }

    //PRIVATE
    //===================================================================================================

    //default files
    private static final byte[] defaultItemsPerDay(){
        String defaultItem = "";

        for (int i = 0; i < 9; i++) {
            defaultItem += "64, minecraft:dirt\n";
        }
        defaultItem = defaultItem.substring(0, defaultItem.length() - 1);

        return defaultItem.getBytes();
    }

    private static final byte[] defaultPlayerDays(){
        String defaultItem = "";

        for (int i = 1; i <= 24; i++) {
            defaultItem += i+"-false\n";
        }
        defaultItem = defaultItem.substring(0, defaultItem.length() - 1);

        return defaultItem.getBytes();
    }

}

/*
    okay so since I'm going to use custom syntax or whatever I'll outline it here

    so first of for the folder structure in each world there is a folder called "data" that is already given by minecraft
    in there we have a folder called "fighters_christmas" (or whatever the mod id is) in there we have 2 folders "days" and "players"
    in the days folder we have 24 txt files for each day (1.txt, 2.txt, 3.txt, etc.) in there we specify the items for each day heres an example:

    ```
    <ammount>, <mod>:<item>, <nbt>
    <ammount>, <mod>:<item>, <nbt>
    <ammount>, <mod>:<item>, <nbt>
    ```
    so to explain each line defines one item first we say how many items, then we state what item and lastely we can specify nbt data (optional) 
    it's kinda like the give command (/give @p <mod>:<item><nbt> <ammount>)

    now for the players folder we have a txt file for each player which will be named after their uuid (e.g. 12345678-1234-1234-1234-123456789012.txt)
    in there we just have 24 lines of booleans and the day
    ```
    <day>-<boolean>
    <day>-<boolean>
    ...
    ```
    not much to say here...


*/