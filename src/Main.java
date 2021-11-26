import java.util.*;
import java.io.*;

public class Main
{
	
	public static ArrayList<Token> Scan(Scanner sc) throws UnexpectedCharacter {
		ArrayList<Token> tokenList = new ArrayList<Token>();
		while(sc.hasNext()) {
			String input = sc.next();
			
			Boolean isNumber = input.matches("-?\\d+");
			
			Token tk;
			
			if(isNumber) {
				tk = new Token(TokenType.NUM, input);
			}
			
			else {
				if(input.charAt(0) == '*') {
					tk = new Token(TokenType.STAR, input);
				}
				else if(input.charAt(0) == '-') {
					tk = new Token(TokenType.MINUS, input);
				}
				else if(input.charAt(0) == '/') {
					tk = new Token(TokenType.SLASH, input);
				}
				else if(input.charAt(0) == '+'){
					tk = new Token(TokenType.PLUS, input);
				}
				// Caractere não esperado
				else {
					throw new UnexpectedCharacter("Charactere não reconhecido: " + input);
				}
			}
			
			tokenList.add(tk);
		};
		return tokenList;
	}
	
	public static Integer evaluate(ArrayList<Token> tokenList){
		
		Stack<Integer> auxStack = new Stack<Integer>(); 
		
		for(Integer i = 0 ; i < tokenList.size() ; i++) {
			Token currentToken = tokenList.get(i);
			if(currentToken.type == TokenType.NUM) {
				auxStack.push(Integer.parseInt(currentToken.lexeme));
			}
			else {
				
				if(auxStack.empty()) {
					throw new NotValidExpression();
				}
				Integer firstNumber = auxStack.peek();
				auxStack.pop();
				if(auxStack.empty()) {
					throw new NotValidExpression();
				}
				Integer secondNumber = auxStack.peek();
				
				Integer result = 0;
				
				if(currentToken.lexeme.charAt(0) == '+') {
					result = (firstNumber + secondNumber);
				}
				else if(currentToken.lexeme.charAt(0) == '-') {
					result = (firstNumber - secondNumber);
				}
				else if(currentToken.lexeme.charAt(0) == '*') {
					result = (firstNumber * secondNumber);
				}
				else { // '/'
					result = (firstNumber / secondNumber);
				}
				
				auxStack.push(result);
			}
		}
		
		return auxStack.peek();
	}
	
	
	public static void main(String[] args) {
		// Trocar FILE_PATH para o path do arquivo no seu computador para rodar
		String FILE_PATH = "/home/gustavo/eclipse-workspace/RPN-task02/src/Calc1.stk";
	    
	    try{
	    	File inputFile = new File(FILE_PATH);
		    Scanner sc = new Scanner(inputFile);
		    ArrayList<Token> tokenList = Scan(sc);
		    System.out.println(evaluate(tokenList));
	    }
	    
	    catch(FileNotFoundException e) {
	        System.out.println("Não foi possivel ler o arquivo");
	    }
	    
	}
}