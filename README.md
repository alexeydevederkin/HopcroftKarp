# HopcroftKarp

Hopcroft–Karp algorithm

An algorithm that takes as input a bipartite graph and produces as output a maximum cardinality matching – a set of as many edges as possible with the property that no two edges share an endpoint.


As an example – a solution for the problem of optimal assignments.

For instance, 5 employees and 5 jobs represented as matrix ("1" if employee can do the job).

```
employee  job1  job2  job3  job4  job5
   0       1     1     1     1     1
   1       1     0     0     1     0
   2       0     1     0     1     0
   3       0     1     0     1     1
   4       1     0     0     0     0
```

Optimal solution:

```
0 - job3
1 - job4
2 - job2
3 - job5
4 - job1
```

So maximum assignements is 5.

input:
```
5 5
1 1 1 1 1
1 0 0 1 0
0 1 0 1 0
0 1 0 1 1
1 0 0 0 0
```

output:
```
5
```


Another example:

```
employee  job1  job2  job3  job4  job5
   0       1     1     0     0     0
   1       1     0     0     0     0
   2       1     0     0     0     0
```

Optimal solution:

```
0 - job2
1 - job1 OR 2 - job1
```

Maximum assignements is 2.

input:
```
3 5
1 1 0 0 0
1 0 0 0 0
1 0 0 0 0
```

output:
```
2
```
