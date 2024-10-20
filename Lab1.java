import java.io.*;
import java.util.*;


class Graph{

    private ArrayList<ArrayList<Integer>> adj;

    private int nodes;
    private int edges;

    private boolean used[];
    private int d[];
    private int p[];



    public Graph(String filename){
        try  {
            Scanner file = new Scanner(new File(filename));

            nodes =  file.nextInt();
            edges =  file.nextInt();

            System.out.println("No of nodes  : " + nodes);
            System.out.println("No of edges  : " + edges);

            used = new boolean[nodes + 1];
            d = new int[nodes + 1];
            p = new int[nodes + 1];


            adj = new ArrayList<>();
            for (int i = 0; i <= nodes; i++) {
                adj.add(new ArrayList<>());
                used[i] = false;
                d[i] = 0;
                p[i] = -1;
            }

            while(file.hasNext())
            {
                int u = file.nextInt();
                int v = file.nextInt();

                System.out.println(u + " " + v);

                addEdge(u,v);
            }
        } catch (Exception e) {
            System.out.println("Exception Found !" + e);
        }
    }
    public boolean checkVerticesInsideBound(int u)
    {
        return (u <= nodes && u >= 1);
    }
    public void addEdge(int u, int v) {
        if(checkVerticesInsideBound(u) && checkVerticesInsideBound(v))
        {
            adj.get(u).add(v);
            adj.get(v).add(u); 
        }
        else 
        {
            System.out.println("Error : You Have inserted a node that is out of the nodes limit.");
        }
    }
    public int getNumOfVertices()
    {
        return nodes;
    }
    public ArrayList<Integer> getAdjVerticesOfAVartex(int v)
    {
        ArrayList<Integer> ans = new ArrayList<>();
        for(Integer ele : adj.get(v))
        {
            ans.add(ele);
        }
        return ans;

    }
    public void addVertex(int n)
    {
        for (int i = nodes + 1; i <= nodes + n; i++) {
            adj.add(new ArrayList<>());
            used[i] = false;
            d[i] = 0;
            p[i] = -1;
        }
    }
    public void printAdjList() {
        System.out.println("\n\nAdjacency List:");
        System.out.println("-------------------\n");
        for (int i = 1; i < adj.size(); i++) {
            System.out.print(i + " -> ");
            for(int ele : adj.get(i))
            {
                System.out.print(ele + " ");
            }
            System.out.println();
        }

    }




    public int[] BFS(int s)
    {

        LinkedList<Integer> q = new LinkedList<Integer>();

        q.push(s);
        used[s] = true;
        p[s] = -1;
        
        
        while (!q.isEmpty()) {

            int v = q.pop();
            System.out.println(v + " ");

            for (int u : adj.get(v)) {

                if (!used[u]) {

                    used[u] = true;

                    q.push(u);

                    d[u] = d[v] + 1;

                    p[u] = v;
                }

            }
        }
        return d;
    }


}
public class Lab1{
    public static void main(String args[]){
        String filename = "input.txt";
        Graph g = new Graph(filename);

        System.out.println("No of vertices +  " + g.getNumOfVertices());

        ArrayList<Integer> adjElementsOf2 = g.getAdjVerticesOfAVartex(2);
        System.out.print("Adj elements of 2 : ");
        for(int ele : adjElementsOf2)
        {
            System.out.print(ele + " ");
        }

        g.printAdjList();

        int dist[] = g.BFS(1);
        System.out.println("Distances from all vertices : ");
        for(int i = 1; i < dist.length; i++)
        {
            System.out.println(i  + " -> " + dist[i]);
        }
    }
}
