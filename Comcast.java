import java.util.*;

// Version 2 (9 pm 08/27/19):
// Small Quality of life changes for questions
// Question 1 now will work much better with any input as long as it is positive, even for values greater than 9
// Question 2 now returns a character value or null.
// Question 3 returns null if there is no next positive value. Before was returning -1 as it never was specified.
// Question 3 iterTest() returns a message.
// Various small changes to optimize the code.

public class Main {

    public static void main(String[] args)
    {
        Scanner sc = new Scanner(System.in);
        System.out.println("Select question 1, 2 or 3");
        int question = sc.nextInt();
        switch (question)
        {
            case 1:
                System.out.println("Input list sizes:");
                int l1 = sc.nextInt();
                int l2 = sc.nextInt();
                LinkedList<Integer> List1 = new LinkedList<>();
                LinkedList<Integer> List2 = new LinkedList<>();
                String result;
                System.out.println("Input the elements of the first linked list:");
                for(int i=0; i<l1; i++)
                {
                    int nextItem = sc.nextInt();
                    List1.add(nextItem);
                }
                System.out.println("Input the elements of the second linked list:");
                for(int i=0; i<l2; i++)
                {
                    int nextItem = sc.nextInt();
                    List2.add(nextItem);
                }
                System.out.println("Result is: ");
                result=firstQuestion(List1,List2);
                System.out.println(result);
                break;
            case 2:
            System.out.println("Input test line");
            String S = sc.next();
            System.out.println("Result is: ");
            System.out.println(secondQuestion(S));
            break;
            case 3:
                iterTest();
                break;


        }

    }

    // Total runtime is O(n)

    private static String firstQuestion(LinkedList<Integer> numberOne, LinkedList<Integer> numberTwo)
    {
        // We assume we only work with positive integers.
        int carry=0;
        int value;
        LinkedList<Integer> finalList = new LinkedList<>();
        int firstCounter=numberOne.size()-1;
        int secondCounter=numberTwo.size()-1;
        while(firstCounter>=0&&secondCounter>=0)
        {
            value=carry+numberOne.get(firstCounter)+numberTwo.get(secondCounter);
            carry = value/10;
            value = value%10;
            finalList.addFirst(value);
            firstCounter--;
            secondCounter--;
        }
        // Three edge cases: if 1 bigger than 2, if 2 bigger than 1 and if equal, but something was carried over
        if(firstCounter>=0)
        {
            while(firstCounter>=0)
            {
                value = carry + numberOne.get(firstCounter);
                carry = value/10;
                value = value%10;
                finalList.addFirst(value);
                firstCounter--;
            }
        }
        if(secondCounter>=0)
        {
            while(secondCounter>=0)
            {
                value = carry + numberTwo.get(secondCounter);
                carry = value/10;
                value = value%10;
                finalList.addFirst(value);
                secondCounter--;
            }
        }
        while(carry>0)
        {

            finalList.addFirst(carry%10);
            carry=carry/10;
        }
        StringBuilder result =new StringBuilder();
        for(int i=0; i<finalList.size();i++)
        {
            result.append(finalList.get(i));
            if(i!=finalList.size()-1)
            {
                result.append("->");
            }
        }
        return result.toString();
    }

    // Total runtime is O(n)
    private static Character secondQuestion (String s)
    {
        // using ascii tables with case and character sensitivity, 128 chars.
        // Idea: create a ascii bucket. Then go through the string once putting characters into buckets
        // Then go the second time, if the character has exactly 1 in the bucket, return it. If we finished and none have it, return null

        int[] bucket = new int[128];
        for(int i=0; i<s.length(); i++)
        {
            bucket[(int)s.charAt(i)]++;
        }
        int flag=0;
        Character result=null;
        for(int i=0; i<s.length()&&flag==0;i++)
        {
            if(bucket[(int)s.charAt(i)]==1)
            {
                flag=1;
                result = s.charAt(i);
            }
        }
        return result;
    }
    private static void iterTest() {
        Iterator<Integer> intIter = Arrays.asList(new Integer[] {null, -1, 2, null, 15, -4, 50, null}).iterator();
        Iterator<Integer> iter = new positiveIntegerIterator(intIter);
        //multiple hasNext() calls succeed
        for (int i = 0; i < 100; i++) {
            assert iter.hasNext();
        }
        //values are correct
        assert iter.next() == 2;
        //no hasNext() call...
        assert iter.next() == 15;
        assert iter.hasNext();
        assert iter.next() == 50;
        assert !iter.hasNext();
        System.out.println("Assertion successful!");
    }


    public static class positiveIntegerIterator implements Iterator<Integer>
    {
        int next=-1; // always assume we have no more positives
        Iterator<Integer> internalIterator;
        private positiveIntegerIterator(Iterator<Integer> i)
        {
            internalIterator=i;
            while(internalIterator.hasNext()&&next<1)
            {
                Integer I = internalIterator.next();
                if(I!=null)
                next=I;
            }

        }
        @Override
        public Integer next()
        {
            int nextValue = next;
            if(nextValue<0) return null;
            if(internalIterator.hasNext())
            {
                Integer I = internalIterator.next();
                next =Objects.requireNonNullElse(I,-1);
            }
            while(internalIterator.hasNext()&&next<1)
            {
                Integer I = internalIterator.next();
                next=Objects.requireNonNull(I);
            }
            if(!internalIterator.hasNext()) next=-1;
            return nextValue;
        }

        @Override
        public boolean hasNext()
        {
            return next>0;
        }


    }


}
