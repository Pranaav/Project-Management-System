Trie:-
Insert:-
 inserion time complexity will be order string length;
i am inserting an element of type T firstly i am taking a key w.r.t that element and as key must be a string and now i am storing that string in an order such 
that every character in that string must be in different(child) array with index as according to ascii; and storing element T in the last character;
Delete:-
delete time complexity will be order of 2*(string length); 
i am deleting it from last and i am checking if particular character has any child or not if it has a child than i will return ; i will move up and delete 
previous character if it does not have any value or child;
print level:-
its time complexity order will be 128*level;
for printing level i am just doing breadth first traversal, by using queues, i am pushing an element and then poping out one and print its all children and 
storing them in a new queue; and when i reached that level i am using array of int and just doing a[i]++ if i found charcter whose ascii is i; and after that 
going through that array i am printing it in lexicographic order;
print():
for full printing i am just printing all level one by one without break;; just breaking if i found next level null;
search:-
its search will also be of string length order time complexity;
for searching i am just seeing the index of word is matching its charcter's array ascii index; and if i found last word and that has value stored in it, then 
just returning that node;


Red BLack Tree:-
 insert:-
 time complexity will be of just greater than order log(n) first log(n) for searching place for node and some complexity for restructuring
 there are some cases for inserting node or restructuring it;
 firstly if parent is black then just add node into it;
 if parent is not black and uncle is null or black then do check 4 cases in which it is 
 
 left left:
if node is like left to grand parent and parent simaltanously then just rotate that node to right so that curr.parent have two child that is curr and previous 
grand parent;

left right:
if node is left w.r.t grand parent and right w.r.t to parent then first rotate its node to left such that curr replace place with curr.parent and curr left 
child will be previous parent and now it become left left case just do right rotate now;

right right case:
it is same as left left, we just have rotate left here;

right left :
it is same as left right case, we just have to first rotate right and then left ;

if uncle is red then just recolor it; curr.parent = black , uncle also become black and change grand parent to red and now just go up and do all the things 
wich we did for curr now did it for curr.parent.parent;

search:-
here search is same as simple and balanced binary tree its time complexity will be order log(n); because there is only log(n) level;


MaxHeap:-
insert:-
for inserting its time complexity order will be order log(n);
for inserting i am putting element in the last and then checking its priority w.r.t to its parent if it has greater priority then swap and just do it till we 
get lower priority;

extract max:
its time complexity order will be log(n);
i am deleting first index and then just putting first index value as its last index and then checking its child 2*i + 1; 2*i +2 ; and if child priority is 
greater than it then swap and here if both child have greater priority then swap with that element which have greater priority than that;


Project Management:-

handling job :-
i am handling job by adding job in heap with project priority and i am also adding it in trie for later usage;

handling user
i am adding user to hash map as key with username and value as user;

handling project:
i am adding all project in a trie; and also making a rbtree which having jobs which is not executed due to unsufficient budget;

handling add:
i am adding budget to that project and also adding all jobs from rb tree for that project to heap of job that i made earlier;

handling emptyline():
here i am extracting a job from heap yes this must have highest priority as we are executing it from max heap; and we check if its runtime is less than its 
project budget and then just return and also subtracting it runtime from budget and also assign job status as completed.
if budget is less than run time just insert that job to rb tree which have project name as a key;

handling runto complition:
here i am executing all remaining jobs in heap same as emptyline just we are breaking here just when there are no more jobs for executing;

handle query :
here we are using trie of jobs we first search for that job in trie and if we found that job then just return its status i,e requested or completed.

handle status :
i am adding all jobs which executed successfully in linked list of jobs; and first i will print that jobs and then we are using another max heap for printing 
all unfinished jobs which have status requested;

handle new_user:
 i am answering this query by taking a trie of users and a particular user have all jobs belong to that particular user;
 time complexity will be order(total no. of jobs in that particular user);

handle new_project:
 i am answering this query by taking a trie of projects and a particular project have all jobs belong to that particular project
 time complexity will be order(total no. of jobs in that project);

handle new_projectuser:
 i am answering this query by iterating an trie of project and after finding that project iterate over a rbtree which has key with userstring and value is a 
 linked list of all jobs of that user; time complexity will be order({(total no. of executed jobs of that user and project) + (total no. of jobs belong to 
 that particular user and project)}log(total no. of users));

handle new_priority:
 i am iterating over all projects which has priority and then add all jobs(belong to that project) to arraylist and then return arraylist;
 time complexity will be order{(total no. of projects) + (total no. of projects whose priority greater than given priority)*(no. of jobs belong to that 
 projects)}

handle timed_top_consumer:
 i am iterating over all user and add to heap with comparator with consume budget and latest time; and then just extract max jobs till top value or null 
 value; time complexity will be order((no. of users)*(log(no. of users)));

handle timed_flush:
 i am iterate over a heap with all jobs then adding jobs which can execute to another heap; and then i am adding all unexecuted jobs again into initial heap ;
 time complexity will be order((total no. of jobs)*log( total no. of jobs));