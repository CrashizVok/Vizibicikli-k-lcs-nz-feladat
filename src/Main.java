import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.*;


//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    static ArrayList<Kolcsonzes> kolcsonzesek = new ArrayList<>();
    static String filename = "f.txt";


    public static void main(String[] args) throws IOException {
        //INIT
        read();
        System.out.print("5. feladat: ");
        Napi_kolcsonzesek();
        System.out.print("6. feladat: ");
        kerek_egynevet();
        System.out.print("7. feladat: ");
        adjonmegegyidopontotorapercarakban();
        System.out.print("8. feladat: ");
        anapibevetel();
        elkovetok();
        System.out.println("10. feladat: Statisztika");
        statisztika();
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
        System.out.println("        " + iNname + " kölcsönzései: ");

        if (kolcsonzesek.stream()
                .noneMatch(name -> Objects.equals(name.getName(), iNname))) {
            System.out.println("        Nem volt ilyen nevű kölcsönző");

        } else {
            kolcsonzesek.stream()
                    .filter(name -> Objects.equals(name.getName(), iNname))
                    .forEach(object -> {
                        String sol = object.getIn_hour() + ":"
                                + object.getIn_minutes() + "-"
                                + object.getOut_hour() + ":"
                                + object.getOut_minutes();

                        System.out.println("        " + sol);
                    });
        }
    }

    public static void adjonmegegyidopontotorapercarakban(){
        Scanner obj = new Scanner(System.in);

        System.out.print("Adjon meg egy időpontot óra:perc alakban: ");
        String time = obj.nextLine();


        String[] splittedTime = time.split(":");
        int hour = Integer.parseInt(splittedTime[0]);
        int minutes = Integer.parseInt(splittedTime[1]);
        if (minutes > 60) {
            System.out.println("        Nem ismered az órát?");
            return;
        }
        if (minutes < 0) {
            System.out.println("        Nem ismered az órát?");
            return;
        }
        if (hour < 0) {
            System.out.println("        Nem ismered az órát?");
            return;
        }

        int finalTime = Integer.parseInt(numberHelper(hour) + numberHelper(minutes));

        System.out.println("        A vizen lévő járművek: ");
        for (Kolcsonzes kolcsonz : kolcsonzesek){
            int InKhour = kolcsonz.getIn_hour();
            int InKminutes = kolcsonz.getIn_minutes();
            int InKfinalTime = Integer.parseInt(InKhour + numberHelper(InKminutes));

            int OutKhour = kolcsonz.getOut_hour();
            int OutKminutes = kolcsonz.getOut_minutes();
            int OutKfinalTime = Integer.parseInt(OutKhour + numberHelper(OutKminutes));


            if (finalTime >= InKfinalTime && finalTime <= OutKfinalTime){
                System.out.println("        " + numberHelper(kolcsonz.getIn_hour()) + ":" + numberHelper(kolcsonz.getIn_minutes()) + "-" + numberHelper(kolcsonz.getOut_hour()) + ":" + numberHelper(kolcsonz.getOut_minutes()) + " : " + kolcsonz.getName());
            }
        }
    }

    public static void anapibevetel(){
        int price = 2400;
        int sum = 0;
        for (Kolcsonzes kolcsonz : kolcsonzesek){
            int minutes = (kolcsonz.getOut_hour() * 60 + kolcsonz.getOut_minutes())
                    - (kolcsonz.getIn_hour() * 60 + kolcsonz.getIn_minutes());

            sum += (int) Math.ceil(minutes / 30.0) * price;
        }
        System.out.println("A napi bevétel: " + sum + " Ft");
    }

    public static void elkovetok() throws IOException {
        PrintWriter writer = new PrintWriter("F.txt", StandardCharsets.UTF_8);
        for (Kolcsonzes kolcsonz : kolcsonzesek) {
            if (kolcsonz.getId().equals("F")) {
                writer.println(numberHelper(kolcsonz.getOut_hour()) + ":" + numberHelper(kolcsonz.getOut_minutes())
                        + "-" + numberHelper(kolcsonz.getIn_hour()) + ":" + numberHelper(kolcsonz.getIn_minutes())
                        + " : " + kolcsonz.getName());
            }
        }
        writer.close();
    }

    public static void statisztika() {
        ArrayList<String> idk = new ArrayList<>();
        for (Kolcsonzes kolcsonz : kolcsonzesek) {
            if (!idk.contains(kolcsonz.getId())) {
                idk.add(kolcsonz.getId());
            }
        }
        Collections.sort(idk);

        for (String id : idk) {
            int db = 0;
            for (Kolcsonzes kolcsonz : kolcsonzesek) {
                if (kolcsonz.getId().equals(id)) {
                    db++;
                }
            }
            System.out.println("        " + id + " - " + db);
        }
    }

    public static String numberHelper(int number){
        if (number < 10) {
            return "0" + number;
        }
        else{
            return number + "";
        }
    }
}



























