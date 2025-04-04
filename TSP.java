package routing;
import java.util.*;
public class DGraph
{
	private final int[][] dMatrix;
	private final int placeCount;
	
	public DGraph(int[][] dMatrix;
	{
		this.dMatrix = dMatrix;
		this.placeCount = dMatrix.length;
	}
	public result getRoute(int src, int dest,double avgS)
	{
		int[] dist = new int[placeCount];
		boolean[] visited = new boolean[placeCount];
		int[] prev = new int[placeCount];
		Arrays.fill(dist, Integer.MAX_VALUE);
		Arrays.fill(prev, -1);
		dist[src] = 0;
	
		for (int i = 0; i < placeCount - 1; i++
		{
			int u = minDist(dist, visited);
			visited[u] = true;
			for (int v = 0; v < placeCount; v++)
			{
				if (!visited[v] && dMatrix[u][v] > 0 && dist[u] + dMatrix[u][v] < dist[v])
				{
					dist[v] = dist[u] + dMatrix[u][v];
					prev[v] = u;
				}
			}
		}
		
		List<Integer> path = rePath(prev, dest);
		double eta = (dist[dest] / avgSpeedKmph);
		
		return new routeRes(path, dist[dest], eta);
	}
	
	private int minDist(int[] dist, boolean[] visited)
	{
		int min = Integer.MAX_VALUE, minIndex = -1;
		for (int i = 0; i < placeCount; i++)
		{
			if (!visited[i] && dist[i] < min)
			{
				min = dist[i];
				minIndex = i;
			}
		}
		return minIndex;
	}
	
	private List<Integer> rePath(int[] prev, int dest)
	{
		List<Integer> path = new ArrayList<>();
		for (int at = dest; at != -1; at = prev[at])
		{
			path.add(at);
		}
		collection.reverse(path);
		return path;
	}
}
		