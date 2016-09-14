package CDS.CDS;

import java.io.IOException;

public class App 
{
    public static void main( String[] args ) throws IOException
    { 
        ConnectedDominatingSetAlgorithm CDS=new ConnectedDominatingSetAlgorithm("E://ThesisProject//ThesisProject//Testfiles//t1.txt",7);
        CDS.execute();
    }
}
