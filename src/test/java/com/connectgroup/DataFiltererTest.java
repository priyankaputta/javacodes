package com.connectgroup;

import org.junit.Test;

import static org.junit.Assert.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

public class DataFiltererTest {
   

    private FileReader openFile(String filename) throws FileNotFoundException {
        return new FileReader(new File(filename));
    }
    
    private double getAverageForFilter(String filename) throws FileNotFoundException {
        return new DataFilterer().findAverage(openFile(filename));
    }

    @Test
    public void shouldReturnEmptyCollection_WhenLogFileIsEmpty() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/empty"), "GB").isEmpty());
    }
    
    @Test
    public void shouldReturnfilteredrows_ByCountry_WhenLogFileIsSingleLine() throws FileNotFoundException {
        String[] myArray = { "1431592497", "GB", "200" };
        Collection<?> expected = new ArrayList<String>(Arrays.asList(myArray));
        assertEquals(expected, DataFilterer.filterByCountry(openFile("src/test/resources/single-line"), "GB"));
    }
    
    @Test
    public void shouldReturnfilteredrows_ByCountry_WhenLogFileIsMultiLines() throws FileNotFoundException {
        String[] myArray = { "1433190845", "US", "539", "1433666287", "US", "789", "1432484176", "US", "850" };
        Collection<?> expected = new ArrayList<String>(Arrays.asList(myArray));
       // System.out.println("expected" +expected);
        assertEquals(expected, DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "US"));

    }

    

    @Test
    public void shouldReturnEmptyCollection_ByCountry_WhenLogFileIsSingleLine() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/single-line"), "US").isEmpty());
    }
    
    @Test
    public void shouldReturnEmptyCollection_ByCountry_WhenLogFileIsMultiLine() throws FileNotFoundException {
        assertTrue(DataFilterer.filterByCountry(openFile("src/test/resources/multi-lines"), "NZ").isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollection_ByCountryWithResponseTimeAboveLimit_WhenLogFileIsEmpty()
            throws FileNotFoundException {
        assertTrue(DataFilterer
                .filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/empty"), "GB", 10).isEmpty());
    }

    @Test
    public void shouldReturnFilteredRows_ByCountryWithResponseTimeAboveLimit_WhenLogFileIsSingleLine()
            throws FileNotFoundException {
        String[] myArray = { "1431592497", "GB", "200" };
        Collection<?> expected = new ArrayList<String>(Arrays.asList(myArray));
        assertEquals(expected, DataFilterer
                .filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/single-line"), "GB", 150));
    }
    
    @Test
    public void shouldReturnFilteredRows_ByCountryWithResponseTimeAboveLimit_WhenLogFileIsMultiLines()
            throws FileNotFoundException {
        String[] myArray = { "1433666287", "US", "789", "1432484176", "US", "850" };
        Collection<?> expected = new ArrayList<String>(Arrays.asList(myArray));
        assertEquals(expected, DataFilterer
                .filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "US", 550));

    }

    

    @Test
    public void shouldReturnEmptyCollection_ByCountryWithResponseTimeAboveLimit_WhenLogFileIsSingleLine()
            throws FileNotFoundException {
        assertTrue(DataFilterer
                .filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/single-line"), "GB", 200)
                .isEmpty());
    }
    
    @Test
    public void shouldReturnEmptyCollection_ByCountryWithResponseTimeAboveLimit_WhenLogFileIsMultiLine()
            throws FileNotFoundException {
        assertTrue(DataFilterer
                .filterByCountryWithResponseTimeAboveLimit(openFile("src/test/resources/multi-lines"), "US", 900)
                .isEmpty());
    }

    @Test
    public void shouldReturnEmptyCollection_ByCountryWithResponseTimeAboveAverage_WhenLogFileIsEmpty()
            throws FileNotFoundException {
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/empty")).isEmpty());
    }

    @Test
    public void shouldReturnFilteredRows_ByCountryWithResponseTimeAboveAverage_WhenLogFileIsMultiLines()
            throws FileNotFoundException {
        String[] myArray = { "1433190845", "US", "539", "1433666287", "US", "789", "1432484176", "US", "850" };
        Collection<?> expected = new ArrayList<String>(Arrays.asList(myArray));
        double average = getAverageForFilter("src/test/resources/multi-lines");
        DataFilterer.setAverage(average);
        assertEquals(expected,
                DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/multi-lines")));
    }
    
    

    @Test
    public void shouldReturnEmptyCollection_ByCountryWithResponseTimeAboveAverage_WhenLogFileIsSingleLine()
            throws FileNotFoundException {
        double average = getAverageForFilter("src/test/resources/single-line");
        DataFilterer.setAverage(average);
        assertTrue(DataFilterer.filterByResponseTimeAboveAverage(openFile("src/test/resources/single-line")).isEmpty());
    }

}
