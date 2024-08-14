package com.kingmang.lazurite.parser.impl;

import com.kingmang.lazurite.exceptions.FileInfo;
import com.kingmang.lazurite.exceptions.IFileInfoProvider;
import com.kingmang.lazurite.exceptions.LzrException;
import com.kingmang.lazurite.exceptions.parser.ParseErrors;
import com.kingmang.lazurite.parser.ast.Accessible;
import com.kingmang.lazurite.parser.ast.Arguments;
import com.kingmang.lazurite.parser.ast.ArgumentsBuilder;
import com.kingmang.lazurite.parser.ast.expressions.*;
import com.kingmang.lazurite.parser.ast.statements.*;
import com.kingmang.lazurite.parser.IParser;
import com.kingmang.lazurite.parser.tokens.Token;
import com.kingmang.lazurite.parser.tokens.TokenType;
import com.kingmang.lazurite.patterns.Pattern;
import com.kingmang.lazurite.patterns.VariablePattern;
import com.kingmang.lazurite.runtime.UserDefinedFunction;
import com.kingmang.lazurite.runtime.values.LzrMap;
import com.kingmang.lazurite.runtime.values.LzrNumber;
import com.kingmang.lazurite.runtime.values.LzrString;
import lombok.Getter;
import lombok.Setter;
import org.jetbrains.annotations.NotNull;

import java.util.Map;
import java.util.*;


public final class ParserImplementation implements IParser, IFileInfoProvider {
    public Statement parse(List<Token> tokens, String file) {
        final ParserImplementation parser = new ParserImplementation(tokens, file);
        final Statement program = parser.parse();
        if (parser.getParseErrors().hasErrors()) {
            throw new LzrException("ParseException ", "");
        }
        return program;
    }

    private static final Token EOF = new Token(TokenType.EOF, "", -1, -1);
    private final Map<String, Integer> macros;
    private static final EnumMap<TokenType, BinaryExpression.Operator> ASSIGN_OPERATORS = new EnumMap<>(TokenType.class);

    static {
        ASSIGN_OPERATORS.put(TokenType.PLUSEQ, BinaryExpression.Operator.ADD);
        ASSIGN_OPERATORS.put(TokenType.MINUSEQ, BinaryExpression.Operator.SUBTRACT);
        ASSIGN_OPERATORS.put(TokenType.STAREQ, BinaryExpression.Operator.MULTIPLY);
        ASSIGN_OPERATORS.put(TokenType.SLASHEQ, BinaryExpression.Operator.DIVIDE);
        ASSIGN_OPERATORS.put(TokenType.PERCENTEQ, BinaryExpression.Operator.REMAINDER);
        ASSIGN_OPERATORS.put(TokenType.AMPEQ, BinaryExpression.Operator.AND);
        ASSIGN_OPERATORS.put(TokenType.CARETEQ, BinaryExpression.Operator.XOR);
        ASSIGN_OPERATORS.put(TokenType.BAREQ, BinaryExpression.Operator.OR);
        ASSIGN_OPERATORS.put(TokenType.COLONCOLONEQ, BinaryExpression.Operator.PUSH);
        ASSIGN_OPERATORS.put(TokenType.LTLTEQ, BinaryExpression.Operator.LSHIFT);
        ASSIGN_OPERATORS.put(TokenType.GTGTEQ, BinaryExpression.Operator.RSHIFT);
        ASSIGN_OPERATORS.put(TokenType.GTGTGTEQ, BinaryExpression.Operator.URSHIFT);
        ASSIGN_OPERATORS.put(TokenType.EQ, null);
        ASSIGN_OPERATORS.put(TokenType.ATEQ, BinaryExpression.Operator.AT);
    }

    private static final EnumSet<TokenType> NUMBER_TOKEN_TYPES = EnumSet.of(
            TokenType.NUMBER,
            TokenType.LONG_NUM,
            TokenType.DOUBLE_NUM,
            TokenType.INT_NUM,
            TokenType.FLOAT_NUM,
            TokenType.BYTE_NUM,
            TokenType.SHORT_NUM,
            TokenType.HEX_NUMBER
    );

    private final List<Token> tokens;
    private final int size;

    //The getter is overridden by the Parser interface
    @Getter
    private final ParseErrors parseErrors;

    @Setter
    @Getter
    private Statement parsedStatement;

    private int pos;
    private final String file;

