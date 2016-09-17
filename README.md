### Connected Dominating Set(CDS) Algorithm

#Phase one:
1. All nodes in V(set containing nodes in descending order of degree) are initialized to red color.
2. The first node in V(set containing nodes in descending order of degree) changes its color to black and sends a notification to all its neighbors.
    On receiving this notification, the red neighbours of this node change their colour to
    gray.
3. Now consider the second node in V(set containing nodes in descending order of degree).
   3.1 If it is red in color, repeat the above process.
   3.2 If it is gray in color, check to see if it has any red neighbors. If yes, the color of the
   second node is changed to black and its red neighbors turn gray after receiving the
   notification from the second node.
4. The above process continues until there are no more red nodes in the network. Note
    that this phase is carried only until the network gets exhausted of red nodes therefore
    not checking for all nodes in the network. This saves computation time.
5. After the above steps are completed, if any gray node in the network is found to have at
    least two black neighbors, it is a possible candidate for a Connected Dominating Set and hence should be
    colored yellow.

#Phase two:

1. Check for yellow nodes in the network, and if present,
    1.1 If the neighbors of the yellow node are not all present in the neighbor set of
          either/both of the two black neighbors, the color of the node is changed from yellow
          to black. Otherwise, the color is changed from yellow to gray.
2. If a black node has at least two black neighbors,
    2.1 If the neighbors of the black node are all present in the neighbor set of either/both the
          two black neighbors, the color of the node is changed from black to gray.
3. Now, all the black nodes in the network form a Connected Dominating Set.

##Result Produced after the run of algorithm on 10 nodes showing the minimum connected dominating set.
![cdsresult](https://cloud.githubusercontent.com/assets/7685777/18609913/60016990-7d2b-11e6-93ed-1fc28f7ef4b7.PNG)

