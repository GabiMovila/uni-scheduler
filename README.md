# Uni-scheduler
A project that schedules the Uni classess during a week, providing the program for the student groups and teachers according to rooms availability and professors's preferences

#How to use
The input are 2 files: _rooms.txt_ and _professors.txt_. In the first file the name of the professors and the course they teach should
be input **(FirstName LastName CourseName)**. </br>
The second file contains the name of the rooms available.
</br>
###Input manually the preferences
The program reads the professors from the textfile and asks for each of them what preferences do they have. The preferences are of 2 types:
* **Unavailable days**: The days the teacher can't teach;
* **Wanted hours**: When the teacher wants to do the job ( >X or <X, X being an hour of the day)
