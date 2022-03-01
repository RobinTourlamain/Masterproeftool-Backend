package Bachelorproef.Masterproeftool.Onderwerp;

class Onderwerpnotfoundexception extends RuntimeException {

    Onderwerpnotfoundexception(int id) {
        super("Kan onderwerp met id " + id + " niet vinden");
    }
}