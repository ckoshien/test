package knapsack;


public class knapsackData {
	public static void main(String[] args) {
		int capacity = 1110;

		int[]price={
				842,968,372,313
				};
		int[]weight={
				842,968,372,313
				};
		System.out.println(computeKnapsack(capacity,price,weight));
	}

	public static int computeKnapsack(int capacity, int[] price, int[] weight) {
		int numItem = price.length;
		int[][] dp = new int[numItem + 1][capacity + 1];
		int[][] g = new int[numItem + 1][capacity + 1];
		for (int c=0;c<=capacity;c++){
			dp[0][c]=0;
			g[0][c]=1;
		}
		for(int i=1;i<=numItem;i++){dp[i][0]=0;}
		for (int c = 1; c <= capacity; c++) {
			dp[1][c] = c < weight[0] ? 0 : price[0];
//			g[0][c]=0;
		}
		for (int i = 2; i <= numItem; i++) {
			for (int c = 1; c <= capacity; c++) {
				if (c < weight[i - 1]) {
					dp[i][c] = dp[i - 1][c];
					g[i][c] = 0;
				} else {
					dp[i][c] = Math.max(dp[i - 1][c], dp[i - 1][c - weight[i - 1]]
							+ price[i - 1]);
					g[i][c] = 0;
					if (dp[i][c]==dp[i - 1][c - weight[i - 1]]
					                        + price[i - 1]){
							g[i][c]=1;
						}
				}
			}
		}
		for(int i=1;i<numItem+1;i++){
			for(int j=1;j<capacity+1;j++){
//				System.out.println("d["+i+"]["+j+"]="+dp[i][j]);
				if(j<=capacity+1){
					System.out.printf("|%6d(%1d,%2d)",dp[i][j],g[i][j],i);
				}
				if(j==capacity){System.out.println("");}
			}
		}
//		for(int i=1;i<numItem+1;i++){
//			for(int j=1;j<capacity+1;j++){
//				if(j<=capacity+1){
//					System.out.printf("|%1d",g[i][j]);
//				}
//				if(j==capacity){System.out.println("");}
//
//			}
//		}
		int c=capacity;
		for (int i=numItem;i>0;i--){
			if(g[i][c]==1){
				System.out.print(price[i-1]+" ");
				c=c-weight[i-1];
			}
		}
		return dp[numItem][capacity];
	}
}