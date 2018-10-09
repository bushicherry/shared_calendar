package distSys.networking.udp;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println(args[0]);

        Scanner hostsInput;
        int numHosts=0;
        HashMap<String,Integer> hostsPorts = new HashMap<>();

        // attempts to create scanner for 'knownhosts_udp.txt'
        try
        {
            hostsInput = new Scanner(new File("knownhosts_udp.txt"));
        }
        catch(FileNotFoundException e)
        {
            System.out.println("File 'knownhosts_udp.txt' doesn't exist.");
            return;
        }

        // reads the file line by line
        while(hostsInput.hasNextLine()) {
            numHosts = numHosts+1;

            hostsPorts.put(hostsInput.next(),Integer.parseInt(hostsInput.next()));
        }

        // test output the map
        for (HashMap.Entry<String,Integer> entry : hostsPorts.entrySet()) {
            System.out.println(entry.getKey() + " " + entry.getValue() + "\n");
        }
    }
}
