# examware

This is a playground that accompanies my blog: 
[https://maxnazarati.ghost.io/](https://maxnazarati.ghost.io/)

It's simple and a bit rough around the edges, the idea is to polish and shape
it along with the topics I write about on the blog.

## Project description

This is a RESTful API powering a theoretical driving exam system.

There are the concepts of a `student`, the `exam` and the `exam attempt`.

The `exam` has a name which corresponds to the type of license the exam
is for (I'm using the german license types).

A `student` can register for an `exam` but there are some requirements:

- the fee must have been paid
- the student has visited at least 10 lessons
- a failed `exam attempt` must lie at least 2 weeks in the past (not 
  applicable to the 1st attempt, of course)

When a `student` registers for an `exam`, then an `exam attempt` is created:

At a later point, an admin enters the `student`'s score into the system.

If the `student` scored over 70% then it's a pass, otherwise, it is a
failure. Before the scoring, the score is null.