    public ParserImplementation(List<Token> tokens, String file) {
        this.tokens = tokens;
        size = tokens.size();
        this.file = file;
        this.macros = new HashMap<>();
        parseErrors = new ParseErrors();
    }

    @NotNull
    @Override
    public FileInfo getFile() {
        return new FileInfo(file, getErrorLine());
    }

    @Override
    public Statement parse() {
        parseErrors.clear();
        final BlockStatement result = new BlockStatement();
        while (!match(TokenType.EOF)) {
            try {
                result.add(statement());
            } catch (Exception ex) {
                parseErrors.add(ex, getErrorLine());
                recover();
            }
        }
        parsedStatement = result;
        return result;
    }

    public Expression parseExpression() {
        return expression();
    }

    private int getErrorLine() {
        if (size == 0) return 0;
        if (pos >= size) return tokens.get(size - 1).getRow();
        return tokens.get(pos).getRow();
    }

    private void recover() {
        int preRecoverPosition = pos;
        for (int i = preRecoverPosition; i <= size; i++) {
            pos = i;
            try {
                statement();
                // successfully parsed,
                pos = i; // restore position
                return;
            } catch (Exception ex) {
                // fail
            }
        }
    }

    private Statement block() {
        final BlockStatement block = new BlockStatement();
        consume(TokenType.LBRACE);

        while (!match(TokenType.RBRACE))
            block.add(statement());

        return block;
    }

    private Statement statementOrBlock() {
        return lookMatch(0, TokenType.LBRACE)
                ? block() : statement();
    }

    private Statement statement() {
        if (lookMatch(0, TokenType.WORD) && macros.containsKey(get(0).getText()))
            return macroUsage();

        if (match(TokenType.PRINT))
            return new PrintStatement(expression());

        if (match(TokenType.ASSERT))
            return new AssertStatement(expression());

        if (match(TokenType.PRINTLN))
            return new PrintlnStatement(expression());

        if (match(TokenType.IF))
            return ifElse();

        if (match(TokenType.WHILE))
            return whileStatement();

        if (match(TokenType.BREAK))
            return new BreakStatement();

        if (match(TokenType.CONTINUE))
            return new ContinueStatement();

        if (match(TokenType.RETURN))
            return new ReturnStatement(expression());

        if (match(TokenType.ENUM))
            return enums();

        if (match(TokenType.USING))
            return new UsingStatement(expression());

        if (match(TokenType.FOR))
            return forStatement();

        if (match(TokenType.MACRO))
            return macro();

        if (match(TokenType.FUNC))
            return functionDefine();

        if (match(TokenType.SWITCH))
            return match();

        if (match(TokenType.CLASS))
            return classDeclaration();

        if (lookMatch(0, TokenType.WORD) && lookMatch(1, TokenType.LPAREN))
            return new ExprStatement(functionChain(qualifiedName()));

        else if (match(TokenType.THROW))
            return throwStatement();

        else if (match(TokenType.TRY))
            return tryCatch();

        return assignmentStatement();
    }

    private Statement tryCatch() {
        final Statement tryStatement = statementOrBlock();
        final Statement catchStatement;
        if (match(TokenType.CATCH)) {
            catchStatement = statementOrBlock();
        } else {
            throw new LzrException("CatchBlockError", "the catch block was not found");
        }
        return new TryCatchStatement(tryStatement, catchStatement);
    }

    private Statement macro() {
        String name = consume(TokenType.WORD).getText();
        Arguments args = arguments();
        Statement block = statementOrBlock();
        macros.put(name, args.size());
        return new FunctionDefineStatement(name, args, block);
    }

    private Statement macroUsage() {
        String name = consume(TokenType.WORD).getText();
        ArrayList<Expression> exprs = new ArrayList<>();

        for (int i = 0; i < macros.get(name); i++)
            exprs.add(expression());

        return new FunctionalExpression(new VariableExpression(name, getFile()), exprs);
    }

    private Statement throwStatement() {
        FileInfo file = getFile();
        String type = consume(TokenType.WORD).getText();
        Expression expr = expression();
        return new ThrowStatement(type, expr, file);
    }

    private Statement assignmentStatement() {

        final Expression expression = expression();
        if (expression instanceof Statement) {
            return (Statement) expression;
        }
        throw new LzrException("ParseException ", "Unknown statement: " + get(0));
    }

