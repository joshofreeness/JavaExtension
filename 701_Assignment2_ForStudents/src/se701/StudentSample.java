package se701;

public class StudentSample extends ExtendMe2 {

    public static void main(String[] args) {
        System.out.println(parents + " " + otherParents);
    }
}

class ExtendMe1 extends ExtendMeAlso {

    protected int otherParents = 1;
}

class ExtendMe2 extends ExtendMe1 {

    protected int parents = 2;
}

class ExtendMeAlso {

    protected int grandparents = 5;
}

class ExtendMeSometimes {

    protected int otherGrandparents = 7;
}
