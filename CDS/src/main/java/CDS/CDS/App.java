package CDS.CDS;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException
    { 
        ConnectedDominatingSetAlgorithm CDS=new ConnectedDominatingSetAlgorithm("E://ThesisProject//ThesisProject//Testfiles//t2.txt",10);
        CDS.execute();
    }
}
