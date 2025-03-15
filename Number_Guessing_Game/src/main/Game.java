package main;

import java.util.Random;
import java.util.Scanner;

public class Game {

	public static void main(String[] args) {
		Random random = new Random();
		int numberToGuess = random.nextInt(100) + 1;
		int attempts = 5;
		Scanner scanner = new Scanner(System.in);

//		System.out.println(numberToGuess);
		System.out.println("Welcome to the Number Guessing Game!");
		System.out.println("I have chosen a number between 1 and 100. Try to guess it!");
		System.out.println("You have " + attempts + " attempts.");

		for (int i = 0; i < attempts; i++) {
			System.out.print("Enter your guess: ");

			if (!scanner.hasNextInt()) {
				System.out.println("Invalid input. Please enter a number between 1 and 100.");
				scanner.next();
				continue;
			}

			int userGuess = scanner.nextInt();

			if (userGuess < 1 || userGuess > 100) {
				System.out.println("Please enter a number between 1 and 100.");
				continue;
			}

			if (userGuess == numberToGuess) {
				System.out.println("Congratulations! You guessed the correct number: " + numberToGuess);
				scanner.close();
				return;
			} 
			else if (userGuess < numberToGuess && (numberToGuess-userGuess)>10) {
				System.out.println("Too Low! Try again.");
			}

			else if (userGuess < numberToGuess && (numberToGuess-userGuess)<10) {
				System.out.println("Low! Try again.");
			} 
			else if (userGuess > numberToGuess && (userGuess-numberToGuess)>10) {
				System.out.println("Too High! Try again.");
			}
			else {
				System.out.println("High! Try again.");
			}

			System.out.println("Attempts left: " + (attempts - i - 1));
		}

		System.out.println("Sorry, you've run out of attempts. The correct number was: " + numberToGuess);
		scanner.close();
	}
}
