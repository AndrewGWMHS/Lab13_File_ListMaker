import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class ListMaker {
    private static ArrayList<String> currentData = new ArrayList<String>();
    private static Scanner scan = new Scanner(System.in);
    public static void main(String[] args) throws IOException {
        boolean playAgain = true;
        boolean needsToBeSaved = false;
        boolean wantsToBeSaved = false;
        boolean isLoaded = false;
        String originalName = "";

        String menu;

        do {
            menu = InputHelper.getRegExString(scan, "Options: \nA  -  Add an item to the list    \nD  -  Delete an item from the list   \nV  -  View the list    \nQ  -  Quit the program   \nO  -  Open a file from PC    \nS  -  Save current file    \nC  -  Clear List", "[AaDdVvQqOoSsCc]");
            if (menu.equalsIgnoreCase("A")) {
                addList();
                if (isLoaded) {
                    needsToBeSaved = true;
                }
            }
            else if (menu.equalsIgnoreCase("D")) {
                deleteList();
                if (isLoaded) {
                    needsToBeSaved = true;
                }
            }
            else if (menu.equalsIgnoreCase("V")) {
                printList();
            }
            else if (menu.equalsIgnoreCase("Q")) {
                playAgain = !quitList();
            }
            else if (menu.equalsIgnoreCase("O")) {
                openList();
            }
            else if (menu.equalsIgnoreCase("S")) {
                saveFile();
            }
            else if (menu.equalsIgnoreCase("C")) {
                clearFile();
                needsToBeSaved = true;
            }

        } while (playAgain);
    }


    private static void addList() {
        String newItem;
        newItem = InputHelper.getNonZeroLenString(scan, "Enter a new item");
        currentData.add(newItem);

    }

    private static void deleteList() {
        int indexRemove;
        indexRemove = InputHelper.getRangedInt(scan, "What item do you want to remove? (Enter index)", 0, currentData.size() - 1);
        currentData.remove(indexRemove);
    }

    private static void printList() {
        for(int i = 0; i < currentData.size(); i++) {
            System.out.println(i + " - "  + currentData.get(i));
        }
    }

    private static boolean quitList(boolean needsToBeSaved, boolean wantsToBeSaved, boolean isLoaded, String originalName) throws IOException {
        if (needsToBeSaved) {
            wantsToBeSaved = InputHelper.getYNConfirm(scan, "Do you want to save your progress? [Y/N]");
            if (wantsToBeSaved) {
                saveFile(isLoaded, needsToBeSaved, originalName);
            }
        }
        boolean quit = InputHelper.getYNConfirm(scan, "Are you sure you want to quit? [Y/N]");
        return quit;
    }

    private static void saveFile(boolean isLoaded, boolean needsToBeSaved, String originalName) throws IOException {
        if (isLoaded) {
            IOHelper.writeFile(currentData, originalName);
        }
        else {
            String name = InputHelper.getNonZeroLenString(scan, "Enter the name of the file");
            IOHelper.writeFile(currentData, name);
        }
        needsToBeSaved = false;
        boolean continueWorking =InputHelper.getYNConfirm(scan, "Do you want to continue working on this file? [Y/N]");
        if (!continueWorking) {
            clearFile();
            isLoaded = false;
        }
    }

    private static String openList(boolean needsToBeSaved, boolean isLoaded, String originalName) throws IOException {
        if (needsToBeSaved) {
            saveFile(isLoaded, needsToBeSaved, originalName);
        }
        clearFile();
        IOHelper.openFile(currentData);
        originalName = IOHelper.fileName;
        isLoaded = true;
        return originalName;
    }

    private static void clearFile() {
        currentData.clear();
    }
}
