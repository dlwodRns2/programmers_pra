package org.example;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.Stack;

class Graph{
    private LinkedList<Integer>[] adjacencyList;

    public Graph(int vertex){
        adjacencyList = new LinkedList[vertex+1];
        for(int i=0;i< adjacencyList.length;i++){
            adjacencyList[i] = new LinkedList<>();
        }
    }

    public LinkedList<Integer>[] getAdjacencyList() {
        return adjacencyList;
    }
    public void addEdge(int v,int w){
        adjacencyList[v].add(w);
        adjacencyList[w].add(v);
    }
    public void printGraph(){
        for(int i=1;i< adjacencyList.length;i++){
            System.out.print(i+"와 연결된 정점 : ");
            for(int v : adjacencyList[i]){
                System.out.print(v+" ");
            }
            System.out.println();
        }
    }
    public void bfs(int st){
        boolean[] visited = new boolean[10];
        Queue<Integer> queue = new LinkedList<>();

        queue.add(st);
        visited[st]=true;

        System.out.println("BFS");
        while(!queue.isEmpty()){
            int cur = queue.peek();
            queue.poll();
            System.out.print(cur+" ");

            for(int v: adjacencyList[cur]){
                if(visited[v]){
                    continue;
                }
                queue.add(v);
                visited[v]=true;
            }
        }
        System.out.println();

    }
}
public class Bfs {
    static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int numEdge = 9;
        //방문한 정점 확인
        boolean visited[] = new boolean[numEdge+1];
        //거리 저장
        int dist[] = new int[numEdge+1];
        int parent[] = new int[numEdge+1];
        Queue<Integer> queue= new LinkedList<>();

        Graph  g = new Graph(numEdge);
        LinkedList<Integer>[] adj = g.getAdjacencyList();

        //간선 추가
        g.addEdge(1,2);g.addEdge(1,3);
        g.addEdge(2,3);g.addEdge(2,4);g.addEdge(2,6);
        g.addEdge(3,7);
        g.addEdge(4,5);g.addEdge(4,7);g.addEdge(4,8);
        g.addEdge(5,6);
        g.addEdge(7,8);
        //g.addEdge(8,9);

        g.printGraph();

        //도전과제 1 : 시작점 변경
        //int startVertex=1;
        int startVertex=5;

        queue.add(startVertex);
        visited[startVertex]=true;
        parent[startVertex]=-1;

        System.out.println("BFS");
        while(!queue.isEmpty()){
            int cur = queue.peek();
            queue.poll();
            System.out.print(cur+" ");

            for(int v: adj[cur]){
                if(visited[v]){
                    continue;
                }
                parent[v]=cur;
                queue.add(v);
                visited[v]=true;
                dist[v]=dist[cur]+1;
            }
        }
        System.out.println();

        //DFS
        //visited 초기화
        for(int i=0;i<visited.length;i++){
            visited[i]=false;
        }
        Stack<Integer> stack = new Stack<>();
        stack.add(startVertex);
        visited[startVertex]=true;

        System.out.println("DFS");
        while(!stack.isEmpty()){
            int cur = stack.peek();
            stack.pop();
            System.out.print(cur+" ");

            for(int v: adj[cur]){
                if(visited[v]){
                    continue;
                }
                stack.push(v);
                visited[v]=true;
            }
        }
        System.out.println();

        //3. BFS 시, 각 정점까지의 최단거리 출력
        System.out.println("start : "+startVertex);
        for(int i=1;i< adj.length;i++){
            System.out.println(i+"번 정점까지의 최단 거리 : "+dist[i]);
        }

        //4. 경로 복원
        System.out.println("parent");

        int vt = 9;
        System.out.print(vt);
        while(true){
            if(parent[vt]==-1||parent[vt]==0){
                break;
            }
            vt=parent[vt];
            System.out.print( " <- "+vt);
        }
        System.out.println();

        //5. 연결되지 않은 그래프 확인
        //vis 초기화
        for(int i=0;i<visited.length;i++){
            visited[i]=false;
        }
        for(int i=1;i<10;i++){
            queue.add(i);
            visited[i]=true;

            while(!queue.isEmpty()){
                int cur = queue.peek();
                queue.poll();
                System.out.print(cur+" ");

                for(int v: adj[cur]){
                    if(visited[v]){
                        continue;
                    }
                    parent[v]=cur;
                    queue.add(v);
                    visited[v]=true;
                }
            }
            System.out.println();

            //visited 초기화
            for(int j=0;j<visited.length;j++){
                visited[j]=false;
            }
        }
    }
}

