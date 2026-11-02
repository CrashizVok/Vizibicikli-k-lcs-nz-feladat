import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Objects;
import java.util.Scanner;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static ArrayList<Kolcsonzes> kolcsonzesek = new ArrayList<>();
    static String filename = "f.txt";


    public static void main(String[] args) throws FileNotFoundException {
        //INIT
        read();
        System.out.print("5. feladat: ");
        Napi_kolcsonzesek();
        System.out.print("6. feladat: ");
        kerek_egynevet();

    }

    public static void read() throws FileNotFoundException {
        File file = new File(filename);
        Scanner scanner = new Scanner(file);

        scanner.nextLine();
        while (scanner.hasNextLine()){
            Kolcsonzes kolcs = new Kolcsonzes();

            String line = scanner.nextLine();
            String[] splitted = line.split(";");

            kolcs.setName(splitted[0]);
            kolcs.setId(splitted[1]);
            kolcs.setIn_hour(Integer.parseInt(splitted[2]));
            kolcs.setIn_minutes(Integer.parseInt(splitted[3]));
            kolcs.setOut_hour(Integer.parseInt(splitted[4]));
            kolcs.setOut_minutes(Integer.parseInt(splitted[5]));

            kolcsonzesek.add(kolcs);
            //System.out.println(splitted[0]);
        }
        System.out.println("-- Read finished -- ");
    }

    public static void Napi_kolcsonzesek(){
        System.out.println("Napikölcsönzések száma:  "+ kolcsonzesek.size());
    }

    public static void kerek_egynevet(){
        Scanner obj = new Scanner(System.in);

        System.out.print("Kérek egy nevet: ");
        String iNname = obj.nextLine();
        System.out.println(iNname + " kölcsönzései: ");

        kolcsonzesek.stream()
                .filter(name -> Objects.equals(name.getName(), iNname))
                .forEach(object -> {
                    String sol = object.getIn_hour() + ":"
                            + object.getIn_minutes() + "-"
                            + object.getOut_hour() + ":"
                            + object.getOut_minutes();

                    System.out.println(sol);
                });
    }
}



























