package Bachelorproef.Masterproeftool.Onderwerp;

public class Promotornotfoundexception extends RuntimeException {
    public Promotornotfoundexception(int promotorid) {
        super("kan promotor met id " + promotorid + " niet vinden");
    }
}
