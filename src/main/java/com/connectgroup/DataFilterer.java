package com.connectgroup;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DataFilterer {
	private static double average;

    public static Collection<?> filterByCountry(Reader source, String country) 
    {
    	
    	BufferedReader lineReader = new BufferedReader(source);
    	String lineText = null;
    	int count = 0;
    	 Collection<String> filteredList = new ArrayList<String>();
    	try {
			while ((lineText = lineReader.readLine()) != null) 
			{
				 if (count == 0) 
	                {
	                	//to remove the header
	                    count++;
	                    continue;
	                }
				 String[] myArray = lineText.split(",");
				 List<String> myList = new ArrayList<String>(Arrays.asList(myArray));
			    //listLines.addAll(myList);
			    //for(String s1 : listLines)
			    if (myList.contains(country))
			    {
			    	filteredList.addAll(myList);
					
				}
			    else
			    {
			    	Collections.emptyList();
			    }
			}
			lineReader.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block      
			e.printStackTrace();
		}
        //System.out.println("filtered"+filteredList);
    	return filteredList;
    	
    }

    public static Collection<?> filterByCountryWithResponseTimeAboveLimit(Reader source, String country, long limit) {
    	 BufferedReader lineReader = new BufferedReader(source);
         String lineText = null;
         Collection<String> filteredList = new ArrayList<String>();
         int count = 0;
         long length = 0;
         long responseTime = 0;
         try {
             while ((lineText = lineReader.readLine()) != null) {
                 // Logic to remove header from the input data
                 if (count == 0) {
                	 count++;
                     continue;
                 }
                 String[] myArray = lineText.split(",");

                 List<String> myList = new ArrayList<String>(Arrays.asList(myArray));
                 for (String s1 : myArray) {
                     //finding RESPONSE_TIME from input
               boolean isNumeric = s1.chars().allMatch(x -> Character.isDigit(x));
                     if (isNumeric) {
                         length = s1.chars().count();
                         //identifying between RESPONSE_TIME and REQUEST_TIMESTAMP.
                         //REQUEST_TIMESTAMP length would be equal to 10 0r 13 digits,so we are 
                         //checking if length is less than 10..so that we will get the RESPONSE_TIME
                         if (length < 10) {
                             responseTime = Integer.parseInt(s1);
                             if (myList.contains(country)) {
                                 if (responseTime > limit) {
                                     filteredList.addAll(myList);
                                 }
                             }
                             else
                             {
                                 Collections.emptyList();
                             }

                         }
                     }
                 }
             }
          lineReader.close();
         } catch (IOException e) {
             // TODO Auto-generated catch block
             e.printStackTrace();
         } 
         
         //System.out.println("filtered"+filteredList);
         return filteredList;

 }

    public static Collection<?> filterByResponseTimeAboveAverage(Reader source) {
        BufferedReader lineReader = new BufferedReader(source);
        String lineText = null;
        double average = 0.0;
        Collection<String> filteredList = new ArrayList<String>();
        average = getAverage();

        long responseTime = 0;
        int iteration = 0;
        long count = 0;
        String[] myArray = null;
        try {
            while ((lineText = lineReader.readLine()) != null) {
                // Logic to remove header from the input data.
                if (iteration == 0) {
                    iteration++;
                    continue;
                }

                myArray = lineText.split(",");
                List<String> myList = new ArrayList<String>(Arrays.asList(myArray));
                for (String eachval : myArray) {
                    // Finding the RESPONSE TIME from the input line
                    boolean isNumeric = eachval.chars().allMatch(x -> Character.isDigit(x));
                    if (isNumeric) {
                        count = eachval.chars().count();
                        // Identifying between RESPONSETIME and
                        // REQUEST_TIMESTAMP.Unix Timestamp will be always 10
                        // digits or 13 digits
                        if (count < 10) {
                            responseTime = Integer.parseInt(eachval);
                            if (responseTime > average) {

                            	filteredList.addAll(myList);

                            }
                            else
                            {
                                 Collections.emptyList();
                            }

                        }
                    }
                }

            }


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	lineReader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }
        System.out.println("filtered"+filteredList);
        return filteredList;


    }


public static double getAverage() {
	return average;
}


public static void setAverage(double average) {
	DataFilterer.average = average;
}

public double findAverage(FileReader openFile) {
    BufferedReader lineReader = new BufferedReader(openFile);
    String lineText = null;
    double average = 0.0;
    List<Integer> filteredList = new ArrayList<Integer>();
    int responseTime = 0;
    int count = 0;
    long length = 0;
    String[] myArray = null;
    try {
        while ((lineText = lineReader.readLine()) != null) {
            // Logic to remove header from the input data.
            if (count == 0) {
            	count++;
                continue;
            }

            myArray = lineText.split(",");
            for (String eachval : myArray) {
                // Finding the RESPONSE_TIME from the input 
                boolean isNumeric = eachval.chars().allMatch(x -> Character.isDigit(x));
                if (isNumeric) {
                    length = eachval.chars().count();
                    //identifying between RESPONSE_TIME and REQUEST_TIMESTAMP.
                    //REQUEST_TIMESTAMP length would be equal to 10 0r 13 digits,so we are 
                    //checking if length is less than 10..so that we will get the RESPONSE_TIME
                    if (length < 10) {
                        responseTime =  Integer.parseInt(eachval);
                        filteredList.add(responseTime);
                    }
                    }
                }
            }

        Integer sum = 0;
        if(!filteredList.isEmpty()) {
          for (Integer respose : filteredList) {
              sum += respose;
          }
          average =  sum.doubleValue() / filteredList.size();
        }

    } catch (Exception e) {
        e.printStackTrace();
    } finally {
        try {
        	lineReader.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
    System.out.println("average: "+average);
	return average;
	}
}