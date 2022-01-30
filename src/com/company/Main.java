package com.company;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter char array:");
        String wordToArray = scanner.next().toUpperCase();

        System.out.println("Enter word to build:");
        String wordToBuild = scanner.next().toUpperCase();

        int rowColumn = (int) Math.ceil(Math.sqrt(wordToArray.length()));
        char[][] matrix = new char[rowColumn][rowColumn];
        String resultString = new String();

        stringToDoubleArray(wordToArray, matrix, rowColumn); // method to convert word to double array

        resultString += foundFirstChar( wordToBuild, matrix, rowColumn); // result string construct from foundFirstChar method

        System.out.println("Matrix: ");
        for (int i = 0; i < rowColumn; i++) {
            for (int j = 0; j < rowColumn; j++) {
                System.out.print(matrix[i][j]);
            }
            System.out.println();
        }

        System.out.println("Find word: " + wordToBuild);
        System.out.println("Result: " + resultString.substring(0, resultString.length() - 2));

    }

    public static char[][] stringToDoubleArray(String word, char[][] doubleArr, int row) {
        int index = 0;
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                if (index < word.length()) {
                    doubleArr[i][j] = word.charAt(index);
                    index++;
                } else doubleArr[i][j] = '_';
            }
        }
        return doubleArr;
    }

    public static String foundFirstChar(String word, char[][] doubleArr, int row) {
        for (int i = 0; i < row; i++) {
            for (int j = 0; j < row; j++) {
                if (word.charAt(0) == doubleArr[i][j]) { // found first symbol of wordToBuild in double array
                    return "[" + i + ", " + j + "]->" + foundOthers(i, j, doubleArr, word);
                }
            }
        }
        return "Can't find first char of your word..."; // if not able to find first char - enter this message to console
    }

    public static String foundOthers(int x, int y, char[][] array, String word) { // found other char's at double array
        String resultString = "";
        int newX = x; // coordinates will be changed
        int newY = y;

        int oldX = -1; // this var's need to avoid old char in cycle check
        int oldY = -1; // and because we "don't know" coords of old symbol - they evaluate -1

        for (int i = 1; i < word.length(); i++) {
            for (int xA = newX - 1; xA <= newX + 1; xA++) { // check neighbors symbol at rows
                if (xA < 0 || xA >= array.length || xA == oldX) // check that xA not enter from array and not evaluate old char coord
                    continue;
                else if (xA == newX) { // if coord at our symbol in row - check neighbor columns
                    for (int yA = newY - 1; yA <= newY + 1; yA++) { // check neighbors symbol at columns
                        if (yA < 0 || yA >= array.length || yA == newY || yA == oldY) // check that yA not enter from array and not evaluate old char coord
                            continue;
                        else if (word.charAt(i) == array[newX][yA]) { // if we find next symbol
                            resultString += "[" + newX + ", " + yA + "]->"; // enter this string to resultString
                            oldY = newY; // write old coords
                            oldX = newX;
                            newY = yA; // enter new coords
                            xA = newX + 2; // condition to exit from cycle at 69 line
                            break;
                        }
                    }
                } else if (word.charAt(i) == array[xA][newY]) {// if we find next symbol
                    resultString += "[" + xA + ", " + newY + "]->"; // enter this string to resultString
                    oldX = newX; // write old coords
                    oldY = newY;
                    newX = xA;// enter new coords. There is no need condition to exit from cycle 'cause we already exit from cycle
                    break;
                }
            }
        }
        if ((resultString.length() / 8) != (word.length() - 1)) { // vheck if the string has all coordinates
            return resultString + " here it's unable to find another neighboor's char...";
        } else return resultString;
    }
}