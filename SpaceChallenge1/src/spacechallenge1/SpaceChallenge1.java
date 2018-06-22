package spacechallenge1;

import java.io.File;
import java.util.*;

class Item {

    private String name; //name of the item to be carried b the rocket
    private int weight; //weight of the item
    public int getWeight()
    {
        return weight;
    }
    public String getName()
    {
        return name;
    }

    public Item(String i, int w) {
        name = i;
        weight = w;
    }
}

interface Spaceship {

    boolean launch();/* a method that returns either true or false indicating if the launch 
                        was successful or if the rocket has crashed.*/
    boolean land();/*  a method that also returns either true or false based on the success of the landing.*/

    boolean canCarry(Item item);

    /*  a method that takes an Item as an argument and 
                                 returns true if the rocket can carry such item or false if it will exceed the weight limit.*/
    double carry(Item item);
    /* a method that also takes an Item object and updates the current weight of the rocket. */
}

class Rocket implements Spaceship {

    double max_weight;//in kgs
    double cost;//int dollars
    double rocket_weight;//in kgs
    double cargo_carried;//in kgs
    double cargo_limit;//kgs
    double current_weight;//in kgs
    double chance_crash;
    double chance_explosion;

    public boolean launch() {
        return true;
    }

    public boolean land() {
        return true;
    }

    public boolean canCarry(Item item) {
        if (this.max_weight >= (this.current_weight + item.getWeight())) {
            return true;
        } else {
            return false;
        }
    }

    public double carry(Item item) {
        this.current_weight += item.getWeight();
        return this.current_weight;
    }

}

class U1 extends Rocket {

    /*
    Rocket cost = $100 Million
    Rocket weight = 10 Tonnes
    Max weight (with cargo) = 18 Tonnes
    Chance of launch explosion = 5% * (cargo carried / cargo limit)
    Chance of landing crash = 1% * (cargo carried / cargo limit)
     */
    U1() {
        super.max_weight = 18000;//in kgs
        cost = 100000000;//int dollars
        rocket_weight = 10000;//in kgs
        cargo_carried = 0;//in kgs
        cargo_limit = max_weight - rocket_weight;//in kgs
        current_weight = 10000;//in kgs
        chance_crash = 0;
        chance_explosion = 0;
    }

    public boolean launch() {
        // Chance of launch explosion = 5% * (cargo carried / cargo limit)
        cargo_carried = current_weight - rocket_weight;
        cargo_limit = max_weight - rocket_weight;
        chance_crash = 0.05 * (cargo_carried / cargo_limit);
        double probability;
        Random rand = new Random();
        probability = rand.nextDouble();
        if (probability > chance_crash) {
            return false;
        } else {
            return true;
        }
    }

