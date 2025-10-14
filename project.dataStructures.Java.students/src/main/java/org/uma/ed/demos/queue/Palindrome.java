package org.uma.ed.demos.queue;

import org.uma.ed.datastructures.queue.ArrayQueue;
import org.uma.ed.datastructures.queue.Queue;
import org.uma.ed.datastructures.stack.ArrayStack;
import org.uma.ed.datastructures.stack.Stack;

public class Palindrome {
    static boolean isPalindrome(String text){
        Stack<Character> stack = ArrayStack.empty();
        Queue<Character> queue = ArrayQueue.empty();

        for(int i = 0; i<text.length();i++){
            Character c = text.charAt(i);
            if(Character.isLetter(c)){
                c = Character.toLowerCase(c);
                stack.push(c);
                queue.enqueue(c);
            } else if(Character.isDigit(c)){
                stack.push(c);
                queue.enqueue(c);
            }
        }
        boolean match = true;
        while(!stack.isEmpty()){
            char c1 = stack.top();
            char c2 = queue.first();
            if(c1 != c2){
                match = false;
            } else{
                stack.pop();
                queue.dequeue();
            }
        }
        return match;
    }

    public static void main(String [] args){
        boolean b1 = isPalindrome("madam");
        System.out.println(b1);

        boolean b2 = isPalindrome("A man, a plan, a canal: Panama");
        System.out.println(b2);
    }
}