/*
 * Copyright (c) 2021 Ian Clement. All rights reserved.
 */

package ca.qc.johnabbott.cs4p6.utility;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A simple menu with generic results
 * @author Ian Clement (ian.clement@johnabbott.qc.ca)
 */
public class Menu<T> {

    /**
     * Stores a menu item.
     */
    private class MenuItem {
        public String title;
        public T item;

        public MenuItem(String title, T item) {
            this.title = title;
            this.item = item;
        }
    }

    // stores the last choice made.
    private MenuItem lastChoice;

    // stores the menu items available in the order they will be displayed.
    private List<MenuItem> menuItems;

    // if the menu has an exit item
    private boolean hasExit;

    /**
     * Create a menu
     */
    public Menu() {
        menuItems = new ArrayList<>();
        hasExit = false;
    }

    /**
     * Add a menu item
     * @param title
     * @param item
     */
    public void addMenuItem(String title, T item) {
        menuItems.add(new MenuItem(title, item));
    }

    /**
     * Set the menu exit option.
     * @param hasExit
     */
    public void hasExit(boolean hasExit) {
        this.hasExit = hasExit;
    }

    /**
     * Check to see that the menu has an exit option
     * @return
     */
    public boolean hasExit() {
        return hasExit;
    }

    /**
     * Print the menu
     */
    public void printMenu() {
        int indexFieldSize = String.valueOf(menuItems.size()).length();
        int index = 1;
        for(MenuItem menuItem : menuItems)
            System.out.printf(" %"+indexFieldSize+"d - %s\n", index++, menuItem.title);
        if(hasExit)
            System.out.printf(" %"+indexFieldSize+"d - exit\n", 0);
    }

    /**
     * Prompt for the next choice with "exit" as the default option.
     * @return
     */
    public boolean nextChoice() {
        return nextChoice(0);
    }

    /**
     * Prompt for the next choice with a user defined default.
     * @param defaultChoice
     * @return
     */
    public boolean nextChoice(int defaultChoice) {
        Scanner scanner = new Scanner(System.in);
        while(true) {
            System.out.print("Enter your choice [" + defaultChoice + "]: ");
            System.out.flush();

            int choice = scanner.hasNextInt() ? scanner.nextInt() : defaultChoice;

            if (hasExit && choice <= 0)
                return false;
            else if(choice <= menuItems.size()) {
                lastChoice = menuItems.get(choice - 1);
                return true;
            }
        }
    }

    /**
     * Get the user's choice
     * @return
     */
    public T getChoice() {
        return lastChoice.item;
    }

}
