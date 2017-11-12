# dp-transportation-optimization
Optimization problem solved using dynamic programming

# Problem
Given c Capita and v vehicule types each with their own capacity and price, optimize the amount of vehicules of each type used to transport the capita.

# Input
- Multiple scenarios can be executed at once
- The first line of every scenario is the total capita to transport (c)
- The second line of every scenario is the total amount of vehicule types (v)
- The next v lines each correspond to the capacity and the price of a vehicule type.

e.g:
```
3
4
4	60
15	210
40	550
400	4800
1249
4
4	60
15	210
40	550
400	4800
1250
3
4	60
15	222
42	615
43
```

# Output
A formatted string containing the scenario index, the total cost, the total capita, the distribution of each vehicule and the number of seats left.

e.g:
```
Case 1: The total cost to transport 1249 capita is 15090$. We need 3 type 4 vehicules, 3 type 2 vehicules and 1 type 1 vehicule. There's no empty seats.
Case 2: The total cost to transport 1250 capita is 15130$. We need 3 type 4 vehicules, 1 type 3 vehicule and 3 type 1 vehicules. There's 2 empty seats.
Case 3: The total cost to transport 43 capita is 660$. We need 11 type 1 vehicules. There's 1 empty seat.
```

# My solution big O
O(cv) where c is the capita and v the amount of vehicule choices
