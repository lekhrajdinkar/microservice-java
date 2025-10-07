module module2
{
    //==== Directives :  requires, opens, exports

    // 1️⃣ requires [transitive] ...
    requires spring.boot;
    requires spring.boot.autoconfigure;
    requires spring.context;
    requires spring.beans;

    requires module1; // 👈🏻

    // 2️⃣opens
    opens client; //to spring.core, spring.beans, spring.aop;
    //opens client to spring.core, spring.beans, spring.aop;

    // 3️⃣exports

}