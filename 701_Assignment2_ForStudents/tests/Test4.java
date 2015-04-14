public class Test4 extends ExtendMe {

    public void foo(boolean x) {
        b = 123;
    }

    public void bar() {
        int b = 456;
    }
}

class ExtendMe {
}

class DontExtendMe {
}