    public boolean land() {
        //Chance of landing crash = 1% * (cargo carried / cargo limit)
        if (launch()) {
            cargo_carried = current_weight - rocket_weight;
            cargo_limit = max_weight - rocket_weight;
            chance_explosion = 0.01 * (cargo_carried / cargo_limit);
            double probability;
            Random rand = new Random();
            probability = rand.nextDouble();
            if (probability > chance_explosion) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}

class U2 extends Rocket {

    /*
        Rocket cost = $120 Million
        Rocket weight = 18 Tonnes
        Max weight (with cargo) = 29 Tonnes
        Chance of launch explosion = 4% * (cargo carried / cargo limit)
        Chance of landing crash = 8% * (cargo carried / cargo limit)
     */
    U2() {
        max_weight = 29000;//in kgs
        cost = 120000000;//int dollars
        rocket_weight = 18000;//in kgs
        cargo_carried = 0;//in kgs
        cargo_limit = max_weight - rocket_weight;//kgs
        current_weight = 18000;//in kgs
        chance_crash = 0;
        chance_explosion = 0;
    }

    public boolean launch() {
        // Chance of launch explosion = 4% * (cargo carried / cargo limit)
        cargo_carried = current_weight - rocket_weight;
        cargo_limit = max_weight - rocket_weight;
        chance_crash = 0.04 * (cargo_carried / cargo_limit);
        double probability;
        Random rand = new Random();
        probability = rand.nextDouble();
        if (probability > chance_crash) {
            return false;
        } else {
            return true;
        }
    }

    public boolean land() {
        //Chance of landing crash = 8% * (cargo carried / cargo limit)
        if (launch()) {
            cargo_carried = current_weight - rocket_weight;
            cargo_limit = max_weight - rocket_weight;
            chance_explosion = 0.08 * (cargo_carried / cargo_limit);
            double probability;
            Random rand = new Random();
            probability = rand.nextDouble();
            if (probability > chance_explosion) {
                return false;
            } else {
                return true;
            }
        } else {
            return false;
        }
    }
}

/*
-----------------------------------------------------------------------------------------------------------------------

                                            PART -2
-----------------------------------------------------------------------------------------------------------------------
 */
class Simulation {

    ArrayList<Item> loadItems(String name_of_file) /*
            this method loads all items from a text file and returns an ArrayList of Items
     */ {
        //read the lines from the file
        ArrayList<Item> arr1 = new ArrayList<>();
        List<Item> arr = new ArrayList<>();//this will store the lies from the file
        File file = new File(name_of_file);
        try {
            Scanner file_scanner = new Scanner(file);
            //add the names to the arr
            while (file_scanner.hasNextLine()) {
                String ab = file_scanner.nextLine();
                String[] it1 = ab.split("=");
                arr1.add(new Item(it1[0], (int) Double.parseDouble(it1[1])));

            }
        } catch (Exception e) {
            System.out.println(e);
        }

        return arr1;

    }

    ArrayList<Rocket> loadU1(ArrayList<Item> arr1) //loads items in rocket type U1
    {
        ArrayList<Rocket> li = new ArrayList<>();
        int counter = 0;
        U1 u1 = new U1();
        li.add(u1);
        for (int i = 0; i < arr1.size(); i++) {
            boolean chk = li.get(counter).canCarry(arr1.get(i));
            if (chk) {
                li.get(counter).current_weight = li.get(counter).carry(arr1.get(i));

            } else {
                u1 = new U1();
                li.add(u1);
                counter += 1;
                li.get(counter).current_weight = li.get(counter).carry(arr1.get(i));
            }
        }
        return li;
    }

    ArrayList<Rocket> loadU2(ArrayList<Item> arr1) {
        ArrayList<Rocket> li = new ArrayList<>();
        int counter = 0;
        U2 u2 = new U2();
        li.add(u2);
        for (int i = 0; i < arr1.size(); i++) {
            boolean chk = li.get(counter).canCarry(arr1.get(i));
            if (chk) {
                li.get(counter).current_weight = li.get(counter).carry(arr1.get(i));
            } else {
                u2 = new U2();
                li.add(u2);
                counter += 1;
                li.get(counter).current_weight = li.get(counter).carry(arr1.get(i));
            }
        }
        return li;
    }

    long runSimulation(ArrayList<Rocket> li) {
        //runs test on the rocket type U2
        long cst = 0;
        for (int i = 0; i < li.size(); i++) {
            do {
                cst = cst + (long) li.get(i).cost;
            } while (!(li.get(i).launch() && li.get(i).land()));

        }
        return cst;
    }
}

/*
--------------------------------------------------------------------------------------------------------------
                                        PART- 3
-------------------------------------------------------------------------------------------------------------
 */
public class SpaceChallenge1 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        long cost;
        ArrayList<Item> arr1 = new ArrayList<Item>();
        Simulation try1 = new Simulation();
        //String[] files = {"phase1.txt","phase2.txt"};
        //  PHASE1
        arr1 = try1.loadItems("phase1.txt");
        cost = try1.runSimulation(try1.loadU1(arr1));
        System.out.println("Cost of setting up phase1 on mars using U1 rockets " + cost);
        cost = try1.runSimulation(try1.loadU2(arr1));
        System.out.println("Cost of setting up phase1 on mars using U2 rockets " + cost);
        //  PHASE2
        arr1 = try1.loadItems("phase2.txt");
        cost = try1.runSimulation(try1.loadU1(arr1));
        System.out.println("Cost of setting up phase2 on mars using U1 rockets " + cost);
        cost = try1.runSimulation(try1.loadU2(arr1));
        System.out.println("Cost of setting up phase2 on mars using U2 rockets " + cost);

    }

}
