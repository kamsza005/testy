package dziurybinarne2;

import java.util.Scanner;

public class liczba {

	public static void main(String[] args) {
		System.out.println("Podaj liczbê binarna:");
		Scanner input = new Scanner(System.in);
        String liczba = input.nextLine();
        
        char[] tablica = liczba.toCharArray();
        
        int i=0,wynik=0,pomocnicza1=1;
        
        
        //########### PÊTLA
        
        while(i<tablica.length){
        	
        	if(tablica[i]=='1') {
        	i++;
        	pomocnicza1=1;
        		while(pomocnicza1==1 && i<tablica.length) {
        			if(tablica[i]=='0') {
        				i++;
        				while(pomocnicza1==1 && i<tablica.length) {
        					if(tablica[i]=='1') {
        						wynik++;
        						pomocnicza1=0;
        					}
        					else if(tablica[i]=='0') {
        						i++;
        					}
        				}
        		
        			}
        			else if(tablica[i]=='1') {
        				i++;
        			}
        		}
        	
        	}
        	else if(tablica[i]=='0') {
        	i++;
        	}
        
        }
        
        //################ KONIEC PÊTLI

        System.out.println("wynik: "+wynik);
	}

}
