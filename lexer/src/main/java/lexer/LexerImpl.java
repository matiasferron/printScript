package lexer;

import lexer.state.*;
import token.Position;
import token.Token;
import token.builder.TokenBuilder;
import token.builder.TokenBuilderImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LexerImpl implements Lexer {

//    private List<LexerState> possibleStates = new ArrayList<>();
    private LexerState keyWordState;
    private LexerState stringState;
    private LexerState numberState;
    private LexerState identifierState;
    private LexerState symbolState;
    private TokenBuilder tokenBuilder;

    private int line = 0;
    private int column = 0;

    void init() {
        //all possibilities at first
        this.tokenBuilder = TokenBuilderImpl.createBuilder();
        this.keyWordState = new KeyWordState();
        this.stringState = new StringState();
        this.numberState = new NumberState();
        this.identifierState = new IdentifierState();
        this.symbolState = new SymbolState();
    }

    void resetStates() {
        this.keyWordState.reset();
        this.stringState.reset();
        this.numberState.reset();
        this.identifierState.reset();
        this.symbolState.reset();
    }

    @Override
    public List<Token> lex(Stream<Character> code) {
        Character[] chars = (Character[]) code.toArray();
         List<Token> resultTokens = new ArrayList<>();
//        StringBuilder current = new StringBuilder();
        Character current;

        int i = 0;
        while(i < chars.length) {
            current = chars[i]; //leo
            //pregunto que es
            //descarto estados

            //leer y appendear al estado
            //cada estado para saber si es valido tiene que saber el primero q appendie y el current que appendeo ahora
            //


            if(this.keyWordState.isValid()) {
                //acumulo en tu estado, else te doy de baja
            }

            if(this.identifierState.isValid()) {
                //acumulo en tu estado, else te doy de baja
            }

            if(this.stringState.isValid()) {
                //acumulo en tu estado, else te doy de baja
            }

            if(this.numberState.isValid()) {
                //acumulo en tu estado, else te doy de baja
            }

            if(this.symbolState.isValid()) {
                resultTokens.add(
                        tokenBuilder.withType(this.symbolState.getMatcher().
                                matchAndReturnType(this.symbolState.append(current).toString()))
                                    .withValue(current.toString()).withPosition(new Position(line, column)).build());
            }

            i++;
        }





    }

    private void changeState(LexerState state) {

    }

    private boolean isNumber(Character character) {
        return Character.isDigit(character);
    }

    private boolean isAlpha(Character character) {
        return Character.isAlphabetic(character);
    }

//    private boolean oneValidState(){
//
//    }

    /*
     for (Character character : code.collect(Collectors.toList())) {
            //getValidStates

            if (isNumber(character)) {
                accumulator.append(character);
                this.keyWordState.setInvalid();
                this.identifierState.setInvalid();
                this.stringState.setInvalid();
                //descarto estados
                //guardo character en algun lado
            }

            if (isAlpha(character)) {
                //descarto estados
                //guardo character en algun lado
            }
            //es un simbolo o es un unkown

        }

        return new ArrayList<>();
     */
}
