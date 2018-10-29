import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Problema {
	public static void main(String[] args) throws NumberFormatException, IOException {
		BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		int T = Integer.parseInt(br.readLine());
		for (int i = 0; i < T; i++) {
			int N = Integer.parseInt(br.readLine());
			int R = Integer.parseInt(br.readLine());
			graph = new double[N][N];
			initGraph();
			for (int j = 0; j < R; j++) {
				String[] temp = br.readLine().split(" ");
				graph[Integer.parseInt(temp[0])][Integer.parseInt(temp[1])] = 1;
				graph[Integer.parseInt(temp[1])][Integer.parseInt(temp[0])] = 1;
			}
			String[] blabla = br.readLine().split(" ");
			int a = Integer.parseInt(blabla[0]);
			int b = Integer.parseInt(blabla[1]);
			double[][] resp = floydWarshall();
			int max = 0;
			for (int j = 0; j < N; j++) {
				if (resp[a][j] + resp[j][b] > max && resp[a][j] != Double.POSITIVE_INFINITY
						&& resp[a][b] != Double.POSITIVE_INFINITY) {
					max = (int) (resp[a][j] + resp[j][b]);
				}
			}
			int blablablaba = i + 1;
			System.out.println("Case " + blablablaba + ": " + max);
		}

	}

	// graph represented by an adjacency matrix
	private static double[][] graph;

	private static boolean negativeCycle;

	private static void initGraph() {
		for (int i = 0; i < graph.length; i++) {
			for (int j = 0; j < graph.length; j++) {
				if (i == j) {
					graph[i][j] = 0;
				} else {
					graph[i][j] = Double.POSITIVE_INFINITY;
				}
			}
		}
	}

	public boolean hasNegativeCycle() {
		return this.negativeCycle;
	}

	// all-pairs shortest path
	public static double[][] floydWarshall() {
		double[][] distances;
		int n = graph.length;
		distances = Arrays.copyOf(graph, n);

		for (int k = 0; k < n; k++) {
			for (int i = 0; i < n; i++) {
				for (int j = 0; j < n; j++) {
					distances[i][j] = Math.min(distances[i][j], distances[i][k] + distances[k][j]);
				}
			}

			if (distances[k][k] < 0.0) {
				negativeCycle = true;
			}
		}

		return distances;
	}

}