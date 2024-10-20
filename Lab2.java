import java.io.*;
import java.util.*;


class Graph{

    private ArrayList<ArrayList<Integer>> adj;

    private int nodes;
    private int edges;

    private boolean loopExists; // loop check using DFS

    private boolean used[]; // visited array for BFS

    private ArrayList<Integer> color = new ArrayList<>();// visited array for DFS

    private int d[]; // distance array for BFS
    private int p[]; // parent array for BFS
    private int p1[]; // parent array for DFS

    private int dd[]; // discovery time for DFS
    private int ff[]; // finishing time for DFS

    private int time;

    private ArrayList<Integer> toposort = new ArrayList<>();
    private ArrayList<Integer> visited3 = new ArrayList<>();

    /*--------------------------------------
            Graph Constructor using file
    --------------------------------------*/
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
            p1 = new int[nodes + 1];
            dd = new int[nodes + 1];
            ff = new int[nodes + 1];

            time = 0;

            loopExists = false;


            adj = new ArrayList<>();
        
            for (int i = 0; i <= nodes; i++) {
                adj.add(new ArrayList<>());

                used[i] = false;

                d[i] = 0;
                dd[i] = 100000000;
                ff[i] = 100000000;

                p[i] = -1;
                p1[i] = -1;

                visited3.add(0);
                color.add(0);

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
        return (u <= nodes && u >= 0);
    }

     /*--------------------------------------
                    AddEdge
    --------------------------------------*/
    public void addEdge(int u, int v) {
        if(checkVerticesInsideBound(u) && checkVerticesInsideBound(v))
        {
            adj.get(u).add(v);
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
     /*--------------------------------------
                Print Adj List
    --------------------------------------*/
    public void printAdjList() {
        System.out.println("\n\nAdjacency List:");
        System.out.println("-------------------\n");
        for (int i = 0; i < adj.size(); i++) {
            System.out.print(i + " -> ");
            for(int ele : adj.get(i))
            {
                System.out.print(ele + " ");
            }
            System.out.println();
        }

    }
     /*--------------------------------------
        <-------   BFS  -------!>
    --------------------------------------*/
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
    
    /*--------------------------------------
        <-------   DFS  -------!>
    --------------------------------------*/
    void DFS(int v)
    {
        System.out.print(v + " ");
        visited3.set(v, 1); // gray
        dd[v] = ++time;
        for(Integer child : adj.get(v))
        {
            if(color.get(child) == 0)
            {
                p1[child] = v;
                DFS(child);
            }
            else {
                loopExists = true;
            }
        }
        visited3.set(v, 2); // black
        ff[v] = ++time;

    }

    /*--------------------------------------
        <-------   Topo Sort  -------!>
    --------------------------------------*/
    public List<Integer> topologicalSort() {
 
        for (int i = 0; i < nodes; i++) {
            if (visited3.get(i) == 0)
                calldfs(i);
        }
        List<Integer> toposort2 = new ArrayList<>();
        for (int i = toposort.size() - 1; i >= 0; i--) {
            toposort2.add(toposort.get(i));
        }
        return toposort2;
    }

    // seperate dfs for topo sort
    //-------------------------
    void calldfs(int i) {
        visited3.set(i, 1);
        for (Integer child : adj.get(i)) {
            if (visited3.get(child) == 0) {
                calldfs(child);
            }
        }
        toposort.add(i);
    }


}
public class Lab2{
    public static void main(String args[]){
        String filename = "input.txt";
        Graph g = new Graph(filename);
        g.printAdjList();

        System.out.println("\nPerforming DFS starting from vertex 5:");
        g.DFS(5);
        System.out.println();

        System.out.println("\nPerforming Topological Sort:");
        List<Integer> topoOrder = g.topologicalSort();
        System.out.println("Topological Sort order: " + topoOrder);

    }
}
