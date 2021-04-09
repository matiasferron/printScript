package lexer.state;

import lexer.matcher.Matcher;

public abstract class LexerState {

    private boolean valid;
    private Matcher matcher;
    StringBuilder accumulator;

    public LexerState(Matcher matcher) {
        this.valid = true;
        this.matcher = matcher;
        accumulator = new StringBuilder();
    }

    public boolean isValid() {
        return valid;
    }

    public void makeValid(){
        this.valid = true;
    }

    public void reset(){
        this.valid = true;
        this.accumulator = new StringBuilder();
    }

    public void makeInvalid(){
        this.valid = false;
    }

    //casi seguro que tengo que retornarlo
    public StringBuilder append(char c) {
        this.accumulator.append(c);
        return this.accumulator;
    }

    public Matcher getMatcher() {
        return this.matcher;
    }



}
