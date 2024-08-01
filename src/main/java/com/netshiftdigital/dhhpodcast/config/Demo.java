package com.netshiftdigital.dhhpodcast.config;

 class Laptop{
    public void code(){
        System.out.println("code, comple, run");
    }
}
 class Developer {
    public void devApp(Laptop lap){
        lap.code();
    }
}
public class Demo {
    public static void main(String[] args) {
        Laptop lap = new Laptop();
        Developer navin = new Developer();
        navin.devApp(lap);
    }
}