    private Statement ifElse() {
        final Expression condition = expression();
        final Statement ifStatement = statementOrBlock();

        final Statement elseStatement = match(TokenType.ELSE)
                ? statementOrBlock() : null;

        return new IfStatement(condition, ifStatement, elseStatement);
    }

    private Statement whileStatement() {
        final Expression condition = expression();
        final Statement statement = statementOrBlock();
        return new WhileStatement(condition, statement);
    }


    private Statement enums() {
        String name = consume(TokenType.WORD).getText();
        LzrMap enums = new LzrMap(1);
        consume(TokenType.LBRACE);
        while (!(match(TokenType.RBRACE))) {
            String en = consume(TokenType.WORD).getText();
            match(TokenType.COMMA);
            enums.set(en, new LzrString(en));
        }
        return new AssignmentStatement(name, new ValueExpression(enums));
    }

    private Statement forStatement() {
        int foreachIndex = lookMatch(0, TokenType.LPAREN) ? 1 : 0;

        if (
                lookMatch(foreachIndex, TokenType.WORD)
                        && lookMatch(foreachIndex + 1, TokenType.COLON)
        ) return foreachArrayStatement();

        if (
                lookMatch(foreachIndex, TokenType.WORD)
                        && lookMatch(foreachIndex + 1, TokenType.COMMA)
                        && lookMatch(foreachIndex + 2, TokenType.WORD)
                        && lookMatch(foreachIndex + 3, TokenType.COLON)
        ) return foreachMapStatement();

        boolean optParentheses = match(TokenType.LPAREN);
        final Statement initialization = assignmentStatement();
        consume(TokenType.COMMA);
        final Expression termination = expression();
        consume(TokenType.COMMA);
        final Statement increment = assignmentStatement();
        if (optParentheses) consume(TokenType.RPAREN); // close opt parentheses
        final Statement statement = statementOrBlock();
        return new ForStatement(initialization, termination, increment, statement);
    }

    private ForeachAStatement foreachArrayStatement() {
        boolean optParentheses = match(TokenType.LPAREN);
        final String variable = consume(TokenType.WORD).getText();
        consume(TokenType.COLON);
        final Expression container = expression();

        if (optParentheses)
            consume(TokenType.RPAREN); // close opt parentheses

        final Statement statement = statementOrBlock();
        return new ForeachAStatement(variable, container, statement);
    }


    private ForeachMStatement foreachMapStatement() {

        boolean optParentheses = match(TokenType.LPAREN);
        final String key = consume(TokenType.WORD).getText();
        consume(TokenType.COMMA);
        final String value = consume(TokenType.WORD).getText();
        consume(TokenType.COLON);
        final Expression container = expression();

        if (optParentheses)
            consume(TokenType.RPAREN); // close opt parentheses

        final Statement statement = statementOrBlock();
        return new ForeachMStatement(key, value, container, statement);
    }

    private FunctionDefineStatement functionDefine() {
        final String name = consume(TokenType.WORD).getText();
        final Arguments arguments = arguments();
        final Statement body = statementBody();
        return new FunctionDefineStatement(name, arguments, body);
    }

    private Arguments arguments() {

        final ArgumentsBuilder builder = new ArgumentsBuilder();
        boolean startsOptionalArgs = false;
        consume(TokenType.LPAREN);
        while (!match(TokenType.RPAREN)) {
            final String name = consume(TokenType.WORD).getText();
            if (match(TokenType.EQ)) {
                startsOptionalArgs = true;
                builder.addOptional(name, variable());
            } else if (!startsOptionalArgs) {
                builder.addRequired(name);
            } else {
                throw new LzrException("ParseException ", "Required argument cannot be after optional");
            }
            match(TokenType.COMMA);
        }
        return builder.build();
    }


    private Statement statementBody() {
        return match(TokenType.EQ)
                ? new ReturnStatement(expression()) : statementOrBlock();
    }

    private Expression functionChain(Expression qualifiedNameExpr) {
        final Expression expr = function(qualifiedNameExpr);

        if (lookMatch(0, TokenType.LPAREN))
            return functionChain(expr);

        if (lookMatch(0, TokenType.DOT)) {
            final List<Expression> indices = variableSuffix();
            if (indices == null || indices.isEmpty())
                return expr;


            if (lookMatch(0, TokenType.LPAREN))
                return functionChain(new ContainerAccessExpression(expr, indices));


            return new ContainerAccessExpression(expr, indices);
        }

        return expr;
    }

