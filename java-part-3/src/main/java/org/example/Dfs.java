package org.example;

import java.util.LinkedList;
import java.util.Stack;

class GraphDFS{
    private LinkedList<Integer>[] adjacencyList;

    public GraphDFS(int vertex){
        adjacencyList = new LinkedList[vertex+1];
        for(int i=0;i< adjacencyList.length;i++){
            adjacencyList[i]=new LinkedList<>();
        }
    }
    public void addEdge(int v,int w){
        adjacencyList[v].add(w);
        adjacencyList[w].add(v);

    }
    public void dfs(int start){
        boolean[] visited = new boolean[10];
        //3. 경로/도달 확인
        int[] parent= new int[10];

        System.out.println("정점 "+start + " 에서 시작하는 DFS");
        dfsRecursive(start,visited,parent);
        parent[start]=-1;
        System.out.println();
    }
    public void dfsRecursive(int vertex, boolean[] visited){
        visited[vertex]=true;
        System.out.print(vertex+" ");
        for(int v : adjacencyList[vertex]){
            if(visited[v]){
                continue;
            }
            visited[v]=true;
            dfsRecursive(v,visited);
        }
    }
    //1. DFS(stack ver)
    public void dfsStack(int start){
        boolean[] visited = new boolean[10];

        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        visited[start]=true;

        System.out.println("정점 "+start+"에서 시작하는 DFS(stack)");
        while(!stack.isEmpty()){
            int cur = stack.pop();
            System.out.print(cur+" ");
            for(int v : adjacencyList[cur]){
                if(visited[v]){
                    continue;
                }
                stack.push(v);
                visited[v]=true;
            }
        }
        System.out.println();
    }

    //3. 경로/도달 확인
    public void dfsRecursive(int vertex, boolean[] visited,int[] parent){
        visited[vertex]=true;
        System.out.print(vertex+" ");
        for(int v : adjacencyList[vertex]){
            if(visited[v]){
                continue;
            }
            visited[v]=true;
            parent[v]=vertex;
            dfsRecursive(v,visited,parent);
        }
    }
    //3. 경로/도달 확인
    public boolean toVertex(int st, int ed){
        boolean[] visited = new boolean[10];
        int[] parent= new int[10];

        parent[st]=-1;
        dfsRecursive(st,visited,parent);
        System.out.println();

        //경로 출력
        System.out.println("경로 출력");
        int v= ed;
        System.out.print(v);
        while(true){
            if(parent[v]==0||parent[v]==-1){
                break;
            }
            System.out.print(" <- "+parent[v]);
            v=parent[v];
        }
        System.out.println();

        if(parent[ed]==0){
            return false;
        }
        return true;
    }

    //4. 사이클 탐지
    public boolean dfsRecursive(int vertex, boolean[] visited,int[] parent,boolean isCycle){
        visited[vertex]=true;
        System.out.print(vertex+" ");
        for(int v : adjacencyList[vertex]){
            if(visited[v]){
                if(parent[vertex]!=v){
                    //System.out.println("사이클 탐지");
                    isCycle=true;
                }
                continue;
            }
            visited[v]=true;
            parent[v]=vertex;
            dfsRecursive(v,visited,parent);
        }
        System.out.println();
        return isCycle;
    }
    public boolean isCycle(int st){
        boolean[] visited = new boolean[10];
        int[] parent= new int[10];
        boolean isCycle=false;
        parent[st]=-1;
        return dfsRecursive(st,visited,parent,isCycle);

    }
    //5. 연결 요소 세기
    public int dfsStack(int start,int cnt){
        boolean[] visited = new boolean[10];

        Stack<Integer> stack = new Stack<>();
        stack.push(start);
        visited[start]=true;

        System.out.println("정점 "+start+"에서 시작하는 DFS(stack)");
        while(!stack.isEmpty()){
            int cur = stack.pop();
            System.out.print(cur+" ");
            for(int v : adjacencyList[cur]){
                if(visited[v]){
                    continue;
                }
                stack.push(v);
                visited[v]=true;
                cnt++;
            }
        }
        System.out.println();
        return cnt;
    }
    public int count(int st){
        return dfsStack(st,1);
    }
}
public class Dfs {
    static void main(String[] args) {
        int numVertex=9;
        GraphDFS graph = new GraphDFS(9);
        Graph bfsGraph = new Graph(10);
        //간선 추가
        graph.addEdge(1, 2);bfsGraph.addEdge(1, 2);
        graph.addEdge(1, 3);bfsGraph.addEdge(1, 3);
        graph.addEdge(2, 3);bfsGraph.addEdge(2, 3);
        graph.addEdge(2, 4);bfsGraph.addEdge(2, 4);
        graph.addEdge(2, 6);bfsGraph.addEdge(2, 6);
        graph.addEdge(3, 7);bfsGraph.addEdge(3, 7);
        graph.addEdge(4, 5);bfsGraph.addEdge(4, 5);
        graph.addEdge(4, 7);bfsGraph.addEdge(4, 7);
        graph.addEdge(4, 8);bfsGraph.addEdge(4, 8);
        graph.addEdge(5, 6);bfsGraph.addEdge(5, 6);
        graph.addEdge(7, 8);bfsGraph.addEdge(7, 8);
        graph.addEdge(8, 9);bfsGraph.addEdge(8, 9);

        graph.dfs(1);   // 1번에서 깊이 우선 탐색

        //1. 스택으로 DFS 구현
        System.out.println("1. 스택으로 DFS 구현");
        graph.dfsStack(1);

        //2. 시작점 바꾸기
        System.out.println("2. 시작점 바꾸기");
        graph.dfs(3); //3 1 2 4 5 6 7 8 9
        graph.dfs(5); //5 4 2 1 3 7 8 9 6

        //3. 경로/도달 확인
        System.out.println("3. 경로/도달 확인");
        System.out.println("도달 가능 여부 : "+graph.toVertex(1,8));

        //4. 사이클 탐지
        System.out.println("4. 사이클 확인: "+graph.isCycle(4));

        //5. 연결 요소 세기
        System.out.println("5. 연결 요소 세기");
        for(int i=1;i<10;i++){
            System.out.println("끊긴 그래프의 연결 요소 개수 : "+(numVertex-graph.count(i))+"개");
        }
        //6. BFS와 한 파일에서 비교
        bfsGraph.bfs(1);//1 2 3 4 6 7 5 8 9
        graph.dfs(1); //1 2 3 7 4 5 6 8 9
    }
}
