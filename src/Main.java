/* instructions: 
For this exercise create a text file full of names called names.txt. Use the following list
of names for data.

Write a program that reads a data file of names and inserts them into a list in
alphabetical order, based on name. Your program should have the following
functionality:
a. Ask user for the file name to read from (see the supplemental JAVA code
for reading name from a file at the end of this exercise).
b. Read in names from the file, names.txt
c. Names should be inserted in the appropriate place (alphabetically).
d. Do not presort the list
e. You are to create routines (methods) such as:
i. init()
ii. makeNode()
iii. insert()
iv. buildIndex()
97
v. etc.
f. Have menu options (just text using a console application) to do the
following:
i. Display the list.
ii. Request the length of the list
iii. The user should be able to delete a single name from the list.
iv. Request the length of a section of the list. (How many people have
a name that starts with “B”)
v. Also, your program should be able to print out sections of names,
for example it should be able to print all peoples names that start
with an “A”, “B”, etc.
1. To do so, you need to make an index that has links into
your list for each letter of the alphabet. If there are no
entries for a particular letter, your link should point to
NULL and if those names are requested, your program
should inform the user.
 */

import java.io.File;
import java.util.Scanner;
//hey professor, i really enjoyed this program

class linkedListStuff { //my linked list
    Node front;
    Node curr;
    Node tail;

    public linkedListStuff() { //init
        front = null;
        curr = null;
        tail = null;
    }

    class Node { //nodes, i put a capital and im too lazy to change them all to lowercase (ruined my aesthetic)
        String name;
        long data;
        Node next;

        public Node(String name, long data) {
            this.name = name;
            this.data = data;
            this.next = null;
        }
    }

    public void showList() { //simply print the list
        curr = front;
        while (curr != null) {
            System.out.println("name: " + curr.name);
            curr = curr.next;
        }
        System.out.println();
    }

    public void insertNode(String name, long data) { //where i put my nodes and how i sort them
        Node newNode = new Node(name, data);
        if (front == null || data < front.data || (data == front.data && name.compareTo(front.name) < 0)) {
            newNode.next = front;
            front = newNode;
            return;
        }
        Node curr = front;
        while (curr.next != null && (curr.next.data < data || (curr.next.data == data && curr.next.name.compareTo(name) < 0))) {
            curr = curr.next;
        }
        newNode.next = curr.next;
        curr.next = newNode;
    }

    public int getLength() { //simple incrementation for length
        int length = 0;
        curr = front;
        while (curr != null) {
            length++;
            curr = curr.next;
        }
        return length;
    }

    public void deleteName(String name) { //not gonna lie it kicked my ass but i got it done
        long finalNum = 0;
        int length = Math.min(name.length(), 3);
        for (int i = 0; i < length; i++) {
            char c = Character.toUpperCase(name.charAt(i));  // get the chars as uppercase
            long charValue = c - 'A' + 1;  // convert char to its alphabetical position, ascii
            long position = length - 1 - i;
            finalNum += charValue * (long) Math.pow(26, position);  // compute the finalNum
        }

        if (front == null) {
            System.out.println("list empty, nothing to delete");
            return;
        }

        if (front == null) {
            System.out.println("list empty");
            return;
        }

        if (front.data == finalNum) {
            front = front.next;
            System.out.println("deleted: " + name);
            return;
        }

        Node curr = front;
        while (curr.next != null && curr.next.data != finalNum) { //i started trying to delete by the this.name instead of this.data
            curr = curr.next;

        }

        if (curr.next == null) {
            System.out.println("name: " + name + " not found");
            System.out.println();
            return;
        }

        curr.next = curr.next.next;
        System.out.println("deleted: " + name);
        System.out.println();
    }

    public int countNames(char letter) {
        int count = 0;
        curr = front;
        while (curr != null) {
            if (curr.name.toUpperCase().charAt(0) == Character.toUpperCase(letter)) {
                count++;
            }
            curr = curr.next;
        }
        return count;
    }


