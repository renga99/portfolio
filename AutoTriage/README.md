AutoTriage - Bug Triaging tool

AutoTriage - Yodlee's one stop solution for bug triaging.

Bug triaging is an important activity in any software development project. It involves developers working through the
set of unassigned bugs, determining for each of the bugs whether it represents a new issue that should receive attention, and, if so,
assigning it to a developer and a milestone. 
Large open source projects like **Mozilla and **Eclipse foundation employ their own bugs triaging tools/mechanisms to identify the best developer 
and ETA efficiently based on their existing bug dataset.

Why automate triaging?
1. Consumes time of senior resources
2. Manual task - no accurate results/based on assumptions
3. Dependent on experienced people - experienced people should concentrate on mentioning more
4. Chances of slippage in ETA or bugs being missed out due to wrong team/developer being assigned.

AutoTriage employs set of machine learning and mathematical algorithms over yodlee's vast bug dataset/database, 
to identify the best possible developer and ETA for an unassigned/new bug.

AutoTrigae Mission Statement :

For a manually triaged BUG(B) having ETA(X) and ASSIGNED_DEVELOPER(Y), can we identify a BEST DEVELOPER(K)such 
that B can be resolved in LESS THAN X days without K being OVER-LOADED with Knowledge Distribution Factor(KF) 
being considered. IF ABOVE IS TRUE, ASSIGNED_DEVELOPER becomes K and ETA(X) becomes LESS THAN X days.

What makes a best developer(K) in a bug resolution  perspective ?

1. Has K worked on the reported code module recently?
2. Is K familiar about the nature and behaviour of the code module ?
3. How much time has K spent on the code module before?
4. Has K worked on bugs of similar nature before ?
5. How many bugs of similar type has K worked on very recently ?
6. How much time has K spent in each section of the code module?
and many more

What makes a good ETA ?

1. Considering average , maximum and minimum closure time for similar bugs.
2. Considering the latency.
3. Considering  the end site response time.
4. Considering  the success rate of users
5. Considering the bug metadata like cobrand, error code, priority 
     and severity.
6. Considering the holidays and scheduled maintenance.
7. Considering code module complexity/type.
8. Considering know issues and duplicate bugs.
9. Considering band width of developers. 
and many more

AutoTriage's goals:

1. Best accuracy in triaging/ETA
2. Identification of best resource
3. Best load balancing with respect to knowledge, bandwidth and work.
4. Best identification of redundant and duplicate bugs
5. Remove manual task, manual errors.
6. Free senior resources for mentioning and complex bug fixing.
7. Collect meaningful data sets from bugs Database

What's Next ?

We have already built a Pilot version of AutoTriage and tested the same. As of now we have achieved 80% accuracy
based on developer feedback and stats.We will be building a production ready version and demonstrate the same.

Technologies :

Java, Spring, Hibernate, Machine Learning
