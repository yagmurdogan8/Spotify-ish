import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Main {
    private static Map<String, List<String>> users = new HashMap<>();
    public static void main(String[] args) {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        boolean exit = true;
        System.out.println("Instructions:");
        System.out.println("TO CREATE A NEW USER: Type 'C' then hit space bar and type name & hit enter");
        System.out.println("TO ADD A LIKED SONG: Type 'S' then hit space bar and type name hit space once " +
                "again and type the song name & hit enter");
        System.out.println("TO LIST THE LIKED SONGS: Type 'L' then hit space bar and type name");
        System.out.println("TO DELETE A LIKED SONG FROM THE LIST: Type 'E' then hit space bar and type name");
        System.out.println("TO DISPLAY ALL LIKED SONGS: Type 'M' then hit space bar & hit enter");
        System.out.println("TO DISPLAY ALL USERS: Type 'N' then hit space bar & hit enter");
        System.out.println("TO DISPLAY A RECOMMENDED SONGS LIST: Type 'R' then hit space bar and type name");
        System.out.println("Enter command");
        while (exit) {
            try {
                String input = reader.readLine().trim(); //reads the console input and trims excess whitespace
                String[] values = input.split("\\s", 3);
                switch (values[0].toUpperCase()) {
                    case "EXIT":
                        //we need a command to exit the loop and terminate the application
                        exit = false;
                        break;
                    case "C":
                        users.putIfAbsent(values[1], new LinkedList<>()); //user will be added to the users map, but only if he doesn't exist already
                        System.out.println("User successfully added");
                        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                        System.out.println("Type a new command");
                        break;

                    case "S":
                        users.putIfAbsent(values[1], new LinkedList<>()); //user will be added to the users map, but only if he doesn't exist already
                        users.compute(values[1], (k, v) -> { //we add the song to his liked songs, but only if it is not already present
                            if (!v.contains(values[2])) {
                                v.add(values[2]);
                            }
                            return v;
                        });
                        System.out.println("Song successfully added");
                        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                        System.out.println("Type a new command");
                        break;
                    case "E":
                        if (users.get(values[1]) != null) { //just a null pointer check if the player wouldn't exist yet
                            users.get(values[1]).remove(values[2]);
                        }
                        System.out.println("Song successfully deleted");
                        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                        System.out.println("Type a new command");
                        break;
                    case "L":
                        System.out.println("User " + values[1] + " likes these songs:");
                        if (users.get(values[1]) != null) { //just a null pointer check if the player wouldn't exist yet
                            users.get(values[1]).forEach(System.out::println);
                        }
                        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                        System.out.println("Type a new command");
                        break;
                    case "N":
                        System.out.println("Existing users are:");
                        users.keySet().forEach(System.out::println);
                        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                        System.out.println("Type a new command");
                        break;
                    case "M":
                        System.out.println("All liked songs: ");
                        users.values()
                                .stream()
                                .flatMap(songs -> songs.stream())
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))//we collect all the songs from all users and count them.
                                .entrySet()
                                .forEach(entry -> System.out.println(entry));
                        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                        System.out.println("Type a new command");
                        break;
                    case "R":
                        users.values()
                                .stream()
                                .flatMap(songs -> songs.stream())
                                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))//we collect all the songs from all users and count them.
                                .entrySet()
                                .stream()
                                .sorted(Map.Entry.<String, Long>comparingByValue().reversed()) //we sort the songs by the number of entries
                                .limit(3) //limit the output to 3 entries
                                .forEach(System.out::println);
                        System.out.println("-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+-+");
                        System.out.println("Type a new command");
                        break;
                    default:
                        System.out.println("Unsupported command " + values[0]);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}