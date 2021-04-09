package lexer;

import exception.LexerException;
import lexer.matcher.*;
import lexer.state.*;
import token.Position;
import token.Token;
import token.builder.TokenBuilder;
import token.builder.TokenBuilderImpl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LexerImpl implements Lexer {

    private List<Matcher> matchers = new ArrayList<>();

    private int line = 0;
    private int column = 0;
    private int currentIndex = 0;

    void init() {
        this.matchers.add(new IdentifierMatcher());
        this.matchers.add(new KeyWordMatcher());
        this.matchers.add(new StringMatcher());
        this.matchers.add(new NumberMatcher());
        this.matchers.add(new SymbolMatcher());
    }

    @Override
    public List<Token> lex(Stream<Character> code) {
        List<Token> resultTokens = new ArrayList<>();


        while (!this.endOfFile(code)) {
            resultTokens.add(this.getToken());
        }

        return resultTokens;
    }

    private Token getToken() {
        for (Matcher matcher : this.matchers) {
           try {
               return matcher.matchAndBuildToken(new Position(this.line, this.column));
           } catch (LexerException e) {
               System.out.println(e.getMessage());
           }
        }
        throw new Error();
    }

    private boolean endOfFile(Stream<Character> code) {
        return this.currentIndex > code.toArray().length;
    }

    private char getCharacter(Stream<Character> code){
        return Arrays.toString(code.toArray()).charAt(currentIndex);
    }

}
