package lexer;

import token.*;
import url.UrlSolver;
import url.UrlSolverImpl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LexerImpl implements Lexer {

    private final AcceptedTokens acceptedTokens;
    private final TokenFactory tokenFactory;
    private final UrlSolver urlSolver;

    private int line = 0;
    private int column = 0;

    public LexerImpl() {
        this.tokenFactory = TokenFactory.newTokenFactory();
        this.acceptedTokens = new AcceptedTokens();
        this.urlSolver = new UrlSolverImpl();
    }

    @Override
    public List<Token> lex(Stream<Character> stream) {

        ArrayList<Token> resultTokens = new ArrayList<>();

        Matcher matcher = getMatcher(stream);

        while (matcher.find()) {
            for (TokenType tokenType: acceptedTokens.getTokenTypes()) {
              if(matcher.group(tokenType.name()) != null)  {
                  Token token = tokenFactory.create(tokenType,
                          matcher.group(tokenType.name()),
                          new Position(this.line, this.column));
                  resultTokens.add(token);
                  this.column += matcher.group(tokenType.name()).length();

                  if(isNewLine(token)) {
                     this.column = 0;
                     this.line++;
                  }
              }
            }
        }
        // recognizes the url token (that contains the regex to download the file) and lexes the file appending the new tokens
        for (Token t: resultTokens) {
            if (isUrl(t)) {
                try {
                    Stream<Character> fileContents = this.urlSolver.downloadFile(t.getTokenValue());
                    resultTokens.addAll(lex(fileContents));
                } catch (IOException e) {
                    System.out.println(e.getMessage());
                }
            }
        }


//        resultTokens.add(tokenFactory.create(TokenType.EOF, "",new Position(this.line, this.column)));
        return resultTokens;
    }


    private Matcher getMatcher(Stream<Character> input) {
        StringBuilder tokenPatternsBuffer = new StringBuilder();
        for (TokenType tokenType: acceptedTokens.getTokenTypes()){
            tokenPatternsBuffer.append(String.format("|(?<%s>%s)", tokenType.name(), acceptedTokens.getPatternByTokenType(tokenType)));
        }

        return Pattern.compile(tokenPatternsBuffer.substring(1)).matcher(input
                .map(Objects::toString)
                .collect(Collectors.joining())
        );
    }

    private boolean isNewLine(Token token) {
        return token.getTokenType() == TokenType.NEWLINE;
    }

    private boolean isUrl(Token token) {
        return token.getTokenType() == TokenType.URL;
    }


}