    public void showNames(char letter) {
        curr = front;
        boolean found = false;
        while (curr != null) {
            if (curr.name.toUpperCase().charAt(0) == Character.toUpperCase(letter)) {
                System.out.println("name: " + curr.name);
                found = true;
            }
            curr = curr.next;
        }
        if (!found) {
            System.out.println("No names found starting with " + letter);
        }
    }
}
//make index, order list, tie names and numbers together, done


    public class Main {
        public static void main(String[] args) {
            linkedListStuff list = new linkedListStuff();
            //create list off the bat so i really didn't need if (front == null) in all my functions but i'm not changing it, it works

            try {
                Scanner fileScanner = new Scanner(new File("C:\\Users\\Evan\\IdeaProjects\\ProgramFive\\src\\names.txt"));
                //intellij wouldn't work with me even though the file was in the same directory so i put the absoulte location

                while (fileScanner.hasNext()) {
                    String word = fileScanner.next();
                    long finalNum = 0;

                    //this is where it gets fun
                    int length = Math.min(word.length(), 3); //i tried to do all letters in the string but the long words would get sent to the shadow realm
                    for (int i = 0; i < length; i++) {
                        char c = Character.toUpperCase(word.charAt(i)); //getting the chars then use their ascii value for numbers
                        long charValue = c - 'A' + 1; //so c (curr char) to ascii number, number minus A to get the letter chart + 1 cause *0 was messing it up
                        long position = length - 1 - i; //cont. idk why * 0 was messing up but it did, getting the length of the strings to multiply
                        finalNum += charValue * (long) Math.pow(26, position); //getting the 'final' number, i used longs just in cause of overflow
                    }
                    list.insertNode(word, finalNum);
                    //scanner to string; string to numbers; numbers to node; organize nodes //<< early comment in the code (i usually delete them as i go)
                }
                fileScanner.close();
            } catch (Exception e) { //file error just in case
                System.out.println("error: file not found" + e.getMessage());
                return;
            }


            Scanner inputScanner = new Scanner(System.in);

            while (true) { //start of my user interactions
                System.out.println("what would you like to do?");
                System.out.println("(display, length, delete, index, or exit)"); //'\n'
                String answer = inputScanner.nextLine();


                if (answer.equals("exit")) { //simple
                    System.out.println("goodbye :) ");
                    System.exit(0);
                }
                if (!answer.equals("display") && !answer.equals("delete") && !answer.equals("specific") &&
                        !answer.equals("length") && !answer.equals("index")) {
                    //if the user types something not applicable then they get this error message (even though they already got it)

                    System.out.println("thats not a valid answer");
                    System.out.println("answers: display, length, delete, index, or exit");
                }

                switch (answer) { //mainly calling related functions

                    case "display":
                        System.out.println("whole list"); //works make sure it prints out in order
                        list.showList();
                        break;

                    case "length": //easy
                        System.out.println("list length: " + list.getLength());
                        System.out.println();
                        break;

                    case "delete": //not easy but works
                        System.out.println("enter name to delete: ");
                        String nameToDelete = inputScanner.nextLine();
                        list.deleteName(nameToDelete); //didn't delete ?? - fixed (pre finished code)
                        break;

                    case "index":
                        System.out.println("choose a number:");
                        System.out.println("1. print the number of names starting with a letter");
                        System.out.println("2. print the names starting with a letter ");
                        int option = inputScanner.nextInt();
                        inputScanner.nextLine(); // Clear the buffer

                        System.out.println("what letter: ");
                        char letter = inputScanner.nextLine().toUpperCase().charAt(0); //first char to capital

                        if (option == 1) {
                            int count = list.countNames(letter);
                            System.out.println("there are " + count + " names starting with the letter " + letter);
                        } else if (option == 2) {
                            list.showNames(letter); //names
                        } else {
                            System.out.println("Invalid option selected");
                        }
                        break;


                }
            }
        }
    }