    private FunctionalExpression function(Expression qualifiedNameExpr) {
        consume(TokenType.LPAREN);
        final FunctionalExpression function = new FunctionalExpression(qualifiedNameExpr);

        while (!match(TokenType.RPAREN)) {
            function.addArgument(expression());
            match(TokenType.COMMA);
        }

        return function;
    }

    private Expression array() {
        consume(TokenType.LBRACKET);
        final List<Expression> elements = new ArrayList<>();

        while (!match(TokenType.RBRACKET)) {
            elements.add(expression());
            match(TokenType.COMMA);
        }

        return new ArrayExpression(elements);
    }

    private Expression map() {
        consume(TokenType.LBRACE);
        final Map<Expression, Expression> elements = new HashMap<>();

        while (!match(TokenType.RBRACE)) {
            final Expression key = primary();
            consume(TokenType.COLON);
            final Expression value = expression();
            elements.put(key, value);
            match(TokenType.COMMA);
        }

        return new MapExpression(elements);
    }

    private MatchExpression match() {
        final Expression expression = expression();
        consume(TokenType.LBRACE);
        final List<MatchExpression.Pattern> patterns = new ArrayList<>();

        do {
            consume(TokenType.CASE);
            MatchExpression.Pattern pattern = null;
            final Token current = get(0);
            if (isNumberToken(current.getType())) {
                // case 20: / case 0.5: / case #FF:
                pattern = new MatchExpression.ConstantPattern(
                        LzrNumber.of(getAsNumber(current))
                );
            } else if (match(TokenType.HEX_NUMBER)) {
                // case #FF:
                pattern = new MatchExpression.ConstantPattern(
                        LzrNumber.of(createNumber(current.getText(), 16))
                );
            } else if (match(TokenType.TEXT)) {
                // case "text":
                pattern = new MatchExpression.ConstantPattern(
                        new LzrString(current.getText())
                );
            } else if (match(TokenType.WORD)) {
                // case value:
                pattern = new MatchExpression.VariablePattern(current.getText());
            } else if (match(TokenType.LBRACKET)) {
                // case [x :: xs]:
                final MatchExpression.ListPattern listPattern = new MatchExpression.ListPattern();
                while (!match(TokenType.RBRACKET)) {
                    listPattern.add(consume(TokenType.WORD).getText());
                    match(TokenType.COLONCOLON);
                }
                pattern = listPattern;
            } else if (match(TokenType.LPAREN)) {

                final MatchExpression.TuplePattern tuplePattern = new MatchExpression.TuplePattern();
                while (!match(TokenType.RPAREN)) {
                    if ("_".equals(get(0).getText())) {
                        tuplePattern.addAny();
                        consume(TokenType.WORD);
                    } else {
                        tuplePattern.add(expression());
                    }
                    match(TokenType.COMMA);
                }
                pattern = tuplePattern;
            }

            if (pattern == null) {
                throw new LzrException("ParseException ", "Wrong pattern in match expression: " + current);
            }
            if (match(TokenType.IF)) {
                pattern.setOptCondition(expression());
            }

            consume(TokenType.COLON);
            if (lookMatch(0, TokenType.LBRACE))
                pattern.setResult(block());
            else pattern.setResult(new ReturnStatement(expression()));
            patterns.add(pattern);
        } while (!match(TokenType.RBRACE));

        return new MatchExpression(expression, patterns);
    }

    private Statement classDeclaration() {

        final String name = consume(TokenType.WORD).getText();
        final ClassDeclarationStatement classDeclaration = new ClassDeclarationStatement(name);
        consume(TokenType.LBRACE);
        do {
            if (match(TokenType.FUNC)) {
                classDeclaration.addMethod(functionDefine());
            } else {
                final AssignmentExpression fieldDeclaration = assignmentStrict();
                if (fieldDeclaration != null) {
                    classDeclaration.addField(fieldDeclaration);
                } else {
                    throw new LzrException("ParseException", "Class can contain only assignments and function declarations");
                }
            }
        } while (!match(TokenType.RBRACE));
        return classDeclaration;
    }

    public Expression expression() {
        return assignment();
    }

    private Expression assignment() {
        final Expression assignment = assignmentStrict();
        if (assignment != null)
            return assignment;

        return ternary();
    }

