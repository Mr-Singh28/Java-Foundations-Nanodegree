/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package udacityproject2;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Aurobind singh
 */
class Movie
    {
        
        String get() // gets the random movie from the list
        {
            List<String> arr = new ArrayList<String>();//this will store the names of the movies
        int a=0;
       File file = new File("names.txt");
        try {
            Scanner file_scanner = new Scanner(file);
            //add the names to the arr
            while(file_scanner.hasNextLine())
            {
                arr.add(a, file_scanner.nextLine());
                a +=1;
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        int size = arr.size();
        //get the random movie
        Random rand = new Random();
        int q = rand.nextInt(size-1);
        String name = arr.get(q);
        //return the name of the random movie
        return name;
        }
        boolean check(String name,String a)//check is the guess lies in the name or not
        {
            if(name.contains(a))
            {
            return true;}
            else return false;
        } 
        boolean already_in(String guess,String a)//checks if the user has already entered the character earlier
        {
            if(guess.contains(a)) return true;
            else return false;
        }
        String update(String name,String guess,String guessed_char)//updates the string and displays the user 
        {
            String updated_string="";
            for (int i = 0; i < name.length(); i++) {
                char p = name.charAt(i);
                char o = guessed_char.charAt(0);
                if(p==o)
                {
                    updated_string =updated_string + name.charAt(i);
                }
                else
                {
                   updated_string =updated_string + guess.charAt(i); 
                }
            }
            return updated_string;
        } 
        void play_the_game(String name,String guess)//game play is done here in this function
        {
            Scanner input = new Scanner(System.in);//object of scanner class 
            String guessed_char="",incorrect_guess="";//needed strings for the game play
            int num=10,flag=0;
            //loop checks, manipulates and tests the user input in the play
            while(num>0)
        {
            int w = name.compareTo(guess);
            if(w==0) break;
            System.out.println("You are guessing : "+guess);
            //System.out.println("");
            System.out.println("Guess a letter");
            //enter the guess
            do 
            {
                guessed_char = input.next();
                if(guessed_char.length()>1 || ((int)guessed_char.charAt(0) >=48 && (int)guessed_char.charAt(0) <=57))
                {
                    System.out.println("you have entered an incorrect input you need to enter only a single character");
                }
            }while(guessed_char.length()>1 || ((int)guessed_char.charAt(0) >=48 && (int)guessed_char.charAt(0) <=57));
            guessed_char =  guessed_char.toLowerCase();
         if(check(name,guessed_char) ^ already_in(guess, guessed_char))/*checks whether the user input is in the orignal string and 
             if the user has entered the same input again*/
        {
            System.out.println("you have guessed "+(10-num)+" wrong characters "+incorrect_guess);
            guess = update(name, guess, guessed_char);
        }
         else if(already_in(guess, guessed_char))//if th user repeats input it deducts one chance
         {
             num -=1;
             System.out.println("you have "+num+" tries left");
         }
        else//if the input is not in the original string the it deducts one chance
         {
             num -=1;
             System.out.println("you have "+num+" tries left");
             incorrect_guess =incorrect_guess + guessed_char +" "; 
         }
         //keeps check in case the user looses
         if(num>0)flag=0;
         else flag=1;
    }
            //gives the results
            if(flag==0)
     {System.out.println("YOU WON!!! "+ " The name of the movie is "+name.toUpperCase());}
     else System.out.println("you loose");
        }
  }
public class UdacityProject2 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
         String name,guess="";
        Movie m1 = new Movie();//object of the class
       name= m1.get();
       //manufacturing of the play string
        for (int i = 0; i <name.length(); i++) {
            if(name.charAt(i) == ' ')
                guess = guess + ' ';
            else
                guess = guess+'_';
        }
       m1.play_the_game(name, guess);    
    }
    
}
