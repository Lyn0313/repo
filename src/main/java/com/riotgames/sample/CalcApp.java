package com.riotgames.sample;

import java.util.ArrayList;
import java.util.Arrays;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;


public class CalcApp {
	
	private static final Map<Character, Integer> basic = new HashMap<Character, Integer>();

	static{
		basic.put('-', 1);
		basic.put('+', 1);
		basic.put('*', 2);
		basic.put('/', 2);
		basic.put('(', 0);
	}
	
	CalcApp(){}


	/*
	 * 문자열 str이 숫자인지 확인하는 메소드.
	 */
	public boolean isNumeric(String str) {
		//pattern은 숫자로만 이루어진 패턴.
		Pattern pattern = Pattern.compile("[0-9]*");
		//str이 pattern에 맞는지 확인.
		Matcher isNum = pattern.matcher(str);
		if (!isNum.matches())
			return false;
		else
			return true;
	}
	
    public double calc(String[] tokens) {
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<tokens.length; i++) {
        	if (this.isNumeric(tokens[i]))
        		sb.append(tokens[i]);
        	else if ("+-*/()".indexOf(tokens[i]) >= 0)
        		sb.append(tokens[i]);
        }
        //숫자와 연산자만 StringBuffer에 저장.
        
        String str = toSuffix(sb.toString());		//후위식으로 변환
        return Double.parseDouble(dealEquation(str)); //계산
    }

    /*
    public static void main( String[] args ) {
        final CalcApp app = new CalcApp();
        final StringBuilder outputs = new StringBuilder();
        Arrays.asList(args).forEach(value -> outputs.append(value + " "));
        System.out.print( "Addition of values: " + outputs + " = ");
        System.out.println(app.calc(args));
    }*/
    
    public String toSuffix(String infix){
		
		List<String> queue = new ArrayList<String>();

		List<Character> stack = new ArrayList<Character>();
	
		char[] charArr = infix.trim().toCharArray();
		
		String standard = "*/+-()";
		
		char ch = '&'; 

		int len = 0;
		
		
		for (int i = 0; i < charArr.length; i++) {
			ch = charArr[i]; 
			
			if(Character.isDigit(ch)){ 
				len++;
			}
			else if(Character.isLetter(ch)){ 
				len++;
			}
			else if(ch == '.'){ 
				len++;
			}
			else if(Character.isSpaceChar(ch)){
			
			
				if(len > 0){
					
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i - len, i)));
					len = 0;
				}
				continue; 
			}
			else if(standard.indexOf(ch) != -1){
				if(len > 0){
		
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i -len, i)));
					len = 0;
				}
				if(ch == '('){
					stack.add(ch);
					continue;
				}
				if(!stack.isEmpty()){ 
					int size = stack.size()-1; 
					boolean flag = false; 
					
					while(size >=0 && ch ==')' && stack.get(size) != '('){
					
						queue.add(String.valueOf(stack.remove(size)));
					
						size--;
					
						flag = true;
					}
			
					while(size >= 0 && !flag && basic.get(stack.get(size)) >= basic.get(ch)){
						queue.add(String.valueOf(stack.remove(size)));
						size--;
					}
				}
				if(ch != ')'){ 
					stack.add(ch);
				}
				else{ 
					stack.remove(stack.size() - 1);
				}
				
			}
			if(i == charArr.length -1){
				if(len > 0){
					
					queue.add(String.valueOf(Arrays.copyOfRange(charArr, i-len+1, i+1)));
				}
				int size = stack.size()-1; 
				
				while(size >= 0){
					queue.add(String.valueOf(stack.remove(size)));
					size --;
				}
			}
		}
		
		return queue.stream().collect(Collectors.joining(","));
		
	}

	public String dealEquation(String equation){
		  String [] arr = equation.split(",");                                    
		  List<String> list = new ArrayList<String>();                            
		         
		  for (int i = 0; i < arr.length; i++) {
		        int size = list.size();
		        switch (arr[i]) {
		           case "+":
		        	   Operator operator = Operator.findOperator(arr[i]);
		        	   double a = operator.evaluate(Double.parseDouble(list.remove(size-2)), Double.parseDouble(list.remove(size-2)));
		        	    //= Double.parseDouble(list.remove(size-2))+ Double.parseDouble(list.remove(size-2)); 
		        	   list.add(String.valueOf(a));     
		        	   break;
		           case "-": 
		        	   Operator operator2 = Operator.findOperator(arr[i]);
		        	   double b = operator2.evaluate(Double.parseDouble(list.remove(size-2)), Double.parseDouble(list.remove(size-2)));
		        	  // double b = Double.parseDouble(list.remove(size-2))- Double.parseDouble(list.remove(size-2)); 
		        	   list.add(String.valueOf(b));     
		        	   break;
		           case "*":
		        	   Operator operator3 = Operator.findOperator(arr[i]);
		        	   double c = operator3.evaluate(Double.parseDouble(list.remove(size-2)), Double.parseDouble(list.remove(size-2)));
		        	   //double c = Double.parseDouble(list.remove(size-2))* 
		        	   //Double.parseDouble(list.remove(size-2)); list.add(String.valueOf(c));     
		        	   break;
		           case "/":
		        	   Operator operator4 = Operator.findOperator(arr[i]);
		        	   double d = operator4.evaluate(Double.parseDouble(list.remove(size-2)), Double.parseDouble(list.remove(size-2)));
		        	   //double d = Double.parseDouble(list.remove(size-2))/ Double.parseDouble(list.remove(size-2)); 
		        	   list.add(String.valueOf(d));     
		        	   break;
		           default: 
		        	   list.add(arr[i]);     
		        	   break;                                    
		         }
		  }
		         
		
  return list.size() == 1 ? list.get(0) : "compute error" ;
	}
	
	
}