    private AssignmentExpression assignmentStrict() {
        // x[0].prop += ...
        final int position = pos;
        final Expression targetExpr = qualifiedName();

        if (!(targetExpr instanceof Accessible)) {
            pos = position;
            return null;
        }

        final TokenType currentType = get(0).getType();

        if (!ASSIGN_OPERATORS.containsKey(currentType)) {
            pos = position;
            return null;
        }

        match(currentType);

        final BinaryExpression.Operator op = ASSIGN_OPERATORS.get(currentType);
        final Expression expression = expression();

        return new AssignmentExpression(op, (Accessible) targetExpr, expression);
    }

    private Expression ternary() {
        Expression result = nullCoalesce();

        if (match(TokenType.QUESTION)) {
            final Expression trueExpr = expression();
            consume(TokenType.COLON);
            final Expression falseExpr = expression();
            return new TernaryExpression(result, trueExpr, falseExpr);
        }

        if (match(TokenType.QUESTIONCOLON))
            return new BinaryExpression(BinaryExpression.Operator.ELVIS, result, expression());

        return result;
    }

    private Expression nullCoalesce() {
        Expression result = logicalOr();

        while (true) {
            if (match(TokenType.QUESTIONQUESTION)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.NULL_COALESCE, result, expression());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression logicalOr() {
        Expression result = logicalAnd();

        while (true) {
            if (match(TokenType.BARBAR)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.OR, result, logicalAnd());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression logicalAnd() {
        Expression result = bitwiseOr();

        while (true) {
            if (match(TokenType.AMPAMP)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.AND, result, bitwiseOr());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression bitwiseOr() {
        Expression expression = bitwiseXor();

        while (true) {
            if (match(TokenType.BAR)) {
                expression = new BinaryExpression(BinaryExpression.Operator.OR, expression, bitwiseXor());
                continue;
            }
            break;
        }

        return expression;
    }

    private Expression bitwiseXor() {
        Expression expression = bitwiseAnd();

        while (true) {
            if (match(TokenType.CARET)) {
                expression = new BinaryExpression(BinaryExpression.Operator.XOR, expression, bitwiseAnd());
                continue;
            }
            break;
        }

        return expression;
    }

    private Expression bitwiseAnd() {
        Expression expression = equality();

        while (true) {
            if (match(TokenType.AMP)) {
                expression = new BinaryExpression(BinaryExpression.Operator.AND, expression, equality());
                continue;
            }
            break;
        }

        return expression;
    }

    private Expression equality() {
        Expression result = conditional();

        if (match(TokenType.EQEQ)) {
            return new ConditionalExpression(ConditionalExpression.Operator.EQUALS, result, conditional());
        }
        if (match(TokenType.EXCLEQ)) {
            return new ConditionalExpression(ConditionalExpression.Operator.NOT_EQUALS, result, conditional());
        }

        return result;
    }

    private Expression conditional() {
        Expression result = shift();

        while (true) {
            if (match(TokenType.LT)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.LT, result, shift());
                continue;
            }
            if (match(TokenType.LTEQ)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.LTEQ, result, shift());
                continue;
            }
            if (match(TokenType.GT)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.GT, result, shift());
                continue;
            }
            if (match(TokenType.GTEQ)) {
                result = new ConditionalExpression(ConditionalExpression.Operator.GTEQ, result, shift());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression shift() {
        Expression expression = additive();

        while (true) {
            if (match(TokenType.LTLT)) {
                expression = new BinaryExpression(BinaryExpression.Operator.LSHIFT, expression, additive());
                continue;
            }
            if (match(TokenType.GTGT)) {
                expression = new BinaryExpression(BinaryExpression.Operator.RSHIFT, expression, additive());
                continue;
            }
            if (match(TokenType.GTGTGT)) {
                expression = new BinaryExpression(BinaryExpression.Operator.URSHIFT, expression, additive());
                continue;
            }
            if (match(TokenType.DOTDOT)) {
                expression = new BinaryExpression(BinaryExpression.Operator.RANGE, expression, additive());
                continue;
            }
            break;
        }

        return expression;
    }

    private Expression additive() {
        Expression result = multiplicative();

        while (true) {
            if (match(TokenType.PLUS)) {
                result = new BinaryExpression(BinaryExpression.Operator.ADD, result, multiplicative());
                continue;
            }
            if (match(TokenType.MINUS)) {
                result = new BinaryExpression(BinaryExpression.Operator.SUBTRACT, result, multiplicative());
                continue;
            }
            if (match(TokenType.COLONCOLON)) {
                result = new BinaryExpression(BinaryExpression.Operator.PUSH, result, multiplicative());
                continue;
            }
            if (match(TokenType.AT)) {
                result = new BinaryExpression(BinaryExpression.Operator.AT, result, multiplicative());
                continue;
            }
            if (match(TokenType.CARETCARET)) {
                result = new BinaryExpression(BinaryExpression.Operator.CARETCARET, result, multiplicative());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression multiplicative() {
        Expression result = objectCreation();

        while (true) {
            if (match(TokenType.STAR)) {
                result = new BinaryExpression(BinaryExpression.Operator.MULTIPLY, result, objectCreation());
                continue;
            }
            if (match(TokenType.SLASH)) {
                result = new BinaryExpression(BinaryExpression.Operator.DIVIDE, result, objectCreation());
                continue;
            }
            if (match(TokenType.PERCENT)) {
                result = new BinaryExpression(BinaryExpression.Operator.REMAINDER, result, objectCreation());
                continue;
            }
            if (match(TokenType.STARSTAR)) {
                result = new BinaryExpression(BinaryExpression.Operator.POWER, result, objectCreation());
                continue;
            }
            break;
        }

        return result;
    }

    private Expression objectCreation() {
        if (match(TokenType.NEW)) {
            final String className = consume(TokenType.WORD).getText();
            final List<Expression> args = new ArrayList<>();
            consume(TokenType.LPAREN);
            while (!match(TokenType.RPAREN)) {
                args.add(expression());
                match(TokenType.COMMA);
            }
            return new ObjectCreationExpression(className, args);
        }

        return unary();
    }

    private Expression unary() {
        if (match(TokenType.PLUSPLUS))
            return new UnaryExpression(UnaryExpression.Operator.INCREMENT_PREFIX, primary());

        if (match(TokenType.MINUSMINUS))
            return new UnaryExpression(UnaryExpression.Operator.DECREMENT_PREFIX, primary());

        if (match(TokenType.MINUS))
            return new UnaryExpression(UnaryExpression.Operator.NEGATE, primary());

        if (match(TokenType.EXCL))
            return new UnaryExpression(UnaryExpression.Operator.NOT, primary());

        if (match(TokenType.TILDE))
            return new UnaryExpression(UnaryExpression.Operator.COMPLEMENT, primary());

        if (match(TokenType.PLUS))
            return primary();

        return primary();
    }

    private Expression primary() {
        if (match(TokenType.LPAREN)) {
            Expression result = expression();
            consume(TokenType.RPAREN);
            return result;
        }

        if (match(TokenType.COLONCOLON)) {
            final String functionName = consume(TokenType.WORD).getText();
            return new FunctionReferenceExpression(functionName);
        }

        if (match(TokenType.SWITCH)) {
            return match();
        }

        if (match(TokenType.FUNC)) {
            final Arguments arguments = arguments();
            final Statement statement = statementBody();
            return new ValueExpression(new UserDefinedFunction(arguments, statement));
        }

        return variable();
    }


    private Expression variable() {
        if (lookMatch(0, TokenType.WORD) && lookMatch(1, TokenType.LPAREN))
            return functionChain(new ValueExpression(consume(TokenType.WORD).getText()));

        final Expression qualifiedNameExpr = qualifiedName();

        if (qualifiedNameExpr != null) {
            if (lookMatch(0, TokenType.LPAREN))
                return functionChain(qualifiedNameExpr);

            if (match(TokenType.PLUSPLUS))
                return new UnaryExpression(UnaryExpression.Operator.INCREMENT_POSTFIX, qualifiedNameExpr);

            if (match(TokenType.MINUSMINUS))
                return new UnaryExpression(UnaryExpression.Operator.DECREMENT_POSTFIX, qualifiedNameExpr);

            return qualifiedNameExpr;
        }

        if (lookMatch(0, TokenType.LBRACKET))
            return array();

        if (lookMatch(0, TokenType.LBRACE))
            return map();


        return value();
    }

    private Expression qualifiedName() {
        // var || var.key[index].key2
        final Token current = get(0);
        if (!match(TokenType.WORD)) return null;

        final List<Expression> indices = variableSuffix();
        if (indices == null || indices.isEmpty())
            return new VariableExpression(current.getText(), getFile());

        return new ContainerAccessExpression(current.getText(), indices, getFile());
    }

    private List<Expression> variableSuffix() {
        // .key1.arr1[expr1][expr2].key2
        if (!lookMatch(0, TokenType.DOT) && !lookMatch(0, TokenType.LBRACKET))
            return null;

        final List<Expression> indices = new ArrayList<>();
        while (lookMatch(0, TokenType.DOT) || lookMatch(0, TokenType.LBRACKET)) {
            if (match(TokenType.DOT)) {
                final String fieldName = consume(TokenType.WORD).getText();
                final Expression key = new ValueExpression(fieldName);
                indices.add(key);
            }

            if (match(TokenType.LBRACKET)) {
                indices.add(expression());
                consume(TokenType.RBRACKET);
            }
        }

        return indices;
    }

    private Expression value() {
        final Token current = get(0);

        if (isNumberToken(current.getType()))
            return new ValueExpression(getAsNumber(current));

        if (match(TokenType.TEXT)) {
            final ValueExpression strExpr = new ValueExpression(current.getText());

            if (lookMatch(0, TokenType.DOT)) {
                if (lookMatch(1, TokenType.WORD) && lookMatch(2, TokenType.LPAREN)) {
                    match(TokenType.DOT);
                    return functionChain(new ContainerAccessExpression(
                            strExpr, Collections.singletonList(
                            new ValueExpression(consume(TokenType.WORD).getText())
                    )));
                }

                final List<Expression> indices = variableSuffix();
                if (indices == null || indices.isEmpty())
                    return strExpr;

                return new ContainerAccessExpression(strExpr, indices);
            }

            return strExpr;
        }

        throw new LzrException("ParseException ", "Unknown expression: " + current);
    }

    private boolean isNumberToken(TokenType type) {
        return NUMBER_TOKEN_TYPES.contains(type);
    }

    private Number getAsNumber(Token current) {
        if (match(TokenType.NUMBER))
            return createNumber(current.getText(), 10);

        if (match(TokenType.FLOAT_NUM))
            return createFloatNumber(current.getText());

        if (match(TokenType.DOUBLE_NUM))
            return createDoubleNumber(current.getText());

        if (match(TokenType.INT_NUM))
            return createIntegerNumber(current.getText(), 10);

        if (match(TokenType.BYTE_NUM))
            return createByteNumber(current.getText());

        if (match(TokenType.SHORT_NUM))
            return createShortNumber(current.getText());

        if (match(TokenType.LONG_NUM))
            return createLongNumber(current.getText());

        if (match(TokenType.HEX_NUMBER))
            return createNumber(current.getText(), 16);

        throw new LzrException("error: ", "Unknown number expression: " + current);
    }

    private Number createNumber(String text, int radix) {
        try {
            return createIntegerNumber(text, radix);
        } catch (NumberFormatException nfe) {
            return createDoubleNumber(text);
        }
    }

    private Number createLongNumber(String text) {
        return Long.parseLong(text, 10);
    }

    private Number createShortNumber(String text) {
        return Short.parseShort(text, 10);
    }

    private Number createByteNumber(String text) {
        return Byte.parseByte(text, 10);
    }

    private Number createIntegerNumber(String text, int radix) {
        return Integer.parseInt(text, radix);
    }

    private Number createFloatNumber(String text) {
        return Float.parseFloat(text);
    }

    private Number createDoubleNumber(String text) {
        return Double.parseDouble(text);
    }

    private Token consume(TokenType type) {
        final Token current = get(0);
        if (type != current.getType())
            throw new LzrException("ParseException ", "Token " + current + " doesn't match " + type);

        pos++;
        return current;
    }

    private boolean match(TokenType type) {
        final Token current = get(0);

        if (type != current.getType())
            return false;

        pos++;
        return true;
    }

    private boolean lookMatch(int pos, TokenType type) {
        return get(pos).getType() == type;
    }

    private Token get(int relativePosition) {
        final int position = pos + relativePosition;

        if (position >= size)
            return EOF;

        return tokens.get(position);
    }

    private Pattern pattern(Expression ast) {
        if (ast instanceof VariableExpression)
            return new VariablePattern(ast.toString());
        return null;
    }
}
