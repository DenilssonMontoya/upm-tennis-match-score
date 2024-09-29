package com.upm.tennis.view;

public class HelpView {

  public void display() {
    System.out.println("****************************************************************");
    System.out.println("Commands:");
    System.out.println("\tcreateReferee [name:name-value];[password:password-value]");
    System.out.println("\t\tCreate a new Referee.");
    System.out.println("\tlogin [name:name-value];[password:password-value]");
    System.out.println("\t\tValidate referee credentials.");
    System.out.println("\tcreatePlayer  [name:name-value]");
    System.out.println("\t\tCreate a new player.  Only after login.");
    System.out.println("\treadPlayers ");
    System.out.println("\t\tShows the list of players.  Only after login.");
    System.out.println("\tcreateMatch  [sets:sets-value];[ids:ids-value-list]");
    System.out.println("\t\tCreate a new Tennis match.  Only after login.");
    System.out.println("\tmatch [id:id-value]>[option]");
    System.out.println("\t\tHandle game points. Only after login.");
    System.out.println("\t\toption");
    System.out.println("\t\t\tpointService : Add a point for the player who has the service role.");
    System.out.println("\t\t\tpointRest : Add a point for the player who has the rest role.");
    System.out.println("\t\t\tlackService: Add a fault to the player who has the service role.");
    System.out.println("\treadPlayer [id:id-value]");
    System.out.println("\t\tShows player info.  Only after login.");
    System.out.println("\treadMatch [id:id-value]");
    System.out.println("\t\tShows match info.  Only after login.");
    System.out.println("\tlogout");
    System.out.println("\t\tQuit referee session. Only after login.");
    System.out.println("****************************************************************");
  }
}
