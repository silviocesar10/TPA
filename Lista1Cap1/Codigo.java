public class Codigo
{
	public static void main(String[] args)
	{
		int i =0, N =10;
		//questao 1
		//for (i = 0; i < N; i++)
        	//	System.out.printf("%d\n", i);
		//questao 2
		//for (i = 0; i < N; i = i+2)
        	//	System.out.printf("%d\n", i);
		//questao 3
		//for (i = 0; i < N; i= i+2){
        	//	System.out.printf("%d\n", i);
		//	i--;
		//}
		//questao 4
		//int[] V = new int[] {0,1,2,3,4,5,6,7,8,9};
		int j =0;
		int[] V = new int[] {9,8,7,6,5,4,3,2,1,0};
		for (i = 0; i < N-1; i++){
        		for(j = i+1; j < N; j++){        
            			if(V[i] < V[j]){
               				 System.out.printf("%d\n", i);
            			}
        		}
    		}		
	}
